package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class FloatLiteral extends AbstractLiteral<java.lang.Float> {
  public FloatLiteral() {
    super();
  }

  public FloatLiteral(java.lang.Float value) {
    super(value);
  }

  @Override
  public java.lang.Float accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitFloatLiteral(this);
  }
}
