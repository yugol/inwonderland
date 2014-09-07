package aibroker.agents.manager.actions;

import java.awt.event.ActionEvent;
import javax.swing.tree.TreePath;
import aibroker.agents.manager.QuotesManager;
import aibroker.agents.manager.util.QuotesTreeManager;
import aibroker.agents.manager.util.SettlementMonth;
import aibroker.model.SeqDesc;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.domains.Updater;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.util.BrokerException;
import aibroker.util.Moment;

@SuppressWarnings("serial")
public class SaveSequenceAction extends BasicAction {

    public SaveSequenceAction(final QuotesManager view) {
        super(view);
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
                settlement = SeqDesc.calculateSettlement(month, year);
            }

            final SeqDesc sDesc = new SeqDesc(symbol, settlement);
            sDesc.setName(view.txtName.getText());
            sDesc.setMarket(market);
            sDesc.setFeed((Feed) view.cbbFeed.getSelectedItem());
            sDesc.setSampling(sampling);
            sDesc.setUpdater((Updater) view.cbbUpdater.getSelectedItem());
            sDesc.setGrouping(sampling == Sampling.SECOND ? Grouping.TICK : Grouping.OHLC);
            sDesc.setFavourite(view.ckbFavourite.isSelected());
            sDesc.setFee(Double.parseDouble(view.txtFee.getText()));
            if (!Market.REGS.equals(market)) {
                sDesc.setMargin(Double.parseDouble(view.txtMargin.getText()));
                sDesc.setMultiplier(Double.parseDouble(view.txtMultiplier.getText()));
                sDesc.setSupport(view.txtSupport.getText());
            }

            final SqlSeq sequence = view.getDatabase().add(sDesc);
            final TreePath sequencePath = QuotesTreeManager.addSequence(view.quotesTree, sequence);
            view.setSequence(sequence, sequencePath);
        } catch (final Exception ex) {
            BrokerException.reportUi(ex);
        } finally {
            view.quotesTree.addTreeSelectionListener(view);
        }
    }

}
