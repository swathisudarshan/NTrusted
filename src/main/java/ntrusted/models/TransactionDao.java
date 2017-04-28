package ntrusted.models;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class TransactionDao {
	@Autowired
	private SessionFactory _sessionFactory;
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	public Transaction getTrxById(int trxId) {
		return (Transaction) getSession().createQuery(
				//"select t from Transaction t join t.renter r1 join t.rentee r2 join t.ad a join t.request r"
				"from Transaction t"
			  + " where t.transactionId = "+ trxId).uniqueResult();
	}
	
	public void save(Transaction transaction) {
		getSession().save(transaction);
		return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getAll() {
		return getSession().createQuery("from Transaction").list();
	}
	
	public void update(Transaction transaction) {
		  getSession().update(transaction);
	      return;
	}
	
	//Renter Transaction lists
	@SuppressWarnings("unchecked")
	public List<Transaction> getRenterActiveTransaction(User user) {
		String id = user.getFbId();
		return (List<Transaction>) getSession().createQuery(
				  "select t from Transaction t join t.renter r "
				+ "where t.status = 1 and r.fbId = "+ id).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getRenterCloseReqTran(User user) {
		String id = user.getFbId();
		return (List<Transaction>) getSession().createQuery(
				  "select t from Transaction t join t.renter r where r.fbId = "+ id 
				+ " and t.renterClose = 1 and t.renteeClose =0").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getRenterClosedTran(User user) {
		String id = user.getFbId();
		return (List<Transaction>) getSession().createQuery(
				  "select t from Transaction t join t.renter r where r.fbId = "+ id 
				+ " and t.renterClose = 1 and t.renteeClose =1").list();
		//return getSession().createQuery("from Transaction where and renter = "+ user.getFbId() +" and renterClose = 1 and renteeClose = 1").list();
	}
	
	// Rentee Transaction lists
	@SuppressWarnings("unchecked")
	public List<Transaction> getRenteeActiveTransaction(User user) {
		String id = user.getFbId();
		return (List<Transaction>) getSession().createQuery(
				  "select t from Transaction t join t.rentee r "
				+ "where t.status = 1 and r.fbId = "+ id).list();
		//return getSession().createQuery("from Transaction where status = 1 and rentee = "+ user.getFbId()).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getRenteeCloseReqTran(User user) {
		String id = user.getFbId();
		return (List<Transaction>) getSession().createQuery(
				  "select t from Transaction t join t.rentee r where r.fbId = "+ id 
				+ " and t.renterClose = 0 and t.renteeClose =1").list();
		//return getSession().createQuery("from Transaction where and rentee = "+ user.getFbId() +" and renteeClose = 1 and renterClose =0").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getRenteeClosedTran(User user) {
		String id = user.getFbId();
		return (List<Transaction>) getSession().createQuery(
				  "select t from Transaction t join t.rentee r where r.fbId = "+ id 
				+ " and t.renterClose = 1 and t.renteeClose =1").list();
		//return getSession().createQuery("from Transaction where and rentee = "+ user.getFbId() +" and renterClose = 1 and renteeClose = 1").list();
	}

	
}
