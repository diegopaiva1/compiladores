package lang.compiler.ast.commands;

import java.util.List;

import lang.compiler.visitors.AstVisitor;

public class CommandScope extends AbstractCommand {
  private List<AbstractCommand> cmds;

  public CommandScope(int line, int column, List<AbstractCommand> cmds) {
    super(line, column);
    this.cmds = cmds;
  }

  public List<AbstractCommand> getCommmands() {
    return cmds;
  }

  public void setCommands(List<AbstractCommand> cmds) {
    this.cmds = cmds;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitCommandScope(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{\n");

    for (AbstractCommand cmd : cmds)
      sb.append(cmd.toString() + ";\n");

    sb.append("}\n");
    return sb.toString();
  }
}
