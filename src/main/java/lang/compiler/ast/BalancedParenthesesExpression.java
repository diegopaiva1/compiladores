package lang.compiler.ast;

public class BalancedParenthesesExpression extends AbstractExpression{
  private AbstractExpression expr;

  public BalancedParenthesesExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractExpression getExpr() {
    return expr;
  }

  public void setExpr(AbstractExpression expr) {
    this.expr = expr;
  }
}
