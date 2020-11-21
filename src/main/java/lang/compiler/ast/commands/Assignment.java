package lang.compiler.ast.commands;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.lvalues.AbstractLvalue;

public class Assignment extends AbstractCommand {
  private AbstractLvalue lvalue;
  private AbstractExpression expr;

  public Assignment(int line, int column, AbstractLvalue lvalue, AbstractExpression expr) {
    super(line, column);
    this.lvalue = lvalue;
    this.expr = expr;
  }

  public AbstractLvalue getLvalue() {
    return lvalue;
  }

  public void setLvalue(AbstractLvalue lvalue) {
    this.lvalue = lvalue;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitAssignment(this);
  }

  @Override
  public String toString() {
    return lvalue.toString() + " = " + expr.toString();
  }
}
