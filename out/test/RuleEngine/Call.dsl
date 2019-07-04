import org.apache.camel.RoutesBuilder
from('direct:input').choice().
    when(body().isEqualTo(constant('aabb')))
         .setHeader(Exchange.HTTP_METHOD, constant("GET"))
         .setHeader(Exchange.HTTP_QUERY, constant("wd="+body().toString()))
         .to("ahc:http://www.baidu.com")
         .to("mock:out")
    .otherwise()
         .to("mock:results")
    .endChoice()

