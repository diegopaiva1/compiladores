package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class FloatLiteral extends AbstractLiteral<java.lang.Float> {
  public FloatLiteral() {
    super();
  }

  public FloatLiteral(java.lang.Float value) {
    super(value);
  }

  @Override
  public java.lang.Float accept(AbstractExpressionVisitor v) {
    return v.visitFloatLiteral(this);
  }
}
