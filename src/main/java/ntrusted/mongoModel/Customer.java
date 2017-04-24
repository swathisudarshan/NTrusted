package ntrusted.mongoModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;


public class Customer {

    @Id
    private String userId;

    private List<Connection> Connections = new ArrayList<Connection>();

	public Customer() {}

    public Customer(String userid, List<Connection> connections) {
		super();
		this.userId = userid;
		this.Connections = connections;
	}

	@Override
	public String toString() {
		return "Customer [userid=" + userId + ", Connections=" + Connections + "]";
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserid(String userid) {
		this.userId = userid;
	}

	public List<Connection> getConnections() {
		return this.Connections;
	}
	public void setConnections(List<Connection> connections) {
		this.Connections = connections;
	}
	
	public Connection getCon(String userId)
	{
		List<Connection> list = this.getConnections();
		for(Connection con : list)
		{
			if(con.getUserid().equals(userId))
				return con;
		}
		return null;
	}
	public void addConnection(Connection con)
	{
		this.Connections.add(con);
	}
	 
}