package com.me.quickstoreserver.DAOS;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.me.quickstoreserver.Pojos.Inventory;
import com.me.quickstoreserver.Pojos.Product;
import com.me.quickstoreserver.Pojos.Store;

@Service
public class InventoryDAO extends HibernateUtil {

	private final static Logger logger = Logger.getLogger(InventoryDAO.class
			.getName());

	public InventoryDAO() {
		System.out.println("Inventory Dao got created through Autowire");
	}

	public void createInventory(Inventory i) {
		Session session = this.getSession();
		session.getTransaction().begin();
		session.save(i);
		session.getTransaction().commit();
		this.closeSession(session);
	}

	public List<Inventory> prepareProductCatalogForTheStore(Store s) {

		Session session = this.getSession();
		session.getTransaction().begin();
		Query query = session.createQuery("from Inventory i where i.store= :s");
		query.setParameter("s", s);
		List<Inventory> invenList = query.list();
		session.getTransaction().commit();
		this.closeSession(session);
		if (invenList.isEmpty()) {
			return null;
		} else {
			return invenList;
		}
	}

	public int getCurrentInventory(Product p) {
		Session session = this.getSession();
		session.getTransaction().begin();
		Query query = session
				.createQuery("from Inventory i where i.product= :p");
		query.setParameter("p", p);
		Inventory iv = (Inventory) query.uniqueResult();
		session.getTransaction().commit();
		this.closeSession(session);
		if (iv == null) {
			return 0;
		} else {
			return iv.getAvialability();
		}
	}

	public void updateInventories(Product p, int inventory) {
		Session session = this.getSession();
		session.getTransaction().begin();
		// Query
		// query=session.createQuery("update Inventory i set i.avialability= :avail where i.product= :p");
		Query query = session
				.createQuery("from Inventory i where i.product= :p");
		query.setParameter("p", p);
		Inventory inven = (Inventory) query.uniqueResult();
		inven.setAvialability((inven.getAvialability() - inventory));
		session.saveOrUpdate(inven);
		session.getTransaction().commit();
		this.closeSession(session);
	}
}