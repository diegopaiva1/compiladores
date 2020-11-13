package lang.compiler.ast.operators.binary;

import lang.compiler.visitors.AbstractExpressionVisitor;
import lang.compiler.ast.AbstractExpression;

public class And extends AbstractBinaryOperator {
  public And(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "&&";
  }

  @Override
  public Boolean accept(AbstractExpressionVisitor v) {
    return v.visitAnd(this);
  }
}
