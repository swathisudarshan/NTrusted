package ntrusted.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

  @Id
  @Column(name="fbId")
  //@GeneratedValue(strategy = GenerationType.AUTO)
  private String fbId;
  
  @NotNull
  @Column(name="name")
  @Size(min = 3, max = 80)
  private String name;
  
  @NotNull
  @Column(name="email")
  @Size(min = 2, max = 80)
  private String email;
  
  @NotNull
  @Column(name="phoneNumber")
  private String phoneNumber;
  
  @NotNull
  @Column(name="address")
  private String address;
  
 /* @OneToMany(mappedBy="renter", fetch=FetchType.EAGER)
  private Collection<Transaction> renter;
  
  @OneToMany(mappedBy="rentee", fetch=FetchType.EAGER)
  private Transaction rentee;

  @OneToMany(mappedBy="fbId", fetch=FetchType.EAGER)
  private Request sender;
  
  @OneToOne(mappedBy="fbId", fetch=FetchType.EAGER)
  private Request receiver
  ;*/
  
 // @OneToMany(mappedBy="fbId", fetch=FetchType.EAGER)
 // private Collection<Advertisement> userId;

  public User() { }

  public User(String id) { 
	    this.fbId = id;
	    }
  
  public User(String id,String name, String email,String phone, String address) { 
    this.fbId = id;
    this.name= name;
    this.email = email;
    this.phoneNumber = phone;
    this.address = address;
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
	

/*	public Collection<Request> getRenter() {
		return renter;
	}

	public void setRenter(Collection<Request> renter) {
		this.renter = renter;
	}

	public Collection<Request> getRentee() {
		return rentee;
	}

	public void setRentee(Collection<Request> rentee) {
		this.rentee = rentee;
	}
  */
} // class User
