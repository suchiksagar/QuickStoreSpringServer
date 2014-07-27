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
@Table(name="STORE")
public class Store {

@Id	
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="storeid")
private int id;

@Column(name="street_addr")
private String street;

@Column(name="zipcode")
private int zipCode;

@Column(name="store_name")
private String storeName;

//FK SECTIION

@OneToMany(cascade=CascadeType.ALL, mappedBy="store")
List<Order> orderList;

@OneToMany(cascade=CascadeType.ALL, mappedBy="store")
List<Inventory> inventoryList;

int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getStreet() {
return street;
}

public void setStreet(String street) {
this.street = street;
}

public int getZipCode() {
return zipCode;
}

public void setZipCode(int zipCode) {
this.zipCode = zipCode;
}

public String getStoreName() {
return storeName;
}

public void setStoreName(String storeName) {
this.storeName = storeName;
}



}