/**
 * Class containing the AST representation of a successfuly parsed program.
 */

package lang.compiler.ast;

import java.util.List;
import java.util.ArrayList;

import lang.compiler.visitors.InterpretorVisitor;
import lang.compiler.visitors.TypeCheckVisitor;

public class Program {
  public boolean isWellTyped;
  private List<AbstractExpression> exprs;

  public Program() {
    isWellTyped = false;
    exprs = new ArrayList<>();
  }

  public void addExpression(AbstractExpression expr) {
    exprs.add(expr);
  }

  public List<AbstractExpression> getExpressions() {
    return exprs;
  }

  public void interpret() {
    new InterpretorVisitor().visitProgram(this);
  }

  public void checkTypes() {
    isWellTyped = new TypeCheckVisitor().visitProgram(this);
  }
}
