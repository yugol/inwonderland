package ess.mg.driver;

public interface MGWebReaderListener {

    void onFetchActiveUsers(String activeUsers);

    void onFetchEurAmount(String eurAmount);

    void onFetchEuroGoldExchangeRate(String rate);

    void onFetchGoldAmount(String goldAmount);

    void onFetchGoldRonExchangeRate(String rate);

    void onFetchSharePrice(String sharePrice);

    void onFetchWorkWage(String wage);

}
