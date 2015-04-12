package ess.common.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import ess.common.agents.EssAgent;

public abstract class Action<AGENT extends EssAgent, RESULT extends ActionResult> {

    private final AGENT agent;
    private RESULT      result;
    private final int   timeout;

    public Action(final AGENT agent) {
        this(agent, 0);
    }

    public Action(final AGENT agent, final int timeout) {
        this.agent = agent;
        this.timeout = timeout;
    }

    public AGENT getAgent() {
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
