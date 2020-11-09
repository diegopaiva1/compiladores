package lang.compiler.ast.lvalues;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;

public class ArrayAccess extends AbstractLvalue {
  private AbstractLvalue lvalue;
  private AbstractExpression expr;

  public ArrayAccess(AbstractLvalue lvalue, AbstractExpression expr) {
    this.lvalue = lvalue;
    this.expr = expr;
  }

  public AbstractLvalue getLvalue() {
    return lvalue;
  }

  public void setLvalue(AbstractLvalue lvalue) {
    this.lvalue = lvalue;
  }

  public AbstractExpression getExpr() {
    return expr;
  }

  public void setExpr(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public String getLabel() {
    return "ArrayAccess";
  }

  @Override
  public String toString() {
    Object test = expr.accept(new AbstractExpressionEvaluatorVisitor());
    return lvalue.toString() + expr.accept(new AbstractExpressionEvaluatorVisitor()).toString();
  }

  @Override
  public Object accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitArrayAccess(this);
  }
}
