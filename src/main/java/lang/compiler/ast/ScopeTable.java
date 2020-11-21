package lang.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class ScopeTable {
  private int scopeLevel;
  private List<SymbolTable> scopes;

  public ScopeTable() {
    scopeLevel = 0;
    scopes = new ArrayList<>();
    scopes.add(new SymbolTable());
  }

  public int getScopeLevel() {
    return scopeLevel;
  }

  public void put(String key, Symbol symbol) {
    scopes.get(scopeLevel).put(key, symbol);
  }

  public void push(SymbolTable symbolTable) {
    scopes.add(symbolTable);
    scopeLevel++;
  }

  public void pop() throws RuntimeException {
    if (scopeLevel == 0)
      throw new RuntimeException("Attempted to pop level-0 scope");

    scopes.remove(scopeLevel);
    scopeLevel--;
  }

  public Symbol search(String key) {
    int level = scopeLevel;

    while (level >= 0) {
      Symbol symbol = scopes.get(level).lookup(key);

      if (symbol != null)
        return symbol;

      level--;
    }

    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int level = 0; level < scopeLevel; level++) {
      for (int i = 0; i < level; i++)
        sb.append("\t");

      sb.append(scopes.get(level).toString());
    }

    return sb.toString();
  }
}
