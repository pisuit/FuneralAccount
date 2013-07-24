package fun.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fun.customtype.Role;
import fun.model.Authorization;
import fun.model.User;
import fun.utils.HibernateUtil;

public class AdminController {
	
	@SuppressWarnings("unchecked")
	public List<User> getUserList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<User> userList = session.createQuery(
					"SELECT distinct user " +
					"FROM User user " +
					"left join fetch user.authorizations " +
					"WHERE user.status = 'NORMAL' " +
					"ORDER BY user.username")
					.list()
					;
			
			tx.commit();
			
			return userList;
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
	
	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			User user = (User) session.createQuery(
					"SELECT distinct user " +
					"FROM User user " +				
					"WHERE user.username = :pusername ")
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
	
	public void saveUser(User user, List<Authorization> authorizations){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(user);
			System.out.println("saved user");
			
			if(user.getAuthorizations() != null){
				for(Authorization authorization : user.getAuthorizations()){
					session.delete(authorization);
				}
			}
			
			if(authorizations != null){
				for(Authorization authorization : authorizations){
					authorization.setUser(user);
					session.saveOrUpdate(authorization);
				}
			}
			
			tx.commit();
		}  catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.clear();
			session.close();
		}
	}
}
