package lang.compiler.ast.commands;

import java.util.List;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.visitors.AstVisitor;

public class Return extends AbstractCommand {
  private List<AbstractExpression> exprs;

  public Return(int line, int column, List<AbstractExpression> exprs) {
    super(line, column);
    this.exprs = exprs;
  }

  public List<AbstractExpression> getExpressions() {
    return exprs;
  }

  public void setExpressions(List<AbstractExpression> exprs) {
    this.exprs = exprs;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitReturn(this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("return ");

    for (int i = 0; i < exprs.size(); i++)
      if (i == exprs.size() - 1)
        sb.append(exprs.get(i).toString());
      else
        sb.append(exprs.get(i).toString() + ", ");

    return sb.toString();
  }
}
