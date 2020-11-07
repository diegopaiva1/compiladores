package lang.compiler.parser;

import lang.compiler.ast.Program;

// Adaptador para classe de parser. a Função parseFile deve retornar null caso o parser resulte em erro.
public interface ParseAdaptor {
  public abstract Program parseFile(String path);
}
