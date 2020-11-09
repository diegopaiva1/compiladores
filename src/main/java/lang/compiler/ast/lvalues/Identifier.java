package lang.compiler.ast.lvalues;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

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
  public String toString() {
    return name;
  }

  @Override
  public Object accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitIdentifier(this);
  }
}
