package lang.compiler.ast.commands;

import java.util.List;

import lang.compiler.visitors.AbstractExpressionVisitor;

public class CommandScope extends AbstractCommand {
  private List<AbstractCommand> cmds;

  public CommandScope(List<AbstractCommand> cmds) {
    this.cmds = cmds;
  }

  public List<AbstractCommand> getCommmands() {
    return cmds;
  }

  public void setCommands(List<AbstractCommand> cmds) {
    this.cmds = cmds;
  }

  @Override
  public String getName() {
    return "CmdScope";
  }

  @Override
  public Object accept(AbstractExpressionVisitor v) {
    return v.visitCommandScope(this);
  }
}
