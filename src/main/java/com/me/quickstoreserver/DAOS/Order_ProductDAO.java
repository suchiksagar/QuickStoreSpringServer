package com.me.quickstoreserver.DAOS;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.me.quickstoreserver.Pojos.Order_Product;
@Service
public class Order_ProductDAO extends HibernateUtil {

public Order_ProductDAO()
{
System.out.println("Order Product Dao got created through Autowire");
}

public void createOrder_Product(Order_Product op)
{
Session session=this.getSession();
session.getTransaction().begin();
session.save(op);
session.getTransaction().commit();
this.closeSession(session);
}
}