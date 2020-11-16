package lang.compiler.ast.miscellaneous;

import java.util.List;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.lvalues.Identifier;

public class AssignableFunctionCall extends AbstractExpression {
  private Identifier id;
  private List<AbstractExpression> args;
  private AbstractExpression index;

  public AssignableFunctionCall(int line, int column, Identifier id, List<AbstractExpression> args, AbstractExpression index) {
    super(line, column);
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
  public Object accept(AstVisitor v) {
    return v.visitAssignableFunctionCall(this);
  }
}
