package lang.compiler.ast.operators.binary;

import lang.compiler.visitors.AbstractExpressionVisitor;
import lang.compiler.ast.AbstractExpression;

public class LessThan extends AbstractBinaryOperator {
  public LessThan(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "<";
  }

  @Override
  public Boolean accept(AbstractExpressionVisitor v) {
    return v.visitLessThan(this);
  }
}
