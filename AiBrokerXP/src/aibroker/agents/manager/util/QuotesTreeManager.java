package aibroker.agents.manager.util;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import aibroker.model.Sequence;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.SqlDatabase;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.model.drivers.sql.VirtualSqlSequence;

public class QuotesTreeManager {

    public static TreePath addSequence(final JTree tree, final SqlSequence sequence) {
        final TreeModel model = tree.getModel();
        final QuotesTreeNode sequenceNode = addSequence(model, sequence);
        tree.setModel(buildDefaultTreeModel());
        tree.setModel(model);
        return new TreePath(sequenceNode.getPath());
    }

    public static TreeModel buildDefaultTreeModel() {
        final QuotesTreeNode root = new QuotesTreeNode("Quotes");
        return new DefaultTreeModel(root);
    }

    public static void expandDefault(final JTree tree) {
        final TreeModel model = tree.getModel();
        final QuotesTreeNode root = (QuotesTreeNode) model.getRoot();
        expandDefault(tree, root);
    }

    public static TreeModel readSequences(final SqlDatabase qdb) {
        final TreeModel model = buildDefaultTreeModel();
        if (qdb != null) {
            for (final Sequence it : qdb) {
                final SqlSequence sequence = (SqlSequence) it;
                addSequence(model, sequence);
                if (Feed.ORIG == sequence.getFeed()) {
                    for (final Sampling smp : Sampling.values()) {
                        if (sequence.getSampling().compareTo(smp) < 0) {
                            final VirtualSqlSequence vSequence = sequence.cloneVirtual();
                            vSequence.setSampling(smp);
                            addSequence(model, vSequence);
                        }
                    }
                }
            }
        }
        return model;
    }

    public static void selectRoot(final JTree tree) {
        final TreePath rootPath = new TreePath(((QuotesTreeNode) tree.getModel().getRoot()).getPath());
        tree.setSelectionPath(rootPath);
    }

    private static QuotesTreeNode addSequence(final TreeModel model, final SqlSequence sequence) {
        final QuotesTreeNode root = (QuotesTreeNode) model.getRoot();

        QuotesTreeNode market = null;
        for (int i = 0; i < root.getChildCount(); ++i) {
            final QuotesTreeNode tmp = (QuotesTreeNode) root.getChildAt(i);
            if (sequence.getMarket().equals(tmp.getUserObject())) {
                market = tmp;
            }
        }
        if (market == null) {
            market = new QuotesTreeNode(sequence.getMarket());
            root.add(market);
        }

        QuotesTreeNode feed = null;
        for (int i = 0; i < market.getChildCount(); ++i) {
            final QuotesTreeNode tmp = (QuotesTreeNode) market.getChildAt(i);
            if (sequence.getFeed().equals(tmp.getUserObject())) {
                feed = tmp;
            }
        }
        if (feed == null) {
            feed = new QuotesTreeNode(sequence.getFeed());
            market.add(feed);
        }

        QuotesTreeNode sampling = null;
        for (int i = 0; i < feed.getChildCount(); ++i) {
            final QuotesTreeNode tmp = (QuotesTreeNode) feed.getChildAt(i);
            if (sequence.getSampling().equals(tmp.getUserObject())) {
                sampling = tmp;
            }
        }
        if (sampling == null) {
            sampling = new QuotesTreeNode(sequence.getSampling());
            feed.add(sampling);
        }

        QuotesTreeNode symbol = null;
        for (int i = 0; i < sampling.getChildCount(); ++i) {
            final QuotesTreeNode tmp = (QuotesTreeNode) sampling.getChildAt(i);
            if (sequence.getSymbol().equals(tmp.getUserObject())) {
                symbol = tmp;
            }
        }
        if (symbol == null) {
            symbol = new QuotesTreeNode(sequence.getSymbol());
            sampling.add(symbol);
        }
        symbol.setSequence(sequence);

        QuotesTreeNode name = null;
        if (!sequence.isRegular()) {
            if (Feed.ORIG == sequence.getFeed()) {
                final VirtualSqlSequence virtualSequence = sequence.cloneVirtual();
                virtualSequence.setName(sequence.getSymbol());
                virtualSequence.setSettlement(null);
                virtualSequence.setFavourite(false);
                virtualSequence.setJoinSettlements(true);
                symbol.setSequence(virtualSequence);
            }

            for (int i = 0; i < symbol.getChildCount(); ++i) {
                final QuotesTreeNode tmp = (QuotesTreeNode) symbol.getChildAt(i);
                if (sequence.getName().equals(tmp.getUserObject())) {
                    name = tmp;
                }
            }
            if (name == null) {
                name = new QuotesTreeNode(sequence.getName());
                symbol.add(name);
            }
            name.setSequence(sequence);
        }

        return name == null ? symbol : name;
    }

    private static void expandDefault(final JTree tree, final QuotesTreeNode node) {
        if (!node.isLeaf()) {
            final Object content = node.getUserObject();
            if (content instanceof Market) {
                if ((Market) content != Market.REGS) { return; }
            }
            if (content instanceof Sampling) {
                if ((Sampling) content != Sampling.DAILY) { return; }
            }
            tree.expandPath(new TreePath(node.getPath()));
            for (int i = 0; i < node.getChildCount(); ++i) {
                final QuotesTreeNode child = (QuotesTreeNode) node.getChildAt(i);
                expandDefault(tree, child);
            }
        }
    }

}
