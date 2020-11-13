/**
 * Inheritance node for every other AST class.
 */

package lang.compiler.ast;

import lang.compiler.visitors.AbstractExpressionVisitor;

public abstract class AbstractExpression {
  public abstract <T>Object accept(AbstractExpressionVisitor v);
}
