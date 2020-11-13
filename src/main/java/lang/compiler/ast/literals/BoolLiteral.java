package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class BoolLiteral extends AbstractLiteral<Boolean> {
  public BoolLiteral() {
    super();
  }

  public BoolLiteral(Boolean value) {
    super(value);
  }

  @Override
  public Boolean accept(AbstractExpressionVisitor v) {
    return v.visitBoolLiteral(this);
  }
}
