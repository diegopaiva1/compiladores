package lang.compiler;

import org.stringtemplate.v4.ST;

import lang.compiler.parser.*;
import lang.compiler.visitors.JavaVisitor;
import lang.compiler.ast.Program;

public class App {
  // Recupera o nome base (sem extensão) de um arquivo.
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Lang compiler v 0.0.1 - Maio de 2020");
      System.out.println("Use java -cp . Lang ação <Caminho para código Fonte> ");
      System.out.println("Ação (uma das seguintes possibilidades): ");

      System.out.println(" -bs : Executa uma bateria de testes sintáticos");
      System.out.println(" -bty : Executa uma bateria de testes no sistemas de tipos");
      System.out.println(" -bsm : Executa uma bateria de testes no interpretador");

      System.out.println(" -pp: Pretty print program.");
      System.out.println(" -tp: Verficar tipos e imprimir o ambiente de tipos");
      System.out.println(" -i : Apenas interpretar");

      System.out.println(" -ti: Verificar tipos e depois interpretar");
      System.out.println(" -dti: Verificar tipos, imprimir o ambiente de tipos e depois interpretar");
      System.out.println(" -gvz: Create a dot file. (Feed it to graphviz dot tool to generate graphical representation of the AST)");
    }
    try {
      ParseAdaptor langParseAdaptor = new LangParseAdaptor();

      if (args[0].equals("-bs")) {
        System.out.println("Executando bateria de testes sintáticos:");
        TestParser tp = new TestParser(langParseAdaptor);
        return;
      }
      if (args[0].equals("-byt")) {
        System.out.println("Executando bateria de testes sintáticos:");
        TestParser tp = new TestParser(langParseAdaptor);
        return;
      }
      if (args[0].equals("-bsm")) {
        System.out.println("Executando bateria de testes sintáticos:");
        // TestParser tp = new TestParser(langParseAdaptor);
        return;
      }
      if (args.length != 2) {
        System.out.println("Para usar essa opção, especifique um nome de arquivo");
        return;
      }

      Program prog = langParseAdaptor.parseFile(args[1]);

      if (prog == null) {
        System.err.println("Aborting due to syntax error(s)");
        System.exit(1);
      }
      else if (args[0].equals("-i")) {
        prog.interpret();
      }
      else if (args[0].equals("-ii")) {
        // iv = new InteractiveInterpreterVisitor();
        // result.accept(iv);
      }
      else if (args[0].equals("-ti")) {
        if (prog.good()) {
          prog.interpret();
        }
        else {
          System.err.println("Aborting due to type error(s)");
          System.exit(1);
        }
      }
      else if (args[0].equals("-tp")) {
        if (!prog.good()) {
          System.err.println("Aborting due to type error(s)");
          System.exit(1);
        }
      }
      else if (args[0].equals("-g")) {
        if (prog.good()) {
          new JavaVisitor().visitProgram(prog);
        }
        else {
          System.err.println("Aborting due to type error(s)");
          System.exit(1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
