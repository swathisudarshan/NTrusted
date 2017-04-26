package ntrusted.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class CategoryDao {
	@Autowired 
	private SessionFactory _sessionFactory;
	
	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}
	
	public void save(Category category) {
		getSession().save(category);
		return;
	}
	
	public void delete(Category category) {
		getSession().delete(category);
		return;
	}
	
	//working Fine
	@SuppressWarnings("unchecked")
	public List<Category> getAll() {
		return getSession().createQuery("from Category").list();
	}
	
	//working Fine
	public Category getById(int id){
		System.out.println("id is: "+id);
		return (Category) getSession().createQuery(
		        "from Category where categoryId = :id")
		        .setParameter("id", id)
		        .uniqueResult();
	}
		
	public void update(Category category){
		getSession().update(category);
		return;
	}

}// class CategoryDao

