package lang.compiler.ast.commands;

import lang.compiler.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;

public class Print extends AbstractCommand {
  private AbstractExpression expr;

  public Print(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public String getName() {
    return "Print";
  }

  @Override
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitPrint(this);
  }
}
