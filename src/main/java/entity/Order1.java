package entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Oleg Dashkevych on 10.05.2015.
 */
@Entity
public class Order1 {

    private int orderId;

    private Date dateRequest;

    private double price;

    @ForeignKey(name = "FK_CAR_ORDERS")
    private String carCarId;

    @ForeignKey(name = "FK_MASTER_ORDERS")
    private String masterMasterId;

    @ForeignKey(name = "FK_TYPE_ORDERS")
    private int orderTypeId;

    private int executed;

    private Car carByCarCarId;

    private Master masterByMasterMasterId;

    private OrderType orderTypeByOrderTypeId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false, insertable = true, updatable = true)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "date_request", nullable = false, insertable = true, updatable = true)
    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    @Basic
    @Column(name = "price", nullable = false, insertable = true, updatable = true)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "executed", nullable = false, insertable = true, updatable = true)
    public int getExecuted() {
        return executed;
    }

    public void setExecuted(int executed) {
        this.executed = executed;
    }

    @Basic
    @Column(name = "car_car_id", nullable = false, insertable = false, updatable = false, length = 20)
    public String getCarCarId() {
        return carCarId;
    }

    public void setCarCarId(String carCarId) {
        this.carCarId = carCarId;
    }

    @Basic
    @Column(name = "master_master_id", nullable = false, insertable = false, updatable = false, length = 18)
    public String getMasterMasterId() {
        return masterMasterId;
    }

    public void setMasterMasterId(String masterMasterId) {
        this.masterMasterId = masterMasterId;
    }

    @Basic
    @Column(name = "order_type_id", nullable = false, insertable = false, updatable = false)
    public int getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(int orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order1 order1 = (Order1) o;

        if (orderId != order1.orderId) return false;
        if (orderTypeId != order1.orderTypeId) return false;
        if (dateRequest != null ? !dateRequest.equals(order1.dateRequest) : order1.dateRequest != null) return false;
        if (price != order1.price) return false;
        if (executed != order1.executed) return false;
        if (carCarId != null ? !carCarId.equals(order1.carCarId) : order1.carCarId != null) return false;
        if (masterMasterId != null ? !masterMasterId.equals(order1.masterMasterId) : order1.masterMasterId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (dateRequest != null ? dateRequest.hashCode() : 0);
        result = 31 * result + (int)price;
        result = 31 * result + executed;
        result = 31 * result + (carCarId != null ? carCarId.hashCode() : 0);
        result = 31 * result + (masterMasterId != null ? masterMasterId.hashCode() : 0);
        result = 31 * result + orderTypeId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "car_car_id", referencedColumnName = "car_id", nullable = false)
    public Car getCarByCarCarId() {
        return carByCarCarId;
    }

    public void setCarByCarCarId(Car carByCarCarId) {
        this.carByCarCarId = carByCarCarId;
    }

    @ManyToOne
    @JoinColumn(name = "master_master_id", referencedColumnName = "passport_id", nullable = false)
    public Master getMasterByMasterMasterId() {
        return masterByMasterMasterId;
    }

    public void setMasterByMasterMasterId(Master masterByMasterMasterId) {
        this.masterByMasterMasterId = masterByMasterMasterId;
    }

    @ManyToOne
    @JoinColumn(name = "order_type_id", referencedColumnName = "type_id", nullable = false)
    public OrderType getOrderTypeByOrderTypeId() {
        return orderTypeByOrderTypeId;
    }

    public void setOrderTypeByOrderTypeId(OrderType orderTypeByOrderTypeId) {
        this.orderTypeByOrderTypeId = orderTypeByOrderTypeId;
    }
}
