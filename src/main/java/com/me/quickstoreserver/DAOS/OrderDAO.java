package com.me.quickstoreserver.DAOS;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.me.quickstoreserver.Pojos.Customer;
import com.me.quickstoreserver.Pojos.Order;
@Service
public class OrderDAO extends HibernateUtil {

public OrderDAO()
{
System.out.println("Order Dao got created through Autowire");
}
public void createOrder(Order o)
{
Session session=this.getSession();
session.getTransaction().begin();
session.save(o);
session.getTransaction().commit();
this.closeSession(session);
}

public Order retrieveOrderById(int id)
{

Session session=this.getSession();
session.getTransaction().begin();
Query query=session.createQuery("from Order o where o.id= :id");
query.setParameter("id", id);
List<Order> orderList=query.list();
session.getTransaction().commit();
this.closeSession(session);
if(orderList.isEmpty())
{return null;}
else
{
return orderList.get(0);
}

}

public List<Order> retrieveOrdersByCustomer(Customer c)
{

Session session=this.getSession();
session.getTransaction().begin();
Query query=session.createQuery("from Order o where o.customer= :c");
query.setParameter("c", c);
List<Order> orderList=query.list();
session.getTransaction().commit();
this.closeSession(session);
if(orderList.isEmpty())
{return null;}
else
{
return orderList;
}

}

}