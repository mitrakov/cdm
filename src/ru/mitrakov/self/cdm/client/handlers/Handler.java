package ru.mitrakov.self.cdm.client.handlers;

import java.io.IOException;
import ru.mitrakov.self.cdm.client.json.commands.Cmd;

/**
 *
 * @author Tommy
 */
public abstract class Handler {
    
    protected Handler next, previous;
    
    public Handler(Handler parent) {
        init(parent);
    }
    
    private void init(Handler parent) {
        this.previous = parent;
        if (parent != null)
            parent.next = this;
    }
    
    public void handle(Cmd cmd) throws IOException {
        if (next != null)
            next.handle(cmd);
        else System.err.println(String.format("Cmd %s not handled", cmd));
    }
}
