package ntrusted.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  private String fbId;
  
  @NotNull
  @Size(min = 3, max = 80)
  private String name;
  
  @NotNull
  @Size(min = 2, max = 80)
  private String email;
  
  @NotNull
  private String phoneNumber;
  
  private String address;

  public User() { }

  public User(String id) { 
	    this.fbId = id;
	    }
  
  public User(String id,String name, String email,String phone) { 
    this.fbId = id;
    this.name= name;
    this.email = email;
    this.phoneNumber = phone;
  }

	public String getFbId() {
		return fbId;
	}
	
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
  
} // class User
