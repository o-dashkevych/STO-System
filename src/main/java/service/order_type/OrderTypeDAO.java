package service.order_type;

import entity.OrderType;

import java.util.List;

/**
 * Created by Oleg Dashkevych on 06.05.2015.
 */
public interface OrderTypeDAO {

    public List<OrderType> getAll();

    public List<OrderType> getById(int id);

    public List<OrderType> getByName(String name);

    public OrderType getUniqueByName(String name);

    public List<OrderType> getByPrice(double price);

    public double minPrice();

    public double maxPrice();

    public List<OrderType> filterPrice(double value);

    public boolean addOrderType(OrderType OrderType);

    public boolean deleteOrderType(OrderType OrderType);

    public OrderType getUniqueById(int id);

    public boolean checkById(int id);

    public boolean updateOrderType(OrderType OrderType);
}
