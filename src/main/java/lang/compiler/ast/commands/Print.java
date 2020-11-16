package lang.compiler.ast.commands;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class Print extends AbstractCommand {
  private AbstractExpression expr;

  public Print(int line, int column, AbstractExpression expr) {
    super(line, column);
    this.expr = expr;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public String getName() {
    return "print";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitPrint(this);
  }
}
