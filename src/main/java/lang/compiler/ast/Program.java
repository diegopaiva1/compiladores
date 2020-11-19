/**
 * Class containing the AST representation of a successfuly parsed program.
 */

package lang.compiler.ast;

import java.util.Set;
import java.util.HashSet;

import lang.compiler.ast.miscellaneous.Data;
import lang.compiler.ast.miscellaneous.Function;
import lang.compiler.visitors.InterpretorVisitor;
import lang.compiler.visitors.ScopeVisitor;
import lang.compiler.visitors.TypeCheckVisitor;

public class Program {
  private ErrorLogger logger;
  private Set<Function> functionSet;
  private Set<Data> dataSet;
  public boolean isWellTyped;

  public Program() {
    logger = new ErrorLogger();
    functionSet = new HashSet<>();
    dataSet = new HashSet<>();
    isWellTyped = false;
  }

  public void addFunction(Function f) {
    functionSet.add(f);
  }

  public void addData(Data d) {
    dataSet.add(d);
  }

  public Set<Function> getFunctionSet() {
    return functionSet;
  }

  public Set<Data> getDataSet() {
    return dataSet;
  }

  public void interpret() {
    new InterpretorVisitor().visitProgram(this);
  }

  public void checkTypes() {
    new ScopeVisitor(logger).visitProgram(this);

    if (logger.isEmpty())
      new TypeCheckVisitor(logger).visitProgram(this);

    if (!logger.isEmpty())
      logger.printErrors();
  }

  public void checkScopes() {
    new ScopeVisitor(logger).visitProgram(this);
    logger.printErrors();
  }
}
