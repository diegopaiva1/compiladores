package lang.compiler.ast;

import lang.compiler.ast.types.AbstractType;

public class Instantiation extends AbstractExpression {
  private AbstractType type;
  private AbstractExpression expr;

  public Instantiation(AbstractType type, AbstractExpression expr) {
    this.type = type;
    this.expr = expr;
  }

  public AbstractType getType() {
    return type;
  }

  public void setType(AbstractType type) {
    this.type = type;
  }

  public AbstractExpression getExpr() {
    return expr;
  }

  public void setExpr(AbstractExpression expr) {
    this.expr = expr;
  }
}
