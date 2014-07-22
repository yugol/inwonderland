package aibroker.engines.markets;

import aibroker.engines.Order;

public interface OrderExecutionListener {

    void onOrderExecuted(Order order);

}
