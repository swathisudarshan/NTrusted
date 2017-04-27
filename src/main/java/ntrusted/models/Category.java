package ntrusted.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="category")
public class Category {
	
	@Id
	  private int categoryId;
	
	@NotNull
	  private String categoryName;

	//@OneToMany(mappedBy="categoryId", fetch=FetchType.EAGER)
	//  private Collection<Advertisement> advertisement;
	
	public Category() {
		super();
	}

	public Category(int categoryId) {
		super();
		this.categoryId = categoryId;
	}

	public Category(int categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public String toString(){
		return String.valueOf(getCategoryId());
	}
	
}
