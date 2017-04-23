package ntrusted.mongoModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;


public class Customer {

    @Id
    private String customerId;

    private List<Connection> Connections = new ArrayList<Connection>();
  
	public Customer() {}

    public Customer(String userid, List<Connection> connections) {
		super();
		this.customerId = userid;
		this.Connections = connections;
	}

	@Override
	public String toString() {
		return "Customer [userid=" + customerId + ", Connections=" + Connections + "]";
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String userid) {
		this.customerId = userid;
	}

	public List<Connection> getConnections() {
		return this.Connections;
	}
	public void setConnections(List<Connection> connections) {
		this.Connections = connections;
	}
	public void addConnection(Connection con)
	{
		this.Connections.add(con);
	}
	 
}