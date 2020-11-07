package lang.compiler.ast.commands;

import lang.compiler.ast.AbstractExpression;

public abstract class AbstractCommand extends AbstractExpression {
  public abstract String getName();
}
