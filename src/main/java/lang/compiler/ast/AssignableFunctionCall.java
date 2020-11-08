package lang.compiler.ast;

import java.util.List;

public class AssignableFunctionCall extends AbstractExpression {
  private String id;
  private List<AbstractExpression> args;
  private AbstractExpression index;

  public AssignableFunctionCall(String id, List<AbstractExpression> args, AbstractExpression index) {
    this.id = id;
    this.args = args;
    this.index = index;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
}
