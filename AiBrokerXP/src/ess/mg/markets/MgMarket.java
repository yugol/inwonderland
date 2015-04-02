package ess.mg.markets;

public enum MgMarket {

    LOCAL ("market"),
    GLOBAL ("global_market"),
    FINANCIAL ("financial_market"),
    REFERRAL ("bid"),
    COMPANY ("companies_market");

    public final String urlChunk;

    private MgMarket(final String urlChunk) {
        this.urlChunk = urlChunk;
    }

}
