package ess.mg.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ess.mg.agents.Agent;

public abstract class Action<RESULT> {

    private final Agent agent;
    private final int   timeout;
    private RESULT      result;

    public Action(final Agent performer) {
        this(performer, 0);
    }

    public Action(final Agent performer, final int timeout) {
        this.agent = performer;
        this.timeout = timeout;
    }

    public Agent getAgent() {
        return agent;
    }

    public RESULT getResult() {
        return result;
    }

    public int getTimeout() {
        return timeout;
    }

    public RESULT perform() {
        result = null;
        Timer timer = null;
        if (timeout > 0) {
            timer = new Timer(timeout, new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    agent.onActionTimeout(Action.this);
                }

            });
            timer.setRepeats(false);
            timer.start();
        }
        result = execute();
        if (timer != null) {
            timer.stop();
        }
        return result;
    }

    protected abstract RESULT execute();

}
