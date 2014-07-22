package aibroker.agents.manager.util;

import javax.swing.tree.DefaultMutableTreeNode;
import aibroker.model.SequenceSelector;
import aibroker.model.drivers.sql.SqlSequence;

@SuppressWarnings("serial")
public class QuotesTreeNode extends DefaultMutableTreeNode {

    private SqlSequence      sequence;
    private SequenceSelector selector;

    public QuotesTreeNode() {
        super();
    }

    public QuotesTreeNode(final Object userObject) {
        super(userObject);
    }

    public QuotesTreeNode(final Object userObject, final boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public SequenceSelector getSelector() {
        return selector;
    }

    public SqlSequence getSequence() {
        return sequence;
    }

    public void setSelector(final SequenceSelector selector) {
        this.selector = selector;
    }

    public void setSequence(final SqlSequence sequence) {
        this.sequence = sequence;
    }

}
