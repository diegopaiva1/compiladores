package lang.compiler.ast.lvalues;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class DataIdentifierAccess extends AbstractLvalue {
  private AbstractLvalue lvalue;
  private Identifier id;

  public DataIdentifierAccess(AbstractLvalue lvalue, Identifier id) {
    this.lvalue = lvalue;
    this.id = id;
  }

  public AbstractLvalue getLvalue() {
    return lvalue;
  }

  public void setLvalue(AbstractLvalue lvalue) {
    this.lvalue = lvalue;
  }

  public Identifier getId() {
    return id;
  }

  public void setId(Identifier id) {
    this.id = id;
  }

  @Override
  public String getLabel() {
    return "DataIdentifierAccess";
  }

  @Override
  public String toKey(AbstractExpressionEvaluatorVisitor v) {
    return lvalue.toKey(v) + id.toKey(v);
  }

  @Override
  public Object accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitDataIdentifierAccess(this);
  }
}
