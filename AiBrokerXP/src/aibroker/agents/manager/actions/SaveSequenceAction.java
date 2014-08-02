package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.tree.TreePath;
import aibroker.agents.manager.QuotesManager;
import aibroker.agents.manager.util.QuotesTreeManager;
import aibroker.agents.manager.util.SettlementMonth;
import aibroker.model.SequenceDescriptor;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.domains.Updater;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.BrokerException;
import aibroker.util.Moment;

@SuppressWarnings("serial")
public class SaveSequenceAction extends AbstractAction {

    private final QuotesManager view;

    public SaveSequenceAction(final QuotesManager view) {
        this.view = view;
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Some short description");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        view.quotesTree.removeTreeSelectionListener(view);
        try {
            final String symbol = view.txtSymbol.getText();
            final Market market = (Market) view.cbbMarket.getSelectedItem();
            final Sampling sampling = (Sampling) view.cbbSampling.getSelectedItem();
            Moment settlement = null;
            if (!Market.REGS.equals(market)) {
                final int month = ((SettlementMonth) view.cbbSettlementMonth.getSelectedItem()).index;
                final int year = (Integer) view.cbbSettlementYear.getSelectedItem();
                settlement = SequenceDescriptor.calculateSettlement(month, year);
            }

            final SequenceDescriptor builder = new SequenceDescriptor(symbol, settlement);
            builder.name(view.txtName.getText());
            builder.market(market);
            builder.feed((Feed) view.cbbFeed.getSelectedItem());
            builder.sampling(sampling);
            builder.updater((Updater) view.cbbUpdater.getSelectedItem());
            builder.grouping(sampling == Sampling.SECOND ? Grouping.TICK : Grouping.OHLC);
            builder.favourite(view.ckbFavourite.isSelected());
            builder.fee(Double.parseDouble(view.txtFee.getText()));
            if (!Market.REGS.equals(market)) {
                builder.margin(Double.parseDouble(view.txtMargin.getText()));
                builder.multiplier(Double.parseDouble(view.txtMultiplier.getText()));
                builder.support(view.txtSupport.getText());
            }

            final SqlSequence sequence = view.getDatabase().add(builder);
            final TreePath sequencePath = QuotesTreeManager.addSequence(view.quotesTree, sequence);
            view.setSequence(sequence, sequencePath);
        } catch (final Exception ex) {
            BrokerException.reportUi(ex);
        } finally {
            view.quotesTree.addTreeSelectionListener(view);
        }
    }

}