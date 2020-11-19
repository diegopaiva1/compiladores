package lang.compiler.ast.miscellaneous;

import java.util.List;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.commands.AbstractCommand;
import lang.compiler.ast.types.AbstractType;
import lang.compiler.ast.lvalues.Identifier;

public class Function extends AbstractExpression {
  private Identifier id;
  private List<Parameter> params;
  private List<AbstractType> returnTypes;
  private List<AbstractCommand> cmds;

  public Function(int line, int column, Identifier id, List<Parameter> params,
                  List<AbstractType> returnTypes, List<AbstractCommand> cmds) {
    super(line, column);
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
  public Object accept(AstVisitor v) {
    return v.visitFunction(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(id.toString() + "(");

    for (int i = 0; i < params.size(); i++)
      if (i == params.size() - 1)
        sb.append(params.get(i).toString());
      else
        sb.append(params.get(i).toString() + ", ");

    sb.append(")");

    if (returnTypes.size() > 0) {
      sb.append(" : ");

      for (int i = 0; i < returnTypes.size(); i++)
        if (i == returnTypes.size() - 1)
          sb.append(returnTypes.get(i).toString());
        else
          sb.append(returnTypes.get(i).toString() + ", ");
    }

    return sb.toString();
  }
}
