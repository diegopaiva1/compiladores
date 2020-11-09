package lang.compiler.ast.types;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

public class Array extends AbstractType {
  private AbstractType type;

  public Array(AbstractType type) {
    this.type = type;
  }

  public AbstractType getType() {
    return type;
  }

  public void setType(AbstractType type) {
    this.type = type;
  }

  @Override
  public String getName() {
    return "Array of " + type.getName();
  }

  @Override
  public <T> Object accept(AbstractExpressionEvaluatorVisitor v) {
    // TODO Auto-generated method stub
    return null;
  }
}
