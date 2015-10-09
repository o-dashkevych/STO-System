package service.order_type;

/**
 * Created by Oleg Dashkevych on 06.05.2015.
 */
public class Factory {
    public static Factory instance = new Factory();
    public OrderTypeDAOImpl orderTypeDAO;

    private Factory() {
    }

    public static Factory getInstance() {
        return Factory.instance;
    }

    public OrderTypeDAOImpl getordertypeDAO() {
        if (orderTypeDAO == null) orderTypeDAO = new OrderTypeDAOImpl();
        return orderTypeDAO;
    }
}
