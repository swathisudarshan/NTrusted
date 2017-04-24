package ntrusted.mongoModel;


public class Connection {
	private String userid;
	private float RenterTrust;
	private int RenterNoTrx;
	private float RenteTrust;
	private int RenteeNoTrx;
	private int activeFriend;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public float getRenterTrust() {
		return RenterTrust;
	}
	public void setRenterTrust(float renterTrust) {
		RenterTrust = renterTrust;
	}
	public int getRenterNoTrx() {
		return RenterNoTrx;
	}
	public void setRenterNoTrx(int renterNoTrx) {
		RenterNoTrx = renterNoTrx;
	}
	public float getRenteTrust() {
		return RenteTrust;
	}
	public void setRenteTrust(float renteTrust) {
		RenteTrust = renteTrust;
	}
	public int getRenteeNoTrx() {
		return RenteeNoTrx;
	}
	public void setRenteeNoTrx(int renteeNoTrx) {
		RenteeNoTrx = renteeNoTrx;
	}
	
	public int getActiveFriend() {
		return activeFriend;
	}

	public void setActiveFriend(int activeFriend) {
		this.activeFriend = activeFriend;
	}

	@Override
	public String toString() {
		return "Connection [userid=" + userid + ", RenterTrust=" + RenterTrust + ", RenterNoTrx=" + RenterNoTrx
				+ ", RenteTrust=" + RenteTrust + ", RenteeNoTrx=" + RenteeNoTrx + "]";
	}
	
	
}
