/**
 * Inheritance node for every other AST class.
 */

package lang.compiler.ast;

import lang.compiler.visitors.AstVisitor;

public abstract class AbstractExpression {
  public abstract Object accept(AstVisitor v);
}
