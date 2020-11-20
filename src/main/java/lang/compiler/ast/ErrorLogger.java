package lang.compiler.ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lang.compiler.ast.commands.Return;
import lang.compiler.ast.commands.StaticFunctionCall;
import lang.compiler.ast.lvalues.AbstractLvalue;
import lang.compiler.ast.miscellaneous.AssignableFunctionCall;
import lang.compiler.ast.miscellaneous.Data;
import lang.compiler.ast.miscellaneous.Function;
import lang.compiler.ast.operators.binary.AbstractBinaryOperator;
import lang.compiler.ast.operators.unary.AbstractUnaryOperator;
import lang.compiler.ast.types.AbstractType;
import lang.compiler.ast.types.CustomType;

public class ErrorLogger {
  private Map<Function, List<String>> functionsLogs;
  private Map<CustomType, List<String>> dataLogs;

  public ErrorLogger() {
    functionsLogs = new LinkedHashMap<>();
    dataLogs = new LinkedHashMap<>();
  }

  public void addBinaryOperatorError(Function caller, AbstractBinaryOperator op,
                                     AbstractType leftType, AbstractType rightType) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + op.getLine() + ":" + op.getColumn() +  ": Binary operator \"" + op.getSymbol() +
      "\" does not apply to types \"" + leftType.toString() + "\" and \"" + rightType.toString() + "\""
    );
  }

  public void addUnaryOperatorError(Function caller, AbstractUnaryOperator op, AbstractType exprType) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + op.getLine() + ":" + op.getColumn() +
      ": Unary operator \"" + op.getSymbol() + "\" does not apply to type \"" + exprType.toString() + "\""
    );
  }

  public void addInvalidAssignmentError(Function caller, AbstractExpression expr,
                                        AbstractType assignedType, AbstractType lvalueType) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    String assignedTypeString = assignedType == null ? "\"null\"" : "\"" + assignedType.toString() + "\"";
    String lvalueTypeString = lvalueType == null ? "unknown" : "\"" + lvalueType.toString() + "\"";

    functionsLogs.get(caller).add(
      "\t" + expr.getLine() + ":" + expr.getColumn() + ": A value of type " + assignedTypeString +
      " cannot be assigned to an entity of type " + lvalueTypeString
    );
  }

  public void addInvalidExpressionTypeError(Function caller, AbstractExpression expr, AbstractType expectedType) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + expr.getLine() + ":" + expr.getColumn() +
      ": Expression \"" + expr.toString() + "\" does not match expected type \"" + expectedType.toString() + "\""
    );
  }

  public void addUndefinedIndexError(Function caller, AbstractExpression index) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + index.getLine() + ":" + index.getColumn() +
      ": Cannot determine value of index \"" + index.toString() + "\""
    );
  }

  public void addIndexOutOfRangeError(Function caller, AbstractExpression index) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + index.getLine() + ":" + index.getColumn() + ": Index \"" + index.toString() + "\" out of range"
    );
  }

  public void addUndefinedLvalueError(Function caller, AbstractLvalue lvalue) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + lvalue.getLine() + ":" + lvalue.getColumn() +
      ": Undefined variable \"" + lvalue.toString() + "\""
    );
  }

  public void addUndefinedArraySizeDeclarationError(Function caller, AbstractExpression sizeExpr) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + sizeExpr.getLine() + ":" + sizeExpr.getColumn() +
      ": Can not determine size \"" + sizeExpr.toString() + "\" of array declaration"
    );
  }

  public void addIncompatibleReturnSizesError(Function callee, Return returnCmd) {
    if (!functionsLogs.containsKey(callee))
      addFunctionHeader(callee);

    functionsLogs.get(callee).add(
      "\t" + returnCmd.getLine() + ":" + returnCmd.getColumn() + ": Command \"" + returnCmd.toString() + "\"" +
      " incompatible with the function definition \"" + callee.toString() + "\""
    );
  }

  public void addInvalidFunctionCallError(Function caller, AssignableFunctionCall callee) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + callee.getLine() + ":" + callee.getColumn() +
      ": Cannot resolve function call \"" + callee.toString() + "\""
    );
  }

  public void addInvalidFunctionCallError(Function caller, StaticFunctionCall callee) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(
      "\t" + callee.getLine() + ":" + callee.getColumn() +
      ": Cannot resolve function call \"" + callee.toString() + "\""
    );
  }

  public void addGenericError(Function caller, String error) {
    if (!functionsLogs.containsKey(caller))
      addFunctionHeader(caller);

    functionsLogs.get(caller).add(error);
  }

  public void addGenericError(CustomType data, String error) {
    if (!dataLogs.containsKey(data))
      dataLogs.put(data, new ArrayList<String>());

    dataLogs.get(data).add(error);
  }

  private void addFunctionHeader(Function caller) {
    List<String> logs = new ArrayList<>();
    logs.add("In function \"" + caller.toString() + "\":");
    functionsLogs.put(caller, logs);
  }

  // public void addDataLog(Data d, String error) {
  //   if (!dataLogs.containsKey(d))
  //     dataLogs.put(d, new ArrayList<String>());

  //   dataLogs.get(d).add(error);
  // }

  public boolean isEmpty() {
    return functionsLogs.isEmpty() && dataLogs.isEmpty();
  }

  public void printErrors() {
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ERROR(S) >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    for (List<String> errors : dataLogs.values()) {
      System.out.println();

      for (String error : errors)
        System.err.println(error);
    }

    for (List<String> errors : functionsLogs.values()) {
      System.out.println();

      for (String error : errors)
        System.err.println(error);
    }
  }
}
