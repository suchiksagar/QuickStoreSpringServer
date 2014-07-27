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
@Table(name="INVENTORY")
public class Inventory {

@Id	
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="inventoryid")
private int id;

@ManyToOne
@JoinColumn(name="productid")
private Product product;

@Column(name="availability")
private int avialability;

@ManyToOne
@JoinColumn(name="storeid")
private Store store;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}


public int getAvialability() {
return avialability;
}

public void setAvialability(int avialability) {
this.avialability = avialability;
}

public Product getProduct() {
return product;
}

public void setProduct(Product product) {
this.product = product;
}

public Store getStore() {
return store;
}

public void setStore(Store store) {
this.store = store;
}



}