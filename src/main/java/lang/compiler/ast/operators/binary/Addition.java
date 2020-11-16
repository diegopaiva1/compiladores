package lang.compiler.ast.operators.binary;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.AbstractExpression;

public class Addition extends AbstractBinaryOperator {
  private int line;
  private int column;

  public Addition(int line, int column, AbstractExpression left, AbstractExpression right) {
    super(line, column, left, right);
    this.line = line;
    this.column = column;
  }

  @Override
  public String getSymbol() {
    return "+";
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitAddition(this);
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
}
