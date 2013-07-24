package fun.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

import fun.customtype.EmployeeStatus;
import fun.customtype.TransactionType;
import fun.model.CurrentBalance;
import fun.model.Debt;
import fun.model.Member;
import fun.model.RetirementPayment;
import fun.model.Warning;
import fun.utils.HibernateUtil;

public class PaymentController {
	
	@SuppressWarnings("unchecked")
	public RetirementPayment getLatestPayment(Member member){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			RetirementPayment payment = (RetirementPayment) session.createQuery(
					"SELECT payment " +
					"FROM RetirementPayment payment " +
					"WHERE payment.member = :pmember " +
					"AND payment.ID = (" +
						"SELECT MAX(pay.ID) " +
						"FROM RetirementPayment pay " +
						"WHERE pay.member = :pmember)")
					.setParameter("pmember", member)
					.uniqueResult()
					;
			tx.commit();
			
			return payment;
		} catch (Exception e) {
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
	public List<RetirementPayment> getPaymentList(Member member, Date fromDate, Date toDate){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<RetirementPayment> paymentList = session.createQuery(
					"SELECT payment " +
					"FROM RetirementPayment payment " +
					"WHERE payment.member = :pmember " +
					"AND payment.transactionDate >= :pfromdate " +
					"AND payment.transactionDate <= :ptodate " +
					"ORDER BY payment.ID desc ")
					.setParameter("pfromdate", fromDate)
					.setParameter("ptodate", toDate)
					.setParameter("pmember", member)
					.list()
					;
			tx.commit();
			
			return paymentList;
		} catch (Exception e) {
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
	
	public void addBalance(RetirementPayment payment){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
			
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(payment);
	
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public void addBalance(List<RetirementPayment> payments){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
			
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			for(RetirementPayment payment : payments){
				session.saveOrUpdate(payment);
			}
	
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public void addDebt(Debt debt){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(debt);
			
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
	
	public void addDebt(List<Debt> debts){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			for(Debt debt : debts){
				session.saveOrUpdate(debt);
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
	
	public void saveCurrentBalance(CurrentBalance balance){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(balance);
			
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
	
	public void saveCurrentBalance(HashMap balances){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			for(Object balance : balances.values()){
				CurrentBalance bal = (CurrentBalance) balance;
				session.saveOrUpdate(bal);
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
	
	public void saveWarning(Warning warning){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(warning);
			
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
	
	public CurrentBalance getCurrentBalance(Member member){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			CurrentBalance balance = (CurrentBalance) session.createQuery(
					"SELECT balance " +
					"FROM CurrentBalance balance " +
					"WHERE balance.member = :pmember")
					.setParameter("pmember", member)
					.uniqueResult()
					;
			
			tx.commit();
			
			return balance;
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
	
	public Warning getWarning(Member member){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
		
			Warning warning = (Warning) session.createQuery(
					"SELECT warn " +
					"FROM Warning warn " +
					"WHERE warn.member = :pmemebr" )
					.setParameter("pmemebr", member)
					.uniqueResult()
					;
			
			tx.commit();
			
			return warning;
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
	public List<Debt> getDebtList(Member member, Date fromDate, Date toDate, boolean isShowAll){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			List<Debt> debtList = null;
			
			if(isShowAll == true){
				debtList = session.createQuery(
						"SELECT debt " +
						"FROM Debt debt " +
						"WHERE debt.inDebtMember = :pmember " +
						"ORDER BY debt.ID ")
						.setParameter("pmember", member)
						.list()
						;
			} else {
				debtList = session.createQuery(
						"SELECT debt " +
						"FROM Debt debt " +
						"WHERE debt.inDebtMember = :pmember " +
						"AND debt.transactionDate >= :pfromdate " +
						"AND debt.transactionDate <= :ptodate " +
						"ORDER BY debt.ID ")
						.setParameter("pmember", member)
						.setParameter("pfromdate", fromDate)
						.setParameter("ptodate", toDate)
						.list()
						;
			}
			
			tx.commit();
			
			return debtList;
		} catch (Exception e) {
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
	public List<Debt> getDebtList(Member member){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			List<Debt> debtList = null;
					
				debtList = session.createQuery(
						"SELECT debt " +
						"FROM Debt debt " +
						"WHERE debt.inDebtMember = :pmember " +
						"AND debt.debtStatus in ('IN_DEBT','WARNED','BAD_DEBT') " +
						"ORDER BY debt.ID desc")
						.setParameter("pmember", member)
						.list()
						;			
			tx.commit();
			
			return debtList;
		} catch (Exception e) {
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
	public List<Debt> getActiveDebtList(Member member){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			List<Debt> debtList = null;
					
				debtList = session.createQuery(
						"SELECT debt " +
						"FROM Debt debt " +
						"WHERE debt.inDebtMember = :pmember " +
						"AND debt.debtStatus in ('IN_DEBT','WARNED') " +
						"ORDER BY debt.ID desc")
						.setParameter("pmember", member)
						.list()
						;			
			tx.commit();
			
			return debtList;
		} catch (Exception e) {
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
	
	public int getNextWarningCount(Member member){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			int maxCount = (Integer) session.createQuery(
					"SELECT MAX(debt.warningCount) " +
					"FROM Debt debt " +
					"WHERE debt.inDebtMember = :pmember " +
					"AND debt.debtStatus in ('IN_DEBT','WARNED') ")
					.setParameter("pmember", member)
					.uniqueResult()
					;
			
			tx.commit();
			
			return maxCount+1;
		}  catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return 0;
		} finally {
			session.clear();
			session.close();
		}
	}
}
