package lang.compiler.ast.literals;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class BoolLiteral extends AbstractLiteral<Boolean> {
  public BoolLiteral() {
    super();
  }

  public BoolLiteral(Boolean value) {
    super(value);
  }

  @Override
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitBoolLiteral(this);
  }
}
