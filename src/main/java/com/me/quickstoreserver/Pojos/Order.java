package com.me.quickstoreserver.Pojos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS")
public class Order {

@Id	
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="orderid")
private int id;

@ManyToOne
@JoinColumn(name="customerid")
private Customer customer;

@Column(name="total")
private double totalAmount;

@Column(name="orderdate")
private Date orderDate;

@ManyToOne
@JoinColumn(name="storeid")
private Store store;

@OneToMany(cascade=CascadeType.ALL, mappedBy="order")
List<Order_Product> order_productList;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public Customer getCustomer() {
return customer;
}

public void setCustomer(Customer customer) {
this.customer = customer;
}

public double getTotalAmount() {
return totalAmount;
}

public void setTotalAmount(double totalAmount) {
this.totalAmount = totalAmount;
}

public String getOrderDate() {
SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
return sdf.format(orderDate);

}

public void setOrderDate(String orderDate) {
SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
try {
this.orderDate = sdf.parse(orderDate);
} catch (ParseException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}

public Store getStore() {
return store;
}

public void setStore(Store store) {
this.store = store;
}



}