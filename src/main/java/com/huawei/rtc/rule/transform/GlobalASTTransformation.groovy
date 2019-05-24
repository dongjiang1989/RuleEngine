package com.huawei.rtc.rule.transform

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * Created by dongjiang on 2019/5/24.
 */

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class GlobalASTTransformation implements ASTTransformation {

    private RuleASTTransformation transformer = new RuleASTTransformation();

    public void visit(final ASTNode[] nodes, final SourceUnit source) {
        if (source.getName().endsWith(".camel")) {
            transformer.visit(nodes, source);
        }
    }
}
