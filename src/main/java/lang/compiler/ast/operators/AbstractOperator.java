package lang.compiler.ast.operators;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractOperator extends AbstractExpression {
  public abstract String getSymbol();
}
