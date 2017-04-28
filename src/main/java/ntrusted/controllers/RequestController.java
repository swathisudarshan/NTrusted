package ntrusted.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import ntrusted.models.Advertisement;
import ntrusted.models.AdvertisementDao;
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
		public List<Request> getBorrowRequestsforCat(String receiverId,String catId)
		{ 
			return (List<Request>)_reDao.getBorrowRequestforCat(receiverId, Integer.valueOf(catId));
		}
		
		//Requests from someone who wants to lend me perticular category
		//working
			@RequestMapping(value="/getLendingRequestsforCat", method=RequestMethod.GET)
			@ResponseBody
			public List<Request> getLendingRequestsforCat(String receiverId,String catId)
			{ 
				return (List<Request>)_reDao.getLendRequestforCat(receiverId, Integer.valueOf(catId));
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
		
}
