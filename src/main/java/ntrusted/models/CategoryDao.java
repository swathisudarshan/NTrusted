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
	
	@SuppressWarnings("unchecked")
	public List<Category> getAll() {
		return getSession().createQuery("from Category").list();
	}
	
	public Category getById(int id){
		System.out.println("id is: "+id);
		 /*return (Category) getSession().createQuery(
			        "from Category where categoryId = :id")
			        .setParameter("categoryId", id)
			        .uniqueResult();*/
		Category category = (Category) getSession().load(Category.class, id);
		System.out.println("name is: "+(Category) getSession().createQuery(
		        "from Category where categoryId = :id")
		        .setParameter("id", id)
		        .uniqueResult());
		//return (Category) getSession().load(Category.class, id);
		return (Category) getSession().createQuery(
		        "from Category where categoryId = :id")
		        .setParameter("id", id)
		        .uniqueResult();
	}

	/*public Category getCategory(int id)  throws RecordNotFoundException
	{   
	    StockCategory stock_category= (StockCategory) getCurrentSession().get(StockCategory.class, id);

	    return stock_category;
	}*/
	
	
	public void update(Category category){
		getSession().update(category);
		return;
	}
}// class CategoryDao


/*
 
  public User getByEmail(String email) {
    return (User) getSession().createQuery(
        "from User where email = :email")
        .setParameter("email", email)
        .uniqueResult();
  }
} // class UserDao

*/