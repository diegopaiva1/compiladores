package lang.compiler.ast.commands;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractCommand extends AbstractExpression {
  public AbstractCommand(int line, int column) {
    super(line, column);
  }
}
