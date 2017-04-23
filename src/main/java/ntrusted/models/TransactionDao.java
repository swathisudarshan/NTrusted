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
	
	public void save(Transaction transaction) {
		getSession().save(transaction);
		return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getAll() {
		return getSession().createQuery("from Transacition").list();
	}
	
	public void update(Transaction transaction) {
		  getSession().update(transaction);
	      return;
	}
	
	//Renter Transaction lists
	public List<Transaction> getRenterActiveTransaction(User user) {
		return getSession().createQuery("from Transaction where status = 1 and renter = "+ user.getFbId()).list();
	}
	
	public List<Transaction> getRenterCloseReqTran(User user) {
		return getSession().createQuery("from Transaction where and renter = "+ user.getFbId() +" and renterClose = 1 and renteeClose =0").list();
	}
	
	public List<Transaction> getRenterClosedTran(User user) {
		return getSession().createQuery("from Transaction where and renter = "+ user.getFbId() +" and renterClose = 1 and renteeClose = 1").list();
	}
	
	// Rentee Transaction lists
	public List<Transaction> getRenteeActiveTransaction(User user) {
		return getSession().createQuery("from Transaction where status = 1 and rentee = "+ user.getFbId()).list();
	}
	
	public List<Transaction> getRenteeCloseReqTran(User user) {
		return getSession().createQuery("from Transaction where and rentee = "+ user.getFbId() +" and renteeClose = 1 and renterClose =0").list();
	}
	
	public List<Transaction> getRenteeClosedTran(User user) {
		return getSession().createQuery("from Transaction where and rentee = "+ user.getFbId() +" and renterClose = 1 and renteeClose = 1").list();
	}

	
}
