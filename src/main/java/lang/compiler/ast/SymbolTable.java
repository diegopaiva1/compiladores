package lang.compiler.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import lang.compiler.ast.miscellaneous.Function;

public class SymbolTable {
  private Map<Function, Scope> allScopes;
  private Stack<Scope> scopeStack;

  public SymbolTable() {
    allScopes = new HashMap<>();
    scopeStack = new Stack<>();
    Scope global = new Scope(null);
    scopeStack.push(global);
  }

  public void pushScope(Function f) {
    Scope enclosing = scopeStack.peek();
    Scope scope = new Scope(enclosing);
    allScopes.put(f, scope);
    scopeStack.push(scope);
  }

  public void popScope() {
    scopeStack.pop();
  }

  public Symbol search(String key) {
    return scopeStack.peek().lookup(key);
  }

  public void put(String key) {
    scopeStack.peek().put(key);
  }

  @Override
  public String toString() {
    return Integer.toString(allScopes.size());
  }
}
