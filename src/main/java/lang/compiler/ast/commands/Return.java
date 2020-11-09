package lang.compiler.ast.commands;

import java.util.List;

import lang.compiler.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;

public class Return extends AbstractCommand {
  private List<AbstractExpression> exprs;

  public Return(List<AbstractExpression> exprs) {
    this.exprs = exprs;
  }

  public List<AbstractExpression> getExpressions() {
    return exprs;
  }

  public void setExpressions(List<AbstractExpression> exprs) {
    this.exprs = exprs;
  }

  @Override
  public String getName() {
    return "Return";
  }

  @Override
  public <T> Object accept(AbstractExpressionEvaluatorVisitor v) {
    // TODO Auto-generated method stub
    return null;
  }
}
