package lang.compiler.ast.commands;

import lang.compiler.visitors.AbstractExpressionVisitor;
import lang.compiler.ast.AbstractExpression;

public class If extends AbstractCommand {
  private AbstractExpression expr;
  private AbstractCommand scopeCmd;

  public If(AbstractExpression expr, AbstractCommand scopeCmd) {
    this.expr = expr;
    this.scopeCmd = scopeCmd;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractCommand getScopeCommand() {
    return scopeCmd;
  }

  public void setScopeCommand(AbstractCommand scopeCmd) {
    this.scopeCmd = scopeCmd;
  }

  @Override
  public String getName() {
    return "If";
  }

  @Override
  public Object accept(AbstractExpressionVisitor v) {
    return v.visitIf(this);
  }
}
