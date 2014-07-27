package com.me.quickstoreserver.DAOS;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.me.quickstoreserver.Pojos.Store;
@Service
public class StoreDAO extends HibernateUtil {

public StoreDAO()
{
System.out.println("Store Dao got created through Autowire");
}
public void createStore(Store s)
{
Session session=this.getSession();
session.getTransaction().begin();

session.save(s);
session.getTransaction().commit();
this.closeSession(session);
}

public Store retrieveStoreByZip(int zip)
{

Session session=this.getSession();
session.getTransaction().begin();
Query query=session.createQuery("from Store s where s.zipCode= :zip");
query.setParameter("zip", zip);
Store store=(Store)query.uniqueResult();
session.getTransaction().commit();
this.closeSession(session);	
return store;

}

public Store retrieveStoreById(int id)
{

Session session=this.getSession();
session.getTransaction().begin();
Query query=session.createQuery("from Store s where s.id= :id");
query.setParameter("id", id);
Store store=(Store)query.uniqueResult();
session.getTransaction().commit();
this.closeSession(session);	
return store;
}

public List<Store> retrieveStores()
{

Session session=this.getSession();
session.getTransaction().begin();
Query query=session.createQuery("from Store");
List<Store> storeList=query.list();
session.getTransaction().commit();
this.closeSession(session);
if(storeList.isEmpty())
{return null;}
else
{
return storeList;
}
}

}