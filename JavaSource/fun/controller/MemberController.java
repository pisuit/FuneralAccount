package fun.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fun.customtype.DebtStatus;
import fun.model.CurrentBalance;
import fun.model.Debt;
import fun.model.Employee;
import fun.model.Member;
import fun.model.Payment;
import fun.model.RetirementPayment;
import fun.utils.HibernateUtil;

public class MemberController {
	private PaymentController paymentController = new PaymentController();
	
	@SuppressWarnings("unchecked")
	public List<Member> searchRetirementMember(String firstName, String lastName, int memberNumber, boolean includedDead){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		firstName = "%"+firstName+"%";
		lastName = "%"+lastName+"%";
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			List<Member> memberList;	
			
			if(includedDead == false){
				 memberList = session.createQuery(
							"SELECT distinct mem " +
							"FROM Member mem " +
							"left join fetch mem.employee emp " +
							"left join fetch mem.mainMember main " +
							"left join fetch main.employee mainemp " +
							"left join fetch mem.balance " +
							"left join fetch mem.paidEmployee mempaidemp " +
							"left join fetch main.paidEmployee mainpaidemp " +
							"left join fetch mem.paidMember mempaidmem " +
							"left join fetch main.paidMember mainpaidmem " +
							"WHERE (mem.memberNumber = :pnumber OR :pnumber = 0) " +
							"AND (mem.name like :pfirstname OR :pfirstname IS NULL) " +
							"AND (mem.name like :plastname OR :plastname IS NULL) " +
							"AND (emp.status in ('RETIREMENT','RESIGNATION') OR mainemp.status in ('RETIREMENT','RESIGNATION','DECEASED')) " +
							"AND mem.status = 'M' " +
							"AND (main.paidEmployee IS NULL OR (main.paidEmployee IS NOT NULL AND mainpaidemp.status in ('RESIGNATION','DECEASED','RETIREMENT'))) " +
							"AND (main.paidMember IS NULL OR (main.paidMember IS NOT NULL AND mainpaidmem.status in ('D','F','R'))) " +
							"AND (mem.paidEmployee IS NULL OR (mem.paidEmployee IS NOT NULL AND mempaidemp.status in ('RESIGNATION','DECEASED','RETIREMENT'))) " +
							"AND (mem.paidMember IS NULL OR (mem.paidMember IS NOT NULL AND mempaidmem.status in ('D','F','R'))) " +
							"ORDER BY mem.ID ")
							.setParameter("pnumber", memberNumber)
							.setParameter("pfirstname", firstName)
							.setParameter("plastname", lastName)				
							.list();
			} else {
				 memberList = session.createQuery(
							"SELECT distinct mem " +
							"FROM Member mem " +
							"left join fetch mem.employee emp " +
							"left join fetch mem.mainMember main " +
							"left join fetch main.employee mainemp " +
							"left join fetch mem.balance " +
							"left join fetch mem.paidEmployee mempaidemp " +
							"left join fetch main.paidEmployee mainpaidemp " +
							"left join fetch mem.paidMember mempaidmem " +
							"left join fetch main.paidMember mainpaidmem " +
							"WHERE (mem.memberNumber = :pnumber OR :pnumber = 0) " +
							"AND (mem.name like :pfirstname OR :pfirstname IS NULL) " +
							"AND (mem.name like :plastname OR :plastname IS NULL) " +
							"AND (emp.status in ('RETIREMENT','RESIGNATION') OR mainemp.status in ('RETIREMENT','RESIGNATION','DECEASED')) " +
							"AND mem.status in ('M','D') " +
							"AND (main.paidEmployee IS NULL OR (main.paidEmployee IS NOT NULL AND mainpaidemp.status in ('RESIGNATION','DECEASED','RETIREMENT'))) " +
							"AND (main.paidMember IS NULL OR (main.paidMember IS NOT NULL AND mainpaidmem.status in ('D','F','R'))) " +
							"AND (mem.paidEmployee IS NULL OR (mem.paidEmployee IS NOT NULL AND mempaidemp.status in ('RESIGNATION','DECEASED','RETIREMENT'))) " +
							"AND (mem.paidMember IS NULL OR (mem.paidMember IS NOT NULL AND mempaidmem.status in ('D','F','R'))) " +
							"ORDER BY mem.ID ")
							.setParameter("pnumber", memberNumber)
							.setParameter("pfirstname", firstName)
							.setParameter("plastname", lastName)				
							.list();
			}
								
			tx.commit();
			
			return memberList;
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
	public List<Member> getSubMember(Member parentMember){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Member> memberList = session.createQuery(
					"SELECT distinct mem " +
					"FROM Member mem " +
					"left join fetch mem.balance " +
					"WHERE mem.mainMember = :pmember " +
					"ORDER BY mem.ID ")
					.setParameter("pmember", parentMember)
					.list()
					;
			tx.commit();
			
			return memberList;
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
	public List<Member> getMainMember(Member subMember){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Member> memberList = session.createQuery(
					"SELECT distinct mem.mainMember " +
					"FROM Member mem " +
					"left join fetch mem.mainMember.balance " +
					"WHERE mem = :pmember " +
					"ORDER BY mem.ID ")
					.setParameter("pmember", subMember)
					.list()
					;
			tx.commit();
			
			return memberList;
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
	public List<Member> getDeadMember(Date fromDate, Date toDate ){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
//		List<Member> deadMemberList = new ArrayList<Member>();

		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Member> deadMemberList = session.createQuery(
					"SELECT distinct mem " +
					"FROM Member mem " +
					"left join fetch mem.employee " +
					"left join fetch mem.mainMember main " +
					"left join fetch main.employee " +
					"left join fetch mem.normalPayments normal " +
					"left join fetch mem.balance " +
					"WHERE mem.status = 'D' " +
					"AND mem.deceaseReportDate >= :pfromdate " +
					"AND mem.deceaseReportDate <= :ptodate " +
					"AND normal.receivedAmount != 0 " +
					"ORDER BY mem.deceaseReportDate desc ")
					.setParameter("pfromdate", fromDate)
					.setParameter("ptodate", toDate)
					.list()
					;
			
//			for(Member member : memberList){
//				Payment latest = (Payment) session.createQuery(
//						"SELECT payment " +
//						"FROM Payment payment " +
//						"WHERE payment.receivedAmount != 0 " +
//						"AND payment.member = :pmember ")
//						.setParameter("pmember", member)
//						.uniqueResult()
//						;
//				
//				if(latest != null){
//					member.setReceivedAmount(latest.getReceivedAmount());
//				}
				
//				List<RetirementPayment> retirePayment = session.createQuery(
//						"SELECT payment " +
//						"FROM RetirementPayment payment " +
//						"WHERE payment.paidForMember = :pmember ")
//						.setParameter("pmember", member)
//						.list()
//						;
//				BigDecimal sum = new BigDecimal("0.00");
//		
//				for(RetirementPayment rePayment : retirePayment){
//					if(rePayment.getAvailableAmount().compareTo(new BigDecimal("0.00")) >= 0){
//						sum = sum.add(rePayment.getTransactionAmount());
//					}			
//				}
//				member.setReceivedAmountFromRetire(sum);
				
//				List<Debt> debtList = session.createQuery(
//						"SELECT debt " +
//						"FROM Debt debt " +
//						"WHERE debt.paidForMember = :pmember " +
//						"AND debt.debtStatus in ('WARNED','IN_DEBT') ")
//						.setParameter("pmember", member)
//						.list()
//						;
//				
//				BigDecimal sumDebt = new BigDecimal("0.00");
//				for(Debt debt : debtList){
//					sumDebt = sumDebt.add(debt.getDebtAmount());
//				}
//				member.setReceivedDebtAmount(sumDebt);
				
//				deadMemberList.add(member);
//			}
			
			tx.commit();
			
			return deadMemberList;
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
	public List<Member> getDeadMemberForMonthlyReport(Date fromDate, Date toDate ){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Member> deadMemberList = session.createQuery(
					"SELECT distinct mem " +
					"FROM Member mem " +
					"left join fetch mem.employee " +
					"left join fetch mem.mainMember main " +
					"left join fetch main.employee " +
					"WHERE mem.status = 'D' " +
					"AND mem.deceaseReportDate >= :pfromdate " +
					"AND mem.deceaseReportDate <= :ptodate " +
					"ORDER BY mem.deceaseReportDate desc ")
					.setParameter("pfromdate", fromDate)
					.setParameter("ptodate", toDate)
					.list()
					;
			
			tx.commit();
			
			return deadMemberList;
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
	public List<Member> getRetireNotDeadMember(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Member> memberList = session.createQuery(
					"SELECT distinct mem " +
					"FROM Member mem " +
					"left join fetch mem.employee emp " +
					"left join fetch mem.mainMember main " +
					"left join fetch main.employee mainemp " +
					"left join fetch mem.balance " +
					"left join fetch mem.paidEmployee mempaidemp " +
					"left join fetch main.paidEmployee mainpaidemp " +
					"left join fetch mem.paidMember mempaidmem " +
					"left join fetch main.paidMember mainpaidmem " +
					"WHERE (emp.status in ('RETIREMENT','RESIGNATION') OR mainemp.status in ('RETIREMENT','RESIGNATION','DECEASED')) " +
					"AND mem.status = 'M' " +
					"AND (main.paidEmployee IS NULL OR (main.paidEmployee IS NOT NULL AND mainpaidemp.status in ('RESIGNATION','DECEASED','RETIREMENT'))) " +
					"AND (main.paidMember IS NULL OR (main.paidMember IS NOT NULL AND mainpaidmem.status in ('D','F','R'))) " +
					"AND (mem.paidEmployee IS NULL OR (mem.paidEmployee IS NOT NULL AND mempaidemp.status in ('RESIGNATION','DECEASED','RETIREMENT'))) " +
					"AND (mem.paidMember IS NULL OR (mem.paidMember IS NOT NULL AND mempaidmem.status in ('D','F','R'))) " +
					"ORDER BY mem.ID ")
					.list()
					;
			
			tx.commit();
			
			return memberList;
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
	public Member getMember(Long id){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Member member = (Member) session.createQuery(
					"SELECT distinct mem " +
					"FROM Member mem " +
					"left join fetch mem.employee emp " +
					"left join fetch mem.mainMember main " +
					"left join fetch main.employee mainemp " +
					"left join fetch mem.balance " +
					"WHERE mem.ID = :pid")
					.setParameter("pid", id)
					.uniqueResult()
					;
			
			tx.commit();
			
			return member;
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
	public int getMemberAmount(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Long memberAmount = (Long) session.createQuery(
					"SELECT COUNT(*) " +
					"FROM Member mem " +
					"WHERE mem.status = 'M'")
					.uniqueResult()
					;
			
			tx.commit();
			
			return Integer.parseInt(Long.toString(memberAmount));
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
	
	@SuppressWarnings("unchecked")
	public List<Member> getMemeberListWithPayment(Date fromDate, Date toDate){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Member> memberList = session.createQuery(
					"SELECT distinct mem " +
					"FROM Member mem " +
					"left join fetch mem.payments payment " +
					"left join fetch mem.balance bal " +
					"WHERE payment.transactionDate >= :pfromdate " +
					"AND payment.transactionDate <= :ptodate " +
					"AND mem.status = 'M' " +
					"ORDER BY payment.ID")
					.setParameter("pfromdate", fromDate)
					.setParameter("ptodate", toDate)
					.list()
					;
			
			tx.commit();
			
			return memberList;
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
	public List<Member> getDebtMemberList(){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Member> memberList = session.createQuery(
					"SELECT distinct mem " +
					"FROM Member mem " +
					"left join fetch mem.warning " +
					"left join fetch mem.balance bal " +
					"WHERE bal.currentBalance < 100 " +
					"AND mem.status = 'M' " +
					"ORDER BY mem.ID ")
					.list()
					;
			
			tx.commit();
			
			return memberList;
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
	
	public void fireMember(Member member, List<Debt> debtList){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(member);
			
			if(debtList.size() != 0){
				for(Debt debt : debtList){
					debt.setDebtStatus(DebtStatus.BAD_DEBT);
					session.saveOrUpdate(debt);
					
					CurrentBalance currentBalance = paymentController.getCurrentBalance(debt.getPaidForMember());
					currentBalance.setDebtAmount(currentBalance.getDebtAmount().subtract(new BigDecimal("20.00")));
					paymentController.saveCurrentBalance(currentBalance);
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
	
//	public List<Employee> getEmpQuery(){
//		SessionFactory sf = HibernateUtil.getSessionFactory();
//		Session session = null;
//		Transaction tx = null;
//
//		try {
//			session = sf.openSession();
//			tx = session.beginTransaction();
//			
//			List<Employee> empList = session.createQuery(
//					"SELECT emp " +
//					"FROM Employee emp ")
//					.list()
//					;
//			tx.commit();
//			
//			return empList;
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (tx != null) {
//				tx.rollback();
//			}
//			return null;
//		} finally {
//			session.clear();
//			session.close();
//		}
//	}
}
