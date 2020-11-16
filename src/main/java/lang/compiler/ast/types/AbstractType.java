package lang.compiler.ast.types;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractType extends AbstractExpression {
  public AbstractType(int line, int column) {
    super(line, column);
  }

  public abstract boolean match(AbstractType type);
}
