package lang.compiler.ast.miscellaneous;

import java.util.List;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.commands.AbstractCommand;
import lang.compiler.ast.types.AbstractType;
import lang.compiler.ast.lvalues.Identifier;

public class Function extends AbstractExpression {
  private Identifier id;
  private List<Parameter> params;
  private List<AbstractType> returnTypes;
  private List<AbstractCommand> cmds;

  public Function(Identifier id, List<Parameter> params, List<AbstractType> returnTypes, List<AbstractCommand> cmds) {
    this.id = id;
    this.params = params;
    this.returnTypes = returnTypes;
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

  public List<AbstractType> getReturnTypes() {
    return returnTypes;
  }

  public void setReturnTypes(List<AbstractType> returnTypes) {
    this.returnTypes = returnTypes;
  }

  public List<AbstractCommand> getCommands() {
    return cmds;
  }

  public void setCommands(List<AbstractCommand> cmds) {
    this.cmds = cmds;
  }

  @Override
  public List<Object> accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitFunction(this);
  }
}
