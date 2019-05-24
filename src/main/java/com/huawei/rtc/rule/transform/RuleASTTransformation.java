package com.huawei.rtc.rule.transform;

import com.huawei.rtc.rule.CamelEngine;
import groovy.lang.Mixin;
import groovy.util.logging.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.MethodClosure;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.LogASTTransformation;

import static org.codehaus.groovy.ast.expr.VariableExpression.THIS_EXPRESSION;

/**
 * Created by dongjiang on 2019/5/24.
 */

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class RuleASTTransformation implements ASTTransformation {
    private static final String CAMEL_CONTEXT_FIELD_NAME = "camelContext";
    private static final String LOG_FIELD_NAME = "log";
    private static final Token EQUAL_TOKEN = Token.newSymbol(Types.EQUAL, -1, -1);
    private MixinASTTransformation mixinTransformation = new MixinASTTransformation();
    private LogASTTransformation logTransformation = new LogASTTransformation();

    /**
     * Source code representation of what this method is doing:
     * <pre>
     * import static com.github.yihtserns.camelscript.transform.PrintlnToLogger.*;
     *
     * {@literal @}Mixin(CamelScriptCategory)
     * {@literal @}groovy.util.logging.Slf4j('log')
     * public class SCRIPT_NAME {
     *
     *      private CamelContext camelContext = new DefaultCamelContext(new ScriptBindingRegistry(this));
     *
     *      {
     *          def printToLogger = new PrintToLogger(log)
     *          metaClass.print = printToLogger.&print
     *          metaClass.printf = printToLogger.&printf
     *          metaClass.println = printToLogger.&println
     *
     *          def camelDependencyGrabber = new CamelDependencyGrabber(camelContext)
     *          metaClass.require = camelDependencyGrabber.&grab
     *
     *          CamelContextStopper.registerToShutdownHook(camelContext);
     *      }
     * }
     * </pre>
     */
    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        final ClassNode scriptClassNode = source.getAST().getScriptClassDummy();
        ScriptClassNodeTransformer transformer = new ScriptClassNodeTransformer(scriptClassNode, source);

        Expression newScriptRegistry = constructorOf(ScriptBindingRegistry.class, THIS_EXPRESSION);
        Expression newCamelContext = constructorOf(DefaultCamelContext.class, newScriptRegistry);
        FieldExpression camelContextField = new FieldExpression(
                fieldNode(CAMEL_CONTEXT_FIELD_NAME, CamelContext.class, newCamelContext));
        Expression registerToShutdownHook = staticMethodOf(
                CamelContextStopper.class, "registerToShutdownHook", camelContextField);

        scriptClassNode.addField(camelContextField.getField());
        transformer.addLogger();
        transformer.addCamelDependencyGrabber();
        transformer.mixin(CamelEngine.class);
        transformer.addToInitializerBlock(registerToShutdownHook);
    }

    private Expression constructorOf(final Class clazz, final Expression... constructorArgs) {
        return new ConstructorCallExpression(ClassHelper.make(clazz), new ArgumentListExpression(constructorArgs));
    }

    /**
     * @param fieldName name for the field
     * @param type field type
     * @param initialValueExpression initial value for the field
     * @return {@link FieldNode} of the given type and initial value
     */
    private FieldNode fieldNode(
            final String fieldName, final Class<?> type, final Expression initialValueExpression) {
        return new FieldNode(fieldName, 9, ClassHelper.make(type), null, initialValueExpression);
    }

    /**
     * Convenience method to create {@link StaticMethodCallExpression}.
     */
    private Expression staticMethodOf(final Class clazz, final String methodName, final Expression arguments) {
        return new StaticMethodCallExpression(ClassHelper.make(clazz), methodName, arguments);
    }

    private class ScriptClassNodeTransformer {

        private ClassNode scriptClassNode;
        private SourceUnit source;

        public ScriptClassNodeTransformer(final ClassNode scriptClassNode, final SourceUnit source) {
            this.scriptClassNode = scriptClassNode;
            this.source = source;
        }

        public void addLogger() {
            AnnotationNode slf4jAnnotationNode = new AnnotationNode(ClassHelper.make(Slf4j.class));
            slf4jAnnotationNode.setMember("value", new ConstantExpression(LOG_FIELD_NAME));

            logTransformation.visit(
                    new ASTNode[]{slf4jAnnotationNode, scriptClassNode},
                    source);
        }

        public void addCamelDependencyGrabber() {
            VariableExpression camelDependencyGrabberVar = new VariableExpression("camelDependencyGrabber");
            addToInitializerBlock(new DeclarationExpression(
                    camelDependencyGrabberVar,
                    EQUAL_TOKEN,
                    constructorOf(CamelDependencyGrabber.class, new VariableExpression(CAMEL_CONTEXT_FIELD_NAME))));

            copyMethodsToMetaClass(camelDependencyGrabberVar, "require");
        }

        private void copyMethodsToMetaClass(final Expression from, final String methodName) {
            Expression thisMetaClass = new PropertyExpression(THIS_EXPRESSION, "metaClass");
            Expression methodPointer = constructorOf(MethodClosure.class, from, new ConstantExpression(methodName));

            addToInitializerBlock(new BinaryExpression(
                    new PropertyExpression(thisMetaClass, methodName),
                    EQUAL_TOKEN,
                    methodPointer));
        }

        /**
         * @param categoryClass Groovy Category to be mixed into the Groovy Script
         */
        public void mixin(final Class categoryClass) {
            AnnotationNode categoryAnnotationNode = new AnnotationNode(ClassHelper.make(Mixin.class));
            categoryAnnotationNode.setMember("value", new ClassExpression(ClassHelper.make(categoryClass)));

            mixinTransformation.visit(
                    new ASTNode[]{categoryAnnotationNode, scriptClassNode},
                    source);
        }

        public void addToInitializerBlock(final Expression expression) {
            scriptClassNode.addObjectInitializerStatements(new ExpressionStatement(expression));
        }
    }
}
