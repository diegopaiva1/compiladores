package lang.compiler.ast.commands;

import lang.compiler.ast.AbstractExpression;

public class Iterate extends AbstractCommand {
  private AbstractExpression expr;
  private AbstractCommand cmd;

  public Iterate(AbstractExpression expr, AbstractCommand cmd) {
    this.expr = expr;
    this.cmd = cmd;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractCommand getCommand() {
    return cmd;
  }

  public void setCommand(AbstractCommand cmd) {
    this.cmd = cmd;
  }

  @Override
  public String getName() {
    return "Iterate";
  }
}
