package lang.compiler.ast.lvalues;

import lang.compiler.visitors.AbstractExpressionVisitor;

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

  @Override
  public String toKey(AbstractExpressionVisitor v) {
    return name;
  }

  @Override
  public Object accept(AbstractExpressionVisitor v) {
    return v.visitIdentifier(this);
  }
}
