routes {
      from('direct:input').transform(constant('mock:out'))
}