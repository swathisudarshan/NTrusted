package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name="PRODUCT")
public class Product {

	@Id
	@Column(name="product_id")
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    private Integer product_id;
		
		private String fb_id;
		
	    private String product_name;
	    
	    private String product_description;
	    
	    private float product_price;
	    
	    private float product_rating;
	    
		public Integer getProduct_id() {
			return product_id;
		}

		public void setProduct_id(Integer product_id) {
			this.product_id = product_id;
		}

		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "fb_id")
		private UserInfo userInfo;
		
		public String getFb_id() {
			return userInfo.getFb_id();
		}

		public void setUser_id(String user_id) {
			this.fb_id = user_id;
		}

		public String getProduct_name() {
			return product_name;
		}

		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}

		public String getProduct_description() {
			return product_description;
		}

		public void setProduct_description(String product_description) {
			this.product_description = product_description;
		}

		public float getProduct_price() {
			return product_price;
		}

		public void setProduct_price(float product_price) {
			this.product_price = product_price;
		}

		public float getProduct_rating() {
			return product_rating;
		}

		public void setProduct_rating(float product_rating) {
			this.product_rating = product_rating;
		}
	    

		
}
