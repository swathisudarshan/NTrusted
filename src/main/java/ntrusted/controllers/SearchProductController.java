package ntrusted.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ntrusted.models.Advertisement;
import ntrusted.models.AdvertisementDao;

@Controller
@RequestMapping(value="/searchRenters")
public class SearchProductController {
	@Autowired
	private AdvertisementDao _adDao;
	@Autowired
	private TrustCalcController TCC;
	
	@RequestMapping(value="/fetchRenters")
	  @ResponseBody
	  public String getRenterAds(int catId, String RenteeId) {
	    List<Advertisement> ads;
	    HashMap<Advertisement,Double> UnsortedResult = new HashMap<Advertisement,Double>();
	    
	    try {
	      ads = _adDao.getAds(catId);
	      
	 //     System.out.println("Inside Search Controller" + ads.toString());
	      //Get List of user Id's for each ad
	      List<String> userIds = new ArrayList<String>();
	      for(Advertisement ad: ads)
	      {
	    	  
	    	  String tempUserID = ad.getUser().getFbId();
	    //	  System.out.println("Inside Search Controller" + tempUserID);
	    //	  System.out.println("Returned rank is " + TCC.calcRenterRank(RenteeId,tempUserID));
	    	  UnsortedResult.put(ad,TCC.calcRenterRank(RenteeId,tempUserID));
	      }
	      Map<Advertisement, Double> sortedResult = sortByValue(UnsortedResult);
	      System.out.println(sortedResult.toString());
	      //HashMap<Customer,>TCC.getRecommendedRenter(userIds, RenteeId);
	 //     System.out.println("Inside Controller");
	   //   System.out.println(ads.toString());
	    }
	    catch(Exception ex) {
	    	System.out.println(ex);
	      return "Ad not found";
	    }
		return ads.toString();
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
