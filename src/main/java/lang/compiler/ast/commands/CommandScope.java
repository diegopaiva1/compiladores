package lang.compiler.ast.commands;

import java.util.List;

import lang.compiler.ast.Scope;
import lang.compiler.visitors.AstVisitor;

public class CommandScope extends AbstractCommand {
  private Scope scope;
  private List<AbstractCommand> cmds;

  public CommandScope(int line, int column, List<AbstractCommand> cmds) {
    super(line, column);
    this.cmds = cmds;
  }

  public List<AbstractCommand> getCommands() {
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

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }
}
