routes {
      from('direct:input').transform(constant(expectedResult))
}