package lang.compiler.ast.lvalues;

public class Identifier extends AbstractLvalue {
  private String name;

  public Identifier(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getLabel() {
    return "Identifier";
  }
}
