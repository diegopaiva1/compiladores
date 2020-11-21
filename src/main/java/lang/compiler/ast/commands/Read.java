package lang.compiler.ast.commands;

import lang.compiler.visitors.AstVisitor;
import lang.compiler.ast.lvalues.AbstractLvalue;

public class Read extends AbstractCommand {
  private AbstractLvalue lvalue;

  public Read(int line, int column, AbstractLvalue lvalue) {
    super(line, column);
    this.lvalue = lvalue;
  }

  public AbstractLvalue getLvalue() {
    return lvalue;
  }

  public void setLvalue(AbstractLvalue lvalue) {
    this.lvalue = lvalue;
  }

  @Override
  public Object accept(AstVisitor v) {
    return v.visitRead(this);
  }

  @Override
  public String toString() {
    return "read " + lvalue.toString();
  }
}
