package lang.compiler.ast.commands;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class IfElse extends AbstractCommand {
  private AbstractExpression expr;
  private AbstractCommand ifScopeCmd;
  private AbstractCommand elseScopeCmd;

  public IfElse(int line, int column, AbstractExpression expr, AbstractCommand ifScopeCmd, AbstractCommand elseScopeCmd) {
    super(line, column);
    this.expr = expr;
    this.ifScopeCmd = ifScopeCmd;
    this.elseScopeCmd = elseScopeCmd;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  public AbstractCommand getIfScopeCommand() {
    return ifScopeCmd;
  }

  public void setIfScopeCommand(AbstractCommand ifScopeCmd) {
    this.ifScopeCmd = ifScopeCmd;
  }

  public AbstractCommand getElseScopeCommand() {
    return elseScopeCmd;
  }

  public void setElseScopeCommand(AbstractCommand elseScopeCmd) {
    this.elseScopeCmd = elseScopeCmd;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitIfElse(this);
  }

  @Override
  public String toString() {
    return "if (" + expr.toString() + ") " +
              ifScopeCmd.toString() +
           "else " +
              elseScopeCmd.toString();
  }
}
