package lang.compiler.ast.commands;

import lang.compiler.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.lvalues.AbstractLvalue;

public class Read extends AbstractCommand {
  private AbstractLvalue lvalue;

  public Read(AbstractLvalue lvalue) {
    this.lvalue = lvalue;
  }

  public AbstractLvalue getLvalue() {
    return lvalue;
  }

  public void setLvalue(AbstractLvalue lvalue) {
    this.lvalue = lvalue;
  }

  @Override
  public String getName() {
    return "Read";
  }

  @Override
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitRead(this);
  }
}
