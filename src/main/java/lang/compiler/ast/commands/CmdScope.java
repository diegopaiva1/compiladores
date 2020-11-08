package lang.compiler.ast.commands;

import java.util.List;

public class CmdScope extends AbstractCommand {
    private List<AbstractCommand> cmds;

    public CmdScope(List<AbstractCommand> cmds) {
        this.cmds = cmds;
    }

    public List<AbstractCommand> getCmds() {
        return cmds;
    }

    public void setCmds(List<AbstractCommand> cmds) {
        this.cmds = cmds;
    }

    @Override
    public String getName() {
        return "CmdScope";
    }
}
