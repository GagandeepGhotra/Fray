package Libraries.Connector;
import java.net.*;
import java.io.*;

public class ConnectToPHP {
	
	public ConnectToPHP() {
		
	}
	
	public static void main(String[] args) {
	
	}
	
	public boolean loginMethod(String username, String password) {//https://stackoverflow.com/questions/4356289/php-random-string-generator
		boolean loggedInValBoolean = false;
			   try {//assn8_invoice.php?productQuantityTeddy=1&productCostTeddy=25.00&productQuantityScooter=1&productCostScooter=47.00&productQuantityFrisby=1&productCostFrisby=15.50
			   //http://mehtapun.dev.fast.sheridanc.on.ca/a8-puneet-mehta/addProduct1.php?CarMake=Volkswagen&CarModel=GolfSportWagen&Year=2019
	            URL firstTimeConnectionStartup = new URL("http://ghotraga.dev.fast.sheridanc.on.ca/Projects/FrayCardGame/Login.php?Username="+username+"&Password="+password+"");
	            URLConnection firstTimeConnectionStartupCon = firstTimeConnectionStartup.openConnection();
	            //DataInputStream dis = new DataInputStream(firstTimeConnectionStartupCon.getInputStream());
	            BufferedReader readVals = new BufferedReader(new InputStreamReader(firstTimeConnectionStartupCon.getInputStream()));
	            //String inputLine;

	          //  while ((inputLine = dis.readLine()) != null) {
	            //    System.out.println(inputLine);
	           // }
	           // dis.close();
	            
	            readVals.close();
	        } catch (MalformedURLException me) {
	            System.out.println("MalformedURLException: " + me);
	        } catch (IOException ioe) {
	            System.out.println("IOException: " + ioe);
	        }
		return loggedInValBoolean;
	}
}
