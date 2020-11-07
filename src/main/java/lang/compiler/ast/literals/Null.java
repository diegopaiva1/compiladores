package lang.compiler.ast.literals;

import javax.lang.model.type.NullType;

public class Null extends AbstractLiteral<NullType> {
  public Null(NullType value) {
    super(value);
  }
}
