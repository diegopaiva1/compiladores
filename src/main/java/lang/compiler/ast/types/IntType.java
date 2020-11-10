package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class IntType extends AbstractType {
  public IntType() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof IntType;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitIntType(this);
  }

  @Override
  public String toString() {
    return "Int";
  }
}
