package com.me.quickstoreserver.DAOS;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.me.quickstoreserver.Pojos.Customer;

@Service
public class CustomerDAO extends HibernateUtil {

	public CustomerDAO() {
		System.out.println("Customer Dao got created through Autowire");
	}

	public void createCustomer(Customer c) {
		Session session = this.getSession();
		session.getTransaction().begin();

		session.save(c);
		session.getTransaction().commit();
		this.closeSession(session);
	}

	public Customer retrieveCustomerByUserName(String uname) {

		Session session = this.getSession();
		session.getTransaction().begin();
		Query query = session
				.createQuery("from Customer c where c.userName= :uname");
		query.setParameter("uname", uname);
		Customer rUser = (Customer) query.uniqueResult();
		session.getTransaction().commit();
		this.closeSession(session);
		return rUser;
	}

	public boolean isUserAuthenticated(String username, String password) {
		Session session = this.getSession();
		session.getTransaction().begin();
		Query query = session
				.createQuery("from Customer u where u.userName= :username and u.password= :password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		Customer rUser = (Customer) query.uniqueResult();
		session.getTransaction().commit();
		this.closeSession(session);
		if (rUser != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean doesSuchUserExist(String username) {
		Session session = this.getSession();
		session.getTransaction().begin();
		Query query = session
				.createQuery("from Customer u where u.userName= :username");
		query.setParameter("username", username);
		Customer rUser = (Customer) query.uniqueResult();
		session.getTransaction().commit();
		this.closeSession(session);
		if (rUser != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean updateCustomerPassword(String username, String pwd) {
		boolean flag = false;
		Session session = this.getSession();
		session.getTransaction().begin();
		Query query = session
				.createQuery("from Customer u where u.userName= :username");
		query.setParameter("username", username);
		Customer rUser = (Customer) query.uniqueResult();
		if (rUser != null) {
			rUser.setPassword(pwd);
			session.saveOrUpdate(rUser);
		} else
			flag = false;
		session.getTransaction().commit();
		this.closeSession(session);
		return flag;
	}

}