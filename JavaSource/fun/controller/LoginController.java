package fun.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fun.model.Member;
import fun.model.User;
import fun.utils.HibernateUtil;

public class LoginController {
	
	@SuppressWarnings("unchecked")
	public User getUser(String username){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			User user = (User) session.createQuery(
					"SELECT distinct user " +
					"FROM User user " +
					"left join fetch user.authorizations " +
					"WHERE user.username = :pusername " +
					"AND user.status = 'NORMAL' ")
					.setParameter("pusername", username)
					.uniqueResult()
					;
			
			tx.commit();
			
			return user;
		}  catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
}
