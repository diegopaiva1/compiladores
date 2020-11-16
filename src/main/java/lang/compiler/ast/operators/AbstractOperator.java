package lang.compiler.ast.operators;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractOperator extends AbstractExpression {
  public AbstractOperator(int line, int column) {
    super(line, column);
  }

  public abstract String getSymbol();
}
