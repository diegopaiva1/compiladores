package lang.compiler.ast.literals;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractLiteral<T> extends AbstractExpression {
  private T value;

  public AbstractLiteral(int line, int column, T value) {
    super(line, column);
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }
}
