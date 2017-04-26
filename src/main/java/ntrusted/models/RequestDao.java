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
		System.out.println("Inside Request Dao getByRequestId");
		return (Request) getSession().createQuery(
		        "from Request where requestId = :id")
		        .setParameter("id", requestId)
		        .uniqueResult();
		//return req;
	}
	
	public void save(Request request) {
		System.out.println("Inside save request");
		getSession().save(request);
		return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> getAll() {
		return getSession().createQuery("from Request").list();
	}
	
	public void update(Request request) {
		System.out.println("Inside requestDao");
		  getSession().update(request);
	      return;
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> getBorrowRequest(String receiverId) {
	    return (List<Request>) getSession().createQuery(
	    		"from Request r join r.advertisement a join a.category c "
			        + "where r.receiver = "+ receiverId +" and r.requestType= 1 and r.response =1" 
			        + " ORDER BY requestDate DESC" ).list();	        
    }
	
	@SuppressWarnings("unchecked")
	public List<Request> getBorrowRequestforCat(String receiverId, int catId) {
		
		 return (List<Request>) getSession().createQuery(
			          "from Request r join r.advertisement a join a.category c "
			        + "where r.receiver = "+ receiverId +" and r.requestType= 1 and r.response =1 and c.categoryId = " + catId 
			        + " ORDER BY requestDate DESC" ).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> getLendRequest(String receiverId) {
	    return (List<Request>) getSession().createQuery(
	    		"from Request r join r.advertisement a join a.category c "
				        + "where r.receiver = "+ receiverId +" and r.requestType= 2 and r.response =1" 
				        + " ORDER BY requestDate DESC" ).list();	        
    }

	@SuppressWarnings("unchecked")
	public List<Request> getLendRequestforCat(String receiverId, int catId) {
	    
		 return (List<Request>) getSession().createQuery(
		          "from Request r join r.advertisement a join a.category c "
		        + "where r.receiver = "+ receiverId +" and r.requestType= 2 and r.response =1 and c.categoryId = " + catId 
		        + " ORDER BY requestDate DESC" ).list();	        
    }
	
	
}
