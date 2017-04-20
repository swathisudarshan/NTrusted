package ntrusted.models;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

@Entity
@Table(name="request")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int requestId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="renterId")
	private User renter;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="renteeId")
	private User rentee;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="adId")
	private Advertisement advertisement;
	
	@NotNull
	@Column(name="requestDate")
	private Date requestDate;
	
	@NotNull
	@Column(name="response")
	private int response;
	
	public Request(){
		
	}

	public Request(int requestId, User renter, User rentee, Advertisement advertisement, int response) {
		this.requestId = requestId;
		this.renter = renter;
		this.rentee = rentee;
		this.advertisement = advertisement;
		this.response = response;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public User getRenter() {
		return renter;
	}

	public void setRenter(User renter) {
		this.renter = renter;
	}

	public User getRentee() {
		return rentee;
	}

	public void setRentee(User rentee) {
		this.rentee = rentee;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
}
