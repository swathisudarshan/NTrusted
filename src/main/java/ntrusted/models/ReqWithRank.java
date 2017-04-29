package ntrusted.models;

public class ReqWithRank {

	public Request request;
	double rank;
	
	
	public ReqWithRank(Request request, double rank) {
		super();
		this.request = request;
		this.rank = rank;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	
	
}
