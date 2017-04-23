package ntrusted.models;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	
	@SuppressWarnings("unchecked")
	public List<Advertisement> getAll() {
		return getSession().createQuery("from Advertisement").list();
	}
	
	public Advertisement getByProductName(String name) {
	    return (Advertisement) getSession().createQuery(
		        "from Advertisement where productName = :name")
		        .setParameter("productName", name)
		        .uniqueResult();
    }

	public Advertisement getById(int id) {
	    return (Advertisement) getSession().load(Advertisement.class, id);
	}


	public void update(Advertisement advertisement) {
	  getSession().update(advertisement);
      return;
	}
	

	
}





