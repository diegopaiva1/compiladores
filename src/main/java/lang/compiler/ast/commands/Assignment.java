package lang.compiler.ast.commands;

import lang.compiler.AbstractExpressionEvaluatorVisitor;
import lang.compiler.ast.AbstractExpression;
import lang.compiler.ast.lvalues.AbstractLvalue;

public class Assignment extends AbstractCommand {
  private AbstractLvalue lvalue;
  private AbstractExpression expr;

  public Assignment(AbstractLvalue lvalue, AbstractExpression expr) {
    this.lvalue = lvalue;
    this.expr = expr;
  }

  public AbstractLvalue getLvalue() {
    return lvalue;
  }

  public void setLvalue(AbstractLvalue lvalue) {
    this.lvalue = lvalue;
  }

  public AbstractExpression getExpression() {
    return expr;
  }

  public void setExpression(AbstractExpression expr) {
    this.expr = expr;
  }

  @Override
  public String getName() {
    return "Assignment";
  }

  @Override
  public Boolean accept(AbstractExpressionEvaluatorVisitor v) {
    return v.visitAssignment(this);
  }
}
