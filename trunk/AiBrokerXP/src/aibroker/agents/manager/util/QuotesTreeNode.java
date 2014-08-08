package aibroker.agents.manager.util;

import javax.swing.tree.DefaultMutableTreeNode;
import aibroker.model.SeqSel;
import aibroker.model.drivers.sql.SqlSeq;

@SuppressWarnings("serial")
public class QuotesTreeNode extends DefaultMutableTreeNode {

    private SqlSeq      sequence;
    private SeqSel selector;

    public QuotesTreeNode() {
        super();
    }

    public QuotesTreeNode(final Object userObject) {
        super(userObject);
    }

    public QuotesTreeNode(final Object userObject, final boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public SeqSel getSelector() {
        return selector;
    }

    public SqlSeq getSequence() {
        return sequence;
    }

    public void setSelector(final SeqSel selector) {
        this.selector = selector;
    }

    public void setSequence(final SqlSeq sequence) {
        this.sequence = sequence;
    }

}
