package lang.compiler.ast.types;

import lang.compiler.visitors.AbstractExpressionEvaluatorVisitor;

public class TypeInt extends AbstractType {
  public TypeInt() {
  }

  @Override
  public boolean match(AbstractType type) {
    return type instanceof TypeInt;
  }

  @Override
  public Void accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitTypeInt(this);
  }

  @Override
  public String toString() {
    return "Int";
  }
}
