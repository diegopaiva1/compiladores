package lang.compiler.ast.types;

public class BasicType extends AbstractType {
  private String name;

  public BasicType(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }
}
