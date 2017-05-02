package ntrusted.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ntrusted.models.AdWithRank;

import ntrusted.models.Advertisement;
import ntrusted.models.AdvertisementDao;
import ntrusted.models.ReqWithRank;

import ntrusted.models.Request;
import ntrusted.models.RequestDao;
import ntrusted.models.Transaction;
import ntrusted.models.TransactionDao;
import ntrusted.models.User;
import ntrusted.models.UserDao;


@Controller
@RequestMapping(value="/request")
public class RequestController {

	@Autowired
	  private RequestDao _reDao;
	@Autowired
	 private UserDao _userDao;
	@Autowired
	private AdvertisementDao _adDao;
	@Autowired
	private TransactionDao _trxDao;

	@Autowired
	private TrustCalcController TCC;
	
	List<ReqWithRank> SortedBorrowRequestsforCat = new ArrayList<ReqWithRank>();
	List<ReqWithRank> SortedLendRequestsforCat = new ArrayList<ReqWithRank>();
	
	//Send request to renter asking for his / her item 
	//working
	@RequestMapping(value="/addBorrowRequest", method=RequestMethod.POST)
	@ResponseBody
	public String createBorrowRequest(@RequestParam(value="adId")String adId,
			  			   @RequestParam(value="senderId") String senderId
			  			   ) 
	{
		//requestType: 1 
		Advertisement ad = _adDao.getById(Integer.valueOf(adId));
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		Request req = new Request(_userDao.getById(senderId),ad.getUser(), ad, 1, 1,date);	
		_reDao.save(req);
		return "Borrowing Request Added in Request table";
	}
	
	//Send request to borrower informing the item he / she needs is available
	//working
	@RequestMapping(value="/addLendingRequest", method=RequestMethod.POST)
	@ResponseBody
	public String createLendRequest(@RequestParam(value="adId")String adId,
			  			   @RequestParam(value="senderId") String senderId 
			  			   ) 
	{
		//requestType: 2 
		Advertisement ad = _adDao.getById(Integer.valueOf(adId));
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		Request req = new Request(_userDao.getById(senderId),ad.getUser(), ad, 1, 2,date);	
		_reDao.save(req);
		return "Lending Request Added in Request table";
	}
	 	
	//Requests from someone who wants to borrwo my items
	//working
	@RequestMapping(value="/getBorrowRequests", method=RequestMethod.GET)
	@ResponseBody
	public List<Request> getBorrowRequests(String receiverId)
	{
		return (List<Request>)_reDao.getBorrowRequest(receiverId);
	}
	
	//Requests from someone who wants to borrwo my perticular category
	//working
		@RequestMapping(value="/getBorrowRequestsforCat", method=RequestMethod.GET)
		@ResponseBody
		public List<ReqWithRank> getBorrowRequestsforCat(String receiverId,String catId)
		{ 
			List<Request> reqs;
		    
		    HashMap<Request,Double> UnsortedResult = new HashMap<Request,Double>();
		    SortedLendRequestsforCat.clear();
		    SortedBorrowRequestsforCat.clear();
		    
		    try{
		      reqs = _reDao.getBorrowRequestforCat(receiverId, Integer.valueOf(catId));
		      System.out.println("getBorrowing reqs: "+reqs.toString());
		      //Get List of user Id's for each req
		      //List<String> userIds = new ArrayList<String>();
		      for(Request req: reqs)
		      {
		    	  System.out.println("In for get Borrowing req: "+req.toString());
		    	  String tempUserID = req.getSender().getFbId();
		    	  UnsortedResult.put(req,TCC.calcRenteeRank(tempUserID,receiverId));
		      }

		      Map<Request, Double> sortedResult = sortByValue(UnsortedResult);
		      System.out.println(sortedResult.toString());
		      
		      for(Map.Entry<Request,Double> entry:sortedResult.entrySet())
		      {
		    	  ReqWithRank obj1 = new ReqWithRank(entry.getKey(),entry.getValue());
		    	  SortedBorrowRequestsforCat.add(obj1);
		      }
		      //System.out.println("The List is ==============   "+sortedRenteeAds.toString());
		    }
		    catch(Exception ex) {
		    	System.out.println(ex);
		      return null;
		    }
		    return SortedBorrowRequestsforCat;
		}
		
		//Requests from someone who wants to lend me perticular category
		//working
			@RequestMapping(value="/getLendingRequestsforCat", method=RequestMethod.GET)
			@ResponseBody
			public List<ReqWithRank> getLendingRequestsforCat(String receiverId,String catId)
			{ 
				List<Request> reqs;
			    
			    HashMap<Request,Double> UnsortedResult = new HashMap<Request,Double>();
			    SortedLendRequestsforCat.clear();
			    SortedBorrowRequestsforCat.clear();
			    
			    try{
			      reqs = _reDao.getLendRequestforCat(receiverId, Integer.valueOf(catId));
			      System.out.println("getLending reqs: "+reqs.toString());
			      //Get List of user Id's for each req
			      //List<String> userIds = new ArrayList<String>();
			      for(Request req: reqs)
			      {
			    	  System.out.println("In for get Lending req: "+req.toString());
			    	  String tempUserID = req.getSender().getFbId();
			    	  UnsortedResult.put(req,TCC.calcRenterRank(receiverId,tempUserID));
			      }

			      Map<Request, Double> sortedResult = sortByValue(UnsortedResult);
			      System.out.println(sortedResult.toString());
			      
			      for(Map.Entry<Request,Double> entry:sortedResult.entrySet())
			      {
			    	  ReqWithRank obj1 = new ReqWithRank(entry.getKey(),entry.getValue());
			    	  SortedLendRequestsforCat.add(obj1);
			      }
			      //System.out.println("The List is ==============   "+sortedRenteeAds.toString());
			    }
			    catch(Exception ex) {
			    	System.out.println(ex);
			      return null;
			    }
		
			    return SortedLendRequestsforCat;
			}
	
	//Requests from someone who wants to Lend me their items
	//working
	@RequestMapping(value="/getLendingRequests", method=RequestMethod.GET)
	@ResponseBody
	public List<Request> getLendingRequests(String receiverId)
	{			
		return (List<Request>)_reDao.getLendRequest(receiverId);
	}
	
	//Update if request Accepted by accepted/Declined
	//working
	@RequestMapping(value="/AcceptDeclineRequest", method=RequestMethod.GET)
	@ResponseBody
	public String AcceptDeclineRequests(int requestId, int response)
	{
		Request req = _reDao.getRequestById(requestId);
		User renter;
		User rentee;
		//response 0=declined, 2= accepted
		//AdType  1 = Lend Product, 2 = Borrow Product
		//Request Type: Who is sending the request - int 1: borrow 2:lend
		if(response == 0 )
		{
			System.out.println("inside response =0");
			req.setResponse(response);	
		}
		else
		{
			System.out.println("inside response =1");
			req.setResponse(response);
			
			//Get renter and rentee according to advertisement
			Advertisement ad = req.getAdvertisement();
			if(ad.getAdType() == 1)
			{
				renter = ad.getUser();
				if(req.getRequestType() == 1)
				{
					rentee = req.getSender();
				}
				else
				{
					rentee = req.getReceiver();
				}
				
			}
			else
			{
				rentee = ad.getUser(); 
				
				if(req.getRequestType() == 1)
				{
					renter = req.getReceiver();
				}
				else
				{
					renter = req.getSender();
				}
			}
			
			//set Advertisemnt active status as busy
			ad.setActive(2);
			_adDao.update(ad);
			
			//convert date format to desired format
			
				java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
				
				//parse the string to date using .parse(string) function and add it to transaction table.
				Transaction trx = new Transaction(ad, req, 0, 0,
						date,null,0,0,renter,rentee, 1);
				
				_trxDao.save(trx);
			//Add to trx i.e start trx
			
		}
		_reDao.update(req);
		return "Request updated successfully";
	}

	private static Map<Request, Double> sortByValue(Map<Request, Double> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Request, Double>> list =
                new LinkedList<Map.Entry<Request, Double>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Request, Double>>() {
            public int compare(Map.Entry<Request, Double> o1,
                               Map.Entry<Request, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Request, Double> sortedMap = new LinkedHashMap<Request, Double>();
        for (Map.Entry<Request, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
