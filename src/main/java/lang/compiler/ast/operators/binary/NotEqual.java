package lang.compiler.ast.operators.binary;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;

public class NotEqual extends AbstractBinaryOperator {
  public NotEqual(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "!=";
  }

  @Override
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitNotEqual(this);
  }
}
