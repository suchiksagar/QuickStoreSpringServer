package com.me.quickstoreserver.Pojos;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT")
public class Product {

@Id	

@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="productid")
private int id;

@Column(name="pName")
private String productName;

@Column(name="pPrice")
private double productPrice;

@Column(name="Category")
private int categoryId;

@OneToMany(cascade=CascadeType.ALL, mappedBy="product")
List<Inventory> inventoryList;

@OneToMany(cascade=CascadeType.ALL, mappedBy="product")
List<Order_Product> order_productList;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getProductName() {
return productName;
}

public void setProductName(String productName) {
this.productName = productName;
}

public double getProductPrice() {
return productPrice;
}

public void setProductPrice(double productPrice) {
this.productPrice = productPrice;
}

public int getCategoryId() {
return categoryId;
}

public void setCategoryId(int categoryId) {
this.categoryId = categoryId;
}


}