package lang.compiler.ast.commands;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class Iterate extends AbstractCommand {
  private AbstractExpression expr;
  private AbstractCommand cmd;

  public Iterate(int line, int column, AbstractExpression expr, AbstractCommand cmd) {
    super(line, column);
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
  public Object accept(AstVisitor v) {
    return v.visitIterate(this);
  }

  @Override
  public String toString() {
    return "iterate (" + expr.toString() + ")" +
              cmd.toString();
  }
}
