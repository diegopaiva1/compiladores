/**
 * Inheritance node for every other AST class.
 */

package lang.compiler.ast;

import lang.compiler.visitors.AstVisitor;

public abstract class AbstractExpression {
  private int line;
  private int column;

  public AbstractExpression(int line, int column) {
    this.line = line;
    this.column = column;
  }

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public abstract Object accept(AstVisitor v);

  @Override
  public abstract String toString();
}
