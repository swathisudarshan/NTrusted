package hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="USERINFO")
public class UserInfo {
    @Id
    @Column(name="fb_id")   
    private String fb_id;

    private String name;
    
    private String phone_number;
    
    private String email;

	
	public String getFb_id() {
		return fb_id;
	}

	public void setFb_id(String fbId) {
		this.fb_id = fbId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phoneNumber) {
		this.phone_number = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}