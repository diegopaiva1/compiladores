package lang.compiler.ast.commands;

import java.util.List;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.lvalues.AbstractLvalue;
import lang.compiler.ast.lvalues.Identifier;

public class StaticFunctionCall extends AbstractCommand {
  private Identifier id;
  private List<AbstractExpression> args;
  private List<AbstractLvalue> lvalues;

  public StaticFunctionCall(int line, int column, Identifier id, List<AbstractExpression> args, List<AbstractLvalue> lvalues) {
    super(line, column);
    this.id = id;
    this.args = args;
    this.lvalues = lvalues;
  }

  public Identifier getId() {
    return id;
  }

  public void setId(Identifier id) {
    this.id = id;
  }

  public List<AbstractExpression> getArgs() {
    return args;
  }

  public void setArgs(List<AbstractExpression> args) {
    this.args = args;
  }

  public List<AbstractLvalue> getLvalues() {
    return lvalues;
  }

  public void setLvalues(List<AbstractLvalue> lvalues) {
    this.lvalues = lvalues;
  }

  @Override
  public String getName() {
    return "Static Function Call";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitStaticFunctionCall(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(id.toString() + "(");

    for (int i = 0; i < args.size(); i++)
      if (i == args.size() - 1)
        sb.append(args.get(i).toString() + ")");
      else
        sb.append(args.get(i).toString() + ", ");

    if (lvalues.size() > 0) {
      sb.append("<");

      for (int i = 0; i < lvalues.size(); i++)
        if (i == lvalues.size() - 1)
          sb.append(lvalues.get(i).toString() + ">");
        else
          sb.append(lvalues.get(i).toString() + ", ");
    }

    return sb.toString();
  }
}
