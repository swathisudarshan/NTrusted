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
import ntrusted.mongoModel.Connection;
import ntrusted.mongoModel.Customer;

@Controller
@RequestMapping(value="/transaction")
public class TransactionController {

	@Autowired
	  private RequestDao _reDao;
	@Autowired
	 private UserDao _userDao;
	@Autowired
	private AdvertisementDao _adDao;
	@Autowired
	private TransactionDao _trxDao;
	
	@Autowired
	  private CustomerRepository cuRepo;

	//Get all Active Transactions
	@RequestMapping(value="/getAllRenterTrx", method=RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getRenterTrx(String userId) 
	{
		User user = _userDao.getById(userId);
		return (List<Transaction>) _trxDao.getRenterActiveTransaction(user);	
	}
	
	@RequestMapping(value="/getAllRenteeTrx", method=RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getRenteeTrx(String userId) 
	{
		User user = _userDao.getById(userId);
		return (List<Transaction>) _trxDao.getRenteeActiveTransaction(user);
	}
	
	
	//Get Close  Requested transaction
	@RequestMapping(value="/getRenterCloseReqTrx", method=RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getRenterCloseReqTrx(String userId) 
	{
		User user = _userDao.getById(userId);
		return (List<Transaction>) _trxDao.getRenterCloseReqTran(user);	
	}
	
	@RequestMapping(value="/getRenteeCloseReqTrx", method=RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getRenteeCloseReqTrx(String userId) 
	{
		User user = _userDao.getById(userId);
		return (List<Transaction>) _trxDao.getRenteeCloseReqTran(user);
	}
	
	
	//Get All Closed Transactions
	@RequestMapping(value="/getRenterClosedTrx", method=RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getRenterClosedTrx(String userId) 
	{
		User user = _userDao.getById(userId);
		return (List<Transaction>) _trxDao.getRenterClosedTran(user);	
	}
	
	@RequestMapping(value="/getRenteeClosedTrx", method=RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getRenteeClosedTrx(String userId) 
	{
		User user = _userDao.getById(userId);
		return (List<Transaction>) _trxDao.getRenteeClosedTran(user);
	}
	
	//Close Requested
	@RequestMapping(value="/CloseTrxUpdate", method=RequestMethod.GET)
	@ResponseBody
	public Transaction CloseTrxUpdate(int trxId, String userId , float rating) 
	{
		User user = _userDao.getById(userId);
		
		Customer renter;
		Customer rentee;
		Transaction trx = _trxDao.getTrxById(trxId);
		
		Advertisement ad = trx.getAd();
		if(trx.getRenter().getFbId().equals(userId))
		{
			trx.setRenterClose(1);
			trx.setRenteeRating(rating);
		}
		else
		{
			trx.setRenteeClose(1);
			trx.setRenterRating(rating);
		}
		
		if(trx.getRenteeClose() == 1 && trx.getRenterClose() == 1)
		{
			trx.setStatus(0);
			ad.setActive(1);
			//Check if renter and rentee are friends if not add connections to both mongoModels
			renter =  cuRepo.findByUserId(trx.getRenter().getFbId());
			rentee = cuRepo.findByUserId(trx.getRentee().getFbId());
			UpdateRatings(renter, rentee,trx.getRenterRating(),trx.getRenteeRating());	
		}
		else
		{
			trx.setStatus(2);
		}
		
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		trx.setEndDate(date);
		_trxDao.update(trx);
		return trx;
	}
	
	public void UpdateRatings(Customer renter, Customer rentee, float renterRating, float renteeRating)
	{
		List<Connection> list = renter.getConnections();
		for(Connection con : list)
		{
			if(con.getUserid().equals(rentee.getUserId()))
			{
				//if they are frnds
				
				//Update for renter
				int temp = con.getRenterNoTrx();
				con.setRenterNoTrx(temp+1);
				con.setRenterTrust((( temp * con.getRenterTrust())+renterRating)/(temp+1));
				cuRepo.save(renter);
				
				Connection renteeCon = rentee.getCon(renter.getUserId());
				int temp2 = renteeCon.getRenteeNoTrx();
				renteeCon.setRenteeNoTrx(temp2+1);
				renteeCon.setRenteTrust((( temp2 * renteeCon.getRenteTrust())+renteeRating)/(temp2+1));
				cuRepo.save(rentee);
				
				return;
			}
		}
		
		
		//Add Connecitons if both users are not friends
		
		//Add to rentee mongo
		Connection con =  new Connection();
		con.setUserid(renter.getUserId());
		//Add rating for rentee considering himself as rentee with respect to renter
		con.setRenteeNoTrx(1);
		con.setRenteTrust(renteeRating);
		con.setRenterNoTrx(0);
		con.setRenterTrust(0);
		con.setActiveFriend(0);
		rentee.addConnection(con);
		cuRepo.save(rentee);
		
		//Add to Renter mongo
		Connection con1 =  new Connection();
		con1.setUserid(rentee.getUserId());
		//Add rating for renter considering himself as renter with respect to rentee
		con1.setRenterNoTrx(1);
		con1.setRenterTrust(renterRating);
		con1.setRenteeNoTrx(0);
		con1.setRenteTrust(0);
		con1.setActiveFriend(0);
		renter.addConnection(con1);
		cuRepo.save(renter);		
	}
}
