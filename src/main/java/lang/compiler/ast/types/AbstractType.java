package lang.compiler.ast.types;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractType extends AbstractExpression {
  public abstract boolean match(AbstractType type);
}
