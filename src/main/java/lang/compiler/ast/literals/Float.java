package lang.compiler.ast.literals;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

public class Float extends AbstractLiteral<java.lang.Float> {
  public Float(java.lang.Float value) {
    super(value);
  }

  @Override
  public java.lang.Float accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitFloat(this);
  }
}
