package lang.compiler.ast.lvalues;

import lang.compiler.visitors.AstVisitor;

public class DataIdentifierAccess extends AbstractLvalue {
  private Identifier id;
  private AbstractLvalue lvalue;

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
  public String toKey(AstVisitor v) {
    return lvalue.toKey(v) + id.toKey(v);
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitDataIdentifierAccess(this);
  }

  @Override
  public Identifier getIdentifier() {
    return lvalue.getIdentifier();
  }
}
