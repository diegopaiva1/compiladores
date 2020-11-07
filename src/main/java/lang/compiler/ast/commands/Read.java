package lang.compiler.ast.commands;

import lang.compiler.ast.AbstractExpression;

public class Read extends AbstractCommand {
  private AbstractExpression expr;

  public Read(AbstractExpression expr) {
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
    return "Read";
  }
}
