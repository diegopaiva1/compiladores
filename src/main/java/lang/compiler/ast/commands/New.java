package lang.compiler.ast.commands;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.types.AbstractType;

public class New extends AbstractCommand {
  private AbstractType type;
  private AbstractExpression expr;

  public New(int line, int column, AbstractType type, AbstractExpression expr) {
    super(line, column);
    this.type = type;
    this.expr = expr;
  }

  public AbstractType getType() {
    return type;
  }

  public void setType(AbstractType type) {
    this.type = type;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitNew(this);
  }

  @Override
  public String toString() {
    return "new" + type.toString() + "[" + expr.toString() + "]";
  }
}
