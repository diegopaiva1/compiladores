package lang.compiler.ast.lvalues;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class ArrayAccess extends AbstractLvalue {
  private AbstractLvalue lvalue;
  private AbstractExpression expr;

  public ArrayAccess(int line, int column, AbstractLvalue lvalue, AbstractExpression expr) {
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
  public String getLabel() {
    return "ArrayAccess";
  }

  @Override
  public String toKey(AstVisitor v) {
    return lvalue.toKey(v) + expr.accept(v).toString();
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitArrayAccess(this);
  }

  @Override
  public Identifier getIdentifier() {
    return lvalue.getIdentifier();
  }

  @Override
  public String toString() {
    return lvalue.toString() + "[" + expr.toString() + "]";
  }
}
