package lang.compiler.ast.types;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

public class BasicType extends AbstractType {
  private String name;

  public BasicType(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public <T> Object accept(AbstractExpressionEvaluatorVisitor v) {
    // TODO Auto-generated method stub
    return null;
  }
}
