package lang.compiler.ast.miscellaneous;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.visitors.AbstractExpressionVisitor;

public class BalancedParenthesesExpression extends AbstractExpression {
  private AbstractExpression expr;

  public BalancedParenthesesExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public Object accept(AbstractExpressionVisitor v) {
    return v.visitBalancedParentheses(this);
  }
}
