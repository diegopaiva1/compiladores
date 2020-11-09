package lang.compiler.ast;

import lang.compiler.AbstractExpressionEvaluatorVisitor;

public abstract class AbstractExpression {
  public abstract <T>Object accept(AbstractExpressionEvaluatorVisitor v);
}
