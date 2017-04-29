package ntrusted.controllers;

import java.util.*;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ntrusted.models.AdWithRank;
import ntrusted.models.Advertisement;
import ntrusted.models.AdvertisementDao;
// Add All ads without cat for both lending and borrowing




@Controller
@RequestMapping(value="/search")
public class SearchProductController {
	@Autowired
	private AdvertisementDao _adDao;
	@Autowired
	private TrustCalcController TCC;
	
	public List<Advertisement> sortedRenterAds = new ArrayList<Advertisement>();
	public List<Advertisement> sortedRenteeAds = new ArrayList<Advertisement>();
	
	public List<AdWithRank> sortedRenterAds1 = new ArrayList<AdWithRank>();
	public List<AdWithRank> sortedRenteeAds1 = new ArrayList<AdWithRank>();
	
	@RequestMapping(value="/fetchRenters")
	  @ResponseBody
	  public List<AdWithRank> getRenterAds(int catId, String RenteeId) {
	    List<Advertisement> ads;
	    sortedRenterAds.clear();
	    sortedRenteeAds.clear();
	    HashMap<Advertisement,Double> UnsortedResult = new HashMap<Advertisement,Double>();
	    
	    try {
	      ads = (List<Advertisement>) _adDao.getLendingAds(catId);
	      for(Advertisement ad: ads)
	      {
	    	  //System.out.println("Inside search controller: "+ ad.getProductName());
	    	  String tempUserID = ad.getUser().getFbId();
	    	  UnsortedResult.put(ad,TCC.calcRenterRank(RenteeId,tempUserID));
	      }
	      System.out.println("Inside search controller: "+ ads.size());
	    
	      //Get List of user Id's for each ad
	      List<String> userIds = new ArrayList<String>();
	      for(Advertisement ad: ads)
	      {
	    	  
	    	  String tempUserID = ad.getUser().getFbId();
	    	  UnsortedResult.put(ad,TCC.calcRenterRank(RenteeId,tempUserID));
	      }
	      Map<Advertisement, Double> sortedResult = sortByValue(UnsortedResult);
	      System.out.println(sortedResult.toString());
	      
	      for(Map.Entry<Advertisement,Double> entry:sortedResult.entrySet())
	      {
	    	  AdWithRank obj1 = new AdWithRank(entry.getKey(),entry.getValue());
	    	  sortedRenterAds1.add(obj1);
	    	  sortedRenterAds.add(entry.getKey());
	      }
	    }
	    catch(Exception ex) {
	    	System.out.println(ex);
	      return null;
	    }
		return sortedRenterAds1;
	  }
	
	@RequestMapping(value="/fetchRentees")
	  @ResponseBody
	  public List<AdWithRank> getRenteeAds(int catId, String RenterId) {
	    List<Advertisement> ads;
	    
	    HashMap<Advertisement,Double> UnsortedResult = new HashMap<Advertisement,Double>();
	    sortedRenterAds.clear();
	    sortedRenteeAds.clear();
	    
//	    JSONObject mainObj = new JSONObject();
//	    JSONArray array = new JSONArray();
	    try{
	      ads = _adDao.getBorrowingAds(catId);
	      System.out.println("getBorrowing ads: "+ads.toString());
	      //Get List of user Id's for each ad
	      List<String> userIds = new ArrayList<String>();
	      for(Advertisement ad: ads)
	      {
	    	  System.out.println("Infor getBorrowing ads: "+ad.toString());
	    	  String tempUserID = ad.getUser().getFbId();
	    	  UnsortedResult.put(ad,TCC.calcRenteeRank(RenterId,tempUserID));
	      }

	      Map<Advertisement, Double> sortedResult = sortByValue(UnsortedResult);
	      System.out.println(sortedResult.toString());
	      
	      for(Map.Entry<Advertisement,Double> entry:sortedResult.entrySet())
	      {
	    	  AdWithRank obj1 = new AdWithRank(entry.getKey(),entry.getValue());
	    	  sortedRenteeAds1.add(obj1);
	    	  sortedRenteeAds.add(entry.getKey());
	      }
	      //System.out.println("The List is ==============   "+sortedRenteeAds.toString());
	    }
	    catch(Exception ex) {
	    	System.out.println(ex);
	      return null;
	    }
	    
	    
		return sortedRenteeAds1;
	  }
	
	private static Map<Advertisement, Double> sortByValue(Map<Advertisement, Double> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Advertisement, Double>> list =
                new LinkedList<Map.Entry<Advertisement, Double>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Advertisement, Double>>() {
            public int compare(Map.Entry<Advertisement, Double> o1,
                               Map.Entry<Advertisement, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Advertisement, Double> sortedMap = new LinkedHashMap<Advertisement, Double>();
        for (Map.Entry<Advertisement, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }
	
}
