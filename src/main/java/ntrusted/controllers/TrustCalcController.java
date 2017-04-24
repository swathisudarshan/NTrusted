package ntrusted.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import ntrusted.mongoModel.Connection;
import ntrusted.mongoModel.Customer;


@EnableMongoRepositories(basePackages = "ntrusted.controllers")
@Service
public class TrustCalcController {

	 @Autowired
	  private CustomerRepository cuRepo;
	
//	public HashMap<Customer,Double> getRecommendedRenter(ArrayList<String> list)
//	{
//		HashMap<Customer,Double> RecoCustomers = new HashMap<Customer,Double>();
//		for(String userId:list)
//		{
//			RecoCustomers.put(cuRepo.findByUserId(userId), calcRenterRank(userId));
//		}
//		
//		return RecoCustomers;
//	}
	public double calcRenterRank(String RenteeId,String RenterId)
	{
		System.out.println("Inside calcRenterRank");
		double Rank = 0.0;
		
		double directTrust = 0.0;
		int friendTrust = 0;
		double reputation = 0.0;
		
		Customer cus1 = cuRepo.findByUserId(RenterId);
	//	System.out.println("Customer obtained is **********"+cus1.toString());
		List<Connection> cons = cus1.getConnections();
		//System.out.println("Inside calcRenterRank");
	//	System.out.println("Connections are: "+cons);
		double PosTrxCnt = 0.0;
		double PosSum = 0.0;
		double NegSum = 0.0;
		double NegTrxCnt = 0.0;
		for(Connection con1 : cons)
		{
		//	System.out.println("Connection is: "+con1);
			if(con1.getUserid().equals(RenteeId))
			{
				directTrust = con1.getRenterTrust();
				
				friendTrust = con1.getActiveFriend();
				
			}
			else{
				double val = con1.getRenterTrust();
			//	System.out.println("Value of renter trust: "+val);
				if(val >0)
				{
					PosTrxCnt++;
					if(CompareThreshold(val))
					{
						PosSum += val * 0.6; 
					}
					else
					{
						PosSum += val*0.4;
					}
				}
				else
				{
					NegTrxCnt++;
					if(CompareThreshold(val))
					{
						NegSum += val * 0.6; 
					}
					else
					{
						NegSum += val*0.4;
					}
				}
			}
			
		}
		if(PosTrxCnt!=0)
		{
			reputation += (PosSum / PosTrxCnt);
		}
		if(NegTrxCnt != 0)
		{
			reputation += (NegSum /NegTrxCnt);
		}
		
	//	System.out.println("Direct Trust of: "+ RenterId +" is "+directTrust);
	//	System.out.println("Positive avg: "+(PosSum / PosTrxCnt));
	//	System.out.println("Negative avg: "+(NegSum /NegTrxCnt));
		//System.out.println("Reputation for: "+ RenterId +" is "+reputation);
		
		if(directTrust <= -1 )
		{
			Rank = ( 0.3 * reputation ) + ( 0.6 * directTrust ) + ( 0.1 * friendTrust );
		}
		else
		{
			//Rank Formulae
			Rank = ( 0.6 * reputation ) + ( 0.3 * directTrust ) + ( 0.1 * friendTrust );
		}
	//	System.out.println("Rank is inside CalcRenterRank: "+Rank);
		return Rank;
	}
	
	public boolean CompareThreshold(double val)
	{
		if(Math.abs(val)>1)
			return true;
		return false;
	}
}
