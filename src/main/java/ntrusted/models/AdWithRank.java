package ntrusted.models;

public class AdWithRank {
	Advertisement advertisement;
	double rank;
	
	
	public AdWithRank(Advertisement advertisement, Double double1) {
		super();
		this.advertisement = advertisement;
		this.rank = double1;
	}
	public Advertisement getAdvertisement() {
		return advertisement;
	}
	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(float rank) {
		this.rank = rank;
	}

}
