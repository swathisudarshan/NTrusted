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

@Entity
@Table(name="transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="adId")
	private Advertisement ad;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="requestId")
	private Request request;
	
	@NotNull
	@Column(name="renterRating")
	private float renterRating;
	
	@NotNull
	@Column(name="renteeRating")
	private float renteeRating;

	@NotNull
	@Column(name="stratDate")
	private Date startDate;
	
	@Column(name="endDate")
	private Date endDate;

	@NotNull
	@Column(name="renterClose")
	private Boolean renterClose;
	
	@NotNull
	@Column(name="renteeClose")
	private Boolean renteeClose;
	
	public Transaction(){
		
	}

	public Transaction(int transactionId, Advertisement ad, Request request, float renterRating, float renteeRating,
			Date startDate, Date endDate, Boolean renterClose, Boolean renteeClose) {
		this.transactionId = transactionId;
		this.ad = ad;
		this.request = request;
		this.renterRating = renterRating;
		this.renteeRating = renteeRating;
		this.startDate = startDate;
		this.endDate = endDate;
		this.renterClose = renterClose;
		this.renteeClose = renteeClose;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Advertisement getAd() {
		return ad;
	}

	public void setAd(Advertisement ad) {
		this.ad = ad;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public float getRenterRating() {
		return renterRating;
	}

	public void setRenterRating(float renterRating) {
		this.renterRating = renterRating;
	}

	public float getRenteeRating() {
		return renteeRating;
	}

	public void setRenteeRating(float renteeRating) {
		this.renteeRating = renteeRating;
	}

	public Boolean getRenterClose() {
		return renterClose;
	}

	public void setRenterClose(Boolean renterClose) {
		this.renterClose = renterClose;
	}

	public Boolean getRenteeClose() {
		return renteeClose;
	}

	public void setRenteeClose(Boolean renteeClose) {
		this.renteeClose = renteeClose;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
