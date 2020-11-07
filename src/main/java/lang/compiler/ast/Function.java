package lang.compiler.ast;

import java.util.List;

import lang.compiler.ast.commands.AbstractCommand;

public class Function extends AbstractExpression {
  private String id;
  private List<Parameter> params;
  private List<AbstractCommand> cmds;

  public Function(String id, List<Parameter> params, List<AbstractCommand> cmds) {
    this.id = id;
    this.params = params;
    this.cmds = cmds;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
