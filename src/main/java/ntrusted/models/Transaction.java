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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="renter")
	private User renter;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="rentee")
	private User rentee;
	
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
	private int renterClose;
	
	@NotNull
	@Column(name="renteeClose")
	private int renteeClose;
	
	@NotNull
	@Column(name="status")
	private int status;
	
	public Transaction(){
		
	}

	public Transaction(Advertisement ad, Request request, float renterRating, float renteeRating,
			Date startDate, Date endDate, int renterClose, int renteeClose, User renter, User rentee, int status) {
		
		this.ad = ad;
		this.request = request;
		this.renterRating = renterRating;
		this.renteeRating = renteeRating;
		this.startDate = startDate;
		this.endDate = endDate;
		this.renterClose = renterClose;
		this.renteeClose = renteeClose;
		this.renter = renter;
		this.rentee = rentee;
		this.status = status;
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

	public int getRenterClose() {
		return renterClose;
	}

	public void setRenterClose(int renterClose) {
		this.renterClose = renterClose;
	}

	public int getRenteeClose() {
		return renteeClose;
	}

	public void setRenteeClose(int renteeClose) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
