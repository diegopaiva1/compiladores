package lang.compiler.ast.literals;

import lang.compiler.visitors.AstVisitor;

public class BoolLiteral extends AbstractLiteral<Boolean> {
  public BoolLiteral() {
    super();
  }

  public BoolLiteral(Boolean value) {
    super(value);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitBoolLiteral(this) ;
  }
}
