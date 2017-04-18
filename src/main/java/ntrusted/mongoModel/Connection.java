package ntrusted.mongoModel;

public class Connection {
	private int userid;
	private double RenterTrust;
	private int RenterNoTrx;
	private double RenteTrust;
	private int RenteeNoTrx;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public double getRenterTrust() {
		return RenterTrust;
	}
	public void setRenterTrust(double renterTrust) {
		RenterTrust = renterTrust;
	}
	public int getRenterNoTrx() {
		return RenterNoTrx;
	}
	public void setRenterNoTrx(int renterNoTrx) {
		RenterNoTrx = renterNoTrx;
	}
	public double getRenteTrust() {
		return RenteTrust;
	}
	public void setRenteTrust(double renteTrust) {
		RenteTrust = renteTrust;
	}
	public int getRenteeNoTrx() {
		return RenteeNoTrx;
	}
	public void setRenteeNoTrx(int renteeNoTrx) {
		RenteeNoTrx = renteeNoTrx;
	}
	@Override
	public String toString() {
		return "Connection [userid=" + userid + ", RenterTrust=" + RenterTrust + ", RenterNoTrx=" + RenterNoTrx
				+ ", RenteTrust=" + RenteTrust + ", RenteeNoTrx=" + RenteeNoTrx + "]";
	}
	
	
}