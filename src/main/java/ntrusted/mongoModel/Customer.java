package ntrusted.mongoModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;


public class Customer {

    @Id
    private int userId;

    private List<Connection> Connections = new ArrayList<Connection>();
    
    private int activeFriend;

   



	public Customer() {}

    

    public Customer(int userid, List<Connection> connections) {
		super();
		this.userId = userid;
		this.Connections = connections;
	}

	@Override
	public String toString() {
		return "Customer [userid=" + userId + ", Connections=" + Connections + "]";
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserid(int userid) {
		this.userId = userid;
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
	 public int getActiveFriend() {
			return activeFriend;
		}

	public void setActiveFriend(int activeFriend) {
			this.activeFriend = activeFriend;
		}
}