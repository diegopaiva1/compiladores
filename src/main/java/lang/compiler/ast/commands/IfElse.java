package lang.compiler.ast.commands;

import lang.compiler.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;

public class IfElse extends AbstractCommand {
  private AbstractExpression expr;
  private AbstractCommand ifCmd;
  private AbstractCommand elseCmd;

  public IfElse(AbstractExpression expr, AbstractCommand ifCmd, AbstractCommand elseCmd) {
    this.expr = expr;
    this.ifCmd = ifCmd;
    this.elseCmd = elseCmd;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractCommand getIfCommand() {
    return ifCmd;
  }

  public void setIfCommand(AbstractCommand ifCmd) {
    this.ifCmd = ifCmd;
  }

  public AbstractCommand getElseCommand() {
    return elseCmd;
  }

  public void setElseCommand(AbstractCommand elseCmd) {
    this.elseCmd = elseCmd;
  }

  @Override
  public String getName() {
    return "If-Else";
  }

  @Override
  public <T> Object accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitIfElse(this);
  }
}
