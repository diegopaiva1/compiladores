package lang.compiler.ast.lvalues;

import lang.compiler.visitors.AstVisitor;

public class Identifier extends AbstractLvalue {
  private String name;

  public Identifier(int line, int column, String name) {
    super(line, column);
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

  @Override
  public String toKey(AstVisitor v) {
    return name;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitIdentifier(this);
  }

  @Override
  public Identifier getIdentifier() {
    return this;
  }
}
