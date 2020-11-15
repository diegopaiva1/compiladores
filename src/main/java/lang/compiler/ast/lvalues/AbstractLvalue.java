package lang.compiler.ast.lvalues;

import lang.compiler.ast.AbstractExpression;
import lang.compiler.visitors.AstVisitor;

public abstract class AbstractLvalue extends AbstractExpression {
    public abstract String getLabel();
    public abstract Identifier getIdentifier();
    public abstract String toKey(AstVisitor v);
}
