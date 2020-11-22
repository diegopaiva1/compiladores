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
  private Set<Function> functionSet;
  private Set<Data> dataSet;
  private ErrorLogger logger;
  private SymbolTable symbolTable;

  public Program() {
    symbolTable = new SymbolTable();
    logger = new ErrorLogger();
    functionSet = new HashSet<>();
    dataSet = new HashSet<>();
  }

  public void interpret() {
    new InterpretorVisitor().visitProgram(this);
  }

  public boolean good() {
    new ScopeVisitor().visitProgram(this);
    System.out.println(symbolTable.toString());

    if (logger.isEmpty())
      new TypeCheckVisitor(logger).visitProgram(this);

    if (!logger.isEmpty()) {
      System.err.println(logger.toString());
      return false;
    }

    return true;
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

  public ErrorLogger getLogger() {
    return logger;
  }

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }
}
