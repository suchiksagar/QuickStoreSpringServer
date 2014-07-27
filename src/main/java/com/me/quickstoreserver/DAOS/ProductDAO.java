package com.me.quickstoreserver.DAOS;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.me.quickstoreserver.Pojos.Product;
@Service
public class ProductDAO extends HibernateUtil {

public ProductDAO()
{
System.out.println("Product Dao got created through Autowire");
}
public void createProduct(Product p)
{
Session session=this.getSession();
session.getTransaction().begin();
session.save(p);
session.getTransaction().commit();
this.closeSession(session);
}

public Product retrieveProductById(int id)
{

Session session=this.getSession();
session.getTransaction().begin();
Query query=session.createQuery("from Product p where p.id= :id");
query.setParameter("id", id);
Product prod=(Product)query.uniqueResult();
session.getTransaction().commit();
this.closeSession(session);
return prod;
}


}