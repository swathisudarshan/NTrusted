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
import javax.persistence.OneToOne;
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="sender")
	private User sender;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="receiver")
	private User receiver;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="adId")
	private Advertisement advertisement;
	
	@NotNull
	@Column(name="requestDate")
	private Date requestDate;
	
	//Response from receiver end: int 1: active, 0:declined 2:Accepted
	@NotNull
	@Column(name="response")
	private int response;
	
	//Request Type: Who is sending the request - int 1: borrow 2:lend
	@NotNull
	@Column(name="requestType")
	private int requestType;
	
	public Request(){
		
	}

	public Request(User sender, User receiver, Advertisement advertisement, int response, int requestType,Date reqDate) {
		
		this.sender = sender;
		this.receiver = receiver;
		this.advertisement = advertisement;
		this.response = response;
		this.requestType = requestType;
		this.requestDate = reqDate;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	
}
