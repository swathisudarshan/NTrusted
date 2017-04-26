package ntrusted.models;

import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AdvertisementDao {

	@Autowired
	private SessionFactory _sessionFactory;
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	public void save(Advertisement advertisement) {
		getSession().save(advertisement);
		return;
	}
	public void delete(Advertisement advertisement) {
		getSession().delete(advertisement);
		return;
	}
	
	//working Fine. Output: full JSON along with all the nested JSONs -- Test for the time calues being fetched!!
	@SuppressWarnings("unchecked")
	public List<Advertisement> getAll() {
		System.out.println("Inside get All ads in AdvertisementDao");
		
		return getSession().createQuery("from Advertisement a join a.category c join a.user u "
				+ "where a.active = :value"
				+ " ORDER BY a.postDate DESC").setParameter("value", 1).list();
		} 
	
	@SuppressWarnings("unchecked")
	public List<Advertisement> getAllBorrowAds() {
		return (List<Advertisement>)getSession().createQuery(
				"from Advertisement a join a.category c join a.user u "
				+ "where a.active = 1 and adType = 2"
				+ " ORDER BY a.postDate DESC").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Advertisement> getAllLendAds() {
		return (List<Advertisement>)getSession().createQuery(
				"from Advertisement a join a.category c join a.user u "
						+ "where a.active = 1 and adType = 1"
						+ " ORDER BY a.postDate DESC").list();
	}
	
	public Advertisement getByProductName(String name) {
	    return (Advertisement) getSession().createQuery(
		        "from Advertisement where productName = :name")
		        .setParameter("productName", name)
		        .uniqueResult();
    }

	public Advertisement getById(int id) {
	    return (Advertisement) getSession().createQuery(
		        "from Advertisement where adId = :id")
		        .setParameter("id", id)
		        .uniqueResult();
	}

	public void update(Advertisement advertisement) {
		System.out.println("Inside Advertisement update");
	  getSession().update(advertisement);
      return;
	}
		
	public List<Advertisement> getAds(int id) {
		try{
			Query query = getSession().createQuery("from Advertisement where categoryId = "+id+"and active = 1");
			@SuppressWarnings("unchecked")
			List<Advertisement> ads = query.list();
			return ads;
		}catch(Exception e){
			return null;
		}		
	}
	
	//If one wants to view list of ads from whom they can borrow item
	public List<Advertisement> getLendingAds(int id) {
		try{
			Query query = getSession().createQuery("from Advertisement where categoryId = "+id+"and active = 1 and adType = 1");
			@SuppressWarnings("unchecked")
			List<Advertisement> ads = query.list();
			return ads;
		}catch(Exception e){
			return null;
		}		
	}
	
	//If one wants to view list of ads to whom they can lend/rent item
		public List<Advertisement> getBorrowingAds(int id) {
			try{
				Query query = getSession().createQuery("from Advertisement where categoryId = "+id+"and active = 1 and adType = 2");
				@SuppressWarnings("unchecked")
				List<Advertisement> ads = query.list();
				System.out.println("Ads are : "+ ads.toString());
				return ads;
			}catch(Exception e){
				return null;
			}		
		}
	
}





