package com.me.quickstoreserver.DAOS;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

private static SessionFactory sessionFactory;
private final static Logger logger = Logger
.getLogger(HibernateUtil.class.getName());
static
    {
            try
            {
                    sessionFactory = new Configuration().configure().buildSessionFactory();
                    logger.info("SessionFactory created");
            }
            catch (Throwable e)
            {
                    logger.error("SessionFactory creation failed!");
                    throw new ExceptionInInitializerError(e);
            }
    }

/*public HibernateUtil(){
if(sessionFactory==null)
{
sessionFactory = new Configuration().configure().buildSessionFactory();
System.out.println("SF created in constructor");
}
}*/
public SessionFactory getSessionFactory() {
return sessionFactory;
}

public Session getSession() throws HibernateException
{
return this.getSessionFactory().openSession();
}
public void closeSession(Session s)
{
//s.flush(); not required as trans commit flushes auto
s.close();
}

}