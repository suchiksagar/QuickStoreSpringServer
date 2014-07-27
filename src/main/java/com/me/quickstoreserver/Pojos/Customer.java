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
@Table(name="CUSTOMERS")
public class Customer {

@Id	
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="customerid")
private int id;

@Column(name="firstName")
private String firstName;

@Column(name="lastName")
private String lastName;

@Column(name="username")
private String userName;

@Column(name="pass")
private String password;

//FK SET
@OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
List<Order> orderList;


public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}	

}