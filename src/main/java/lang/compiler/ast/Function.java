package lang.compiler.ast;

import java.util.List;

import lang.compiler.ast.commands.AbstractCommand;

public class Function extends AbstractExpression {
  private String id;
  private List<Parameter> parameters;
  private List<AbstractCommand> commands;

  public Function(String id, List<Parameter> parameters, List<AbstractCommand> commands) {
    this.id = id;
    this.parameters = parameters;
    this.commands = commands;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

  public List<AbstractCommand> getCommands() {
    return commands;
  }

  public void setCommands(List<AbstractCommand> commands) {
    this.commands = commands;
  }
}
