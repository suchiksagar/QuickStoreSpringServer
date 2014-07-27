package com.me.quickstoreserver.Pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Order_Product")
public class Order_Product {

@Id	
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="id")
private int id;

@ManyToOne
@JoinColumn(name="productid")
private Product product;

@ManyToOne
@JoinColumn(name="orderid")
private Order order;

@Column(name="quantity")
private int quantity;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public Product getProduct() {
return product;
}

public void setProduct(Product product) {
this.product = product;
}

public Order getOrder() {
return order;
}

public void setOrder(Order order) {
this.order = order;
}

public int getQuantity() {
return quantity;
}

public void setQuantity(int quantity) {
this.quantity = quantity;
}


}