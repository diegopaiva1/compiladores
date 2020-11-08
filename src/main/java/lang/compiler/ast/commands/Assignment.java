package lang.compiler.ast.commands;

import lang.compiler.ast.AbstractExpression;

public class Assignment extends AbstractCommand {
    //private AbstractExpression left;
    private AbstractExpression right;

    @Override
    public String getName() {
        return "Assignment";
    }
}
