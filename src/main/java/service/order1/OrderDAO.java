package service.order1;

import entity.Order1;

import java.util.Date;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 11.05.2015.
 */
public interface OrderDAO {

    public List<Order1> getAll();

    public List<Order1> getById(int id);

    public List<Order1> getByDate(Date date);

    public List<Order1>  getByCarId(String id);

    public List<Order1>  getByCarName(String name);

    public List<Order1>  getByMasterId(String id);

    public List<Order1>  getByMasterName(String name);

    public List<Order1>  getByTypeName(String name);

    public List<Order1> getByPrice(double price);

    public double minPrice();

    public double maxPrice();

    public List<Order1> filterPrice(double value);

    public boolean addOrder(Order1 Order1);

    public boolean deleteOrder(Order1 Order1);

    public Order1 getUniqueById(int id);

    public boolean checkById(int id);

    public boolean updateOrder(Order1 Order1);

    public int countFreeOrders();

    public int getLastId();

    public List<Order1> getNotExec();

    public List<Order1> getExec();
}
