package ntrusted.models;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class RequestDao {

	@Autowired
	private SessionFactory _sessionFactory;
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	public Request getRequestById(int requestId) {
		Request req = (Request) getSession().createQuery("from Request where requestId = "+ requestId);
		return req;
	}
	
	public void save(Request request) {
		getSession().save(request);
		return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> getAll() {
		return getSession().createQuery("from Request").list();
	}
	
	public void update(Request request) {
		  getSession().update(request);
	      return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> getBorrowRequest(String receiverId) {
	    return (List<Request>) getSession().createQuery(
		        "from Request where receiver = "+ receiverId +" and requestType= 1 and response =1 ORDER BY requestDate DESC" ).list();	        
    }
	
	@SuppressWarnings("unchecked")
	public List<Request> getBorrowRequestforCat(String receiverId, int catId) {
		
		 return (List<Request>) getSession().createQuery(
			        "from Request r join Advertisement a on (r.adId = a.adId) where receiver = "+ receiverId +" and requestType= 1 and response =1 and a.categoryId = " + catId +" ORDER BY requestDate DESC" ).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> getLendRequest(String receiverId) {
	    return (List<Request>) getSession().createQuery(
		        "from Request where receiver = "+ receiverId +" and requestType= 2 and response =1 ORDER BY requestDate DESC" ).list();	        
    }

	@SuppressWarnings("unchecked")
	public List<Request> getLendRequestforCat(String receiverId, int catId) {
	    return (List<Request>) getSession().createQuery(
		        "from Request where receiver = "+ receiverId +" and requestType= 2 and response =1 ORDER BY requestDate DESC" ).list();	        
    }
	
	
}
