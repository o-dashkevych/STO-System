package entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Oleg Dashkevych on 10.05.2015.
 */
@Entity
@Table(name = "order_type", schema = "", catalog = "sto")
public class OrderType {

    private int typeId;

    private double price;

    private String name;

    private Collection<Master> mastersByTypeId;

    private Collection<Order1> ordersByTypeId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "type_id", nullable = false, insertable = true, updatable = true)
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderType orderType = (OrderType) o;

        if (typeId != orderType.typeId) return false;
        if (Double.compare(orderType.price, price) != 0) return false;
        if (name != null ? !name.equals(orderType.name) : orderType.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = typeId;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "orderTypeByCategory")
    public Collection<Master> getMastersByTypeId() {
        return mastersByTypeId;
    }

    public void setMastersByTypeId(Collection<Master> mastersByTypeId) {
        this.mastersByTypeId = mastersByTypeId;
    }

    @OneToMany(mappedBy = "orderTypeByOrderTypeId")
    public Collection<Order1> getOrdersByTypeId() {
        return ordersByTypeId;
    }

    public void setOrdersByTypeId(Collection<Order1> ordersByTypeId) {
        this.ordersByTypeId = ordersByTypeId;
    }
}
