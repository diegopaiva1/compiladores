package lang.compiler.ast;

import java.util.List;

import lang.compiler.ast.commands.AbstractCommand;
import lang.compiler.ast.lvalues.Identifier;

public class Function extends AbstractExpression {
  private Identifier id;
  private List<Parameter> params;
  private List<AbstractCommand> cmds;

  public Function(Identifier id, List<Parameter> params, List<AbstractCommand> cmds) {
    this.id = id;
    this.params = params;
    this.cmds = cmds;
  }

  public Identifier getId() {
    return id;
  }

  public void setId(Identifier id) {
    this.id = id;
  }

  public List<Parameter> getParameters() {
    return params;
  }

  public void setParameters(List<Parameter> params) {
    this.params = params;
  }

  public List<AbstractCommand> getCommands() {
    return cmds;
  }

  public void setCommands(List<AbstractCommand> cmds) {
    this.cmds = cmds;
  }
}
