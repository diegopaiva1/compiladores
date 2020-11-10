package lang.compiler.ast.miscellaneous;

import java.util.List;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.lvalues.Identifier;

public class AssignableFunctionCall extends AbstractExpression {
  private Identifier id;
  private List<AbstractExpression> args;
  private AbstractExpression index;

  public AssignableFunctionCall(Identifier id, List<AbstractExpression> args, AbstractExpression index) {
    this.id = id;
    this.args = args;
    this.index = index;
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

  public AbstractExpression getIndex() {
    return index;
  }

  public void setIndex(AbstractExpression index) {
    this.index = index;
  }

  @Override
  public Object accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitAssignableFunctionCall(this);
  }
}
