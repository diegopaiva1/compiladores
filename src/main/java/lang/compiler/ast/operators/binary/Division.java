package lang.compiler.ast.operators.binary;

import lang.compiler.visitors.AbstractExpressionVisitor;
import lang.compiler.ast.AbstractExpression;

public class Division extends AbstractBinaryOperator {
  public Division(AbstractExpression left, AbstractExpression right) {
    super(left, right);
  }

  @Override
  public String getSymbol() {
    return "/";
  }

  @Override
  public Number accept(AbstractExpressionVisitor v) {
    return v.visitDivision(this);
  }
}
