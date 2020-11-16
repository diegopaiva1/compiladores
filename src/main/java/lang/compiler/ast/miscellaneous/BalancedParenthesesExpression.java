package lang.compiler.ast.miscellaneous;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.visitors.AstVisitor;

public class BalancedParenthesesExpression extends AbstractExpression {
  private AbstractExpression expr;

  public BalancedParenthesesExpression(int line, int column, AbstractExpression expr) {
    super(line, column);
    this.expr = expr;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitBalancedParentheses(this);
  }
}
