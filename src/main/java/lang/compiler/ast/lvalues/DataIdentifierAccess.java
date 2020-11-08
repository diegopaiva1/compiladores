package lang.compiler.ast.lvalues;

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
}
