package database;
//This class takes care of all local data-validation by checking if there has not been an update to certain data since last check
import database.*;
import database.data.ProductPriceClass;

import java.util.*;
public class Validation {
	private final int CLIENT_UPDATE_INTERVAL_SEC = 60;
	
	private DBConnection dB;
	private int lastClientLogID;
	private int lastAdminChangeID;
	
	public Validation(DBConnection dBIn)
	{
		dB = dBIn;
		lastClientLogID = 0;
		lastAdminChangeID = 0;
	}
	
	//check for clients that have not send an update for more then CLIENT_UPDATE_INTERVAL seconds
	public boolean validateClients()
	{
		boolean ret = true;
		String clientIDs = "";
		dB.runQuery("SELECT client_id,TIME_TO_SEC(TIMEDIFF(NOW(),last_client_update)) FROM clients WHERE client_is_active = true");
		dB.commit();
		while(dB.next())
		{
			if(dB.getInt(2) > CLIENT_UPDATE_INTERVAL_SEC)
			{
				ret = false;
				clientIDs += "OR client_id = " +Integer.toString(dB.getInt(1)) + " ";
			}
		}
		//now change the inactive client_is_active states to false
		dB.runUpdate("UPDATE clients SET client_is_active = false WHERE 1=2 "+ clientIDs);
		return ret;
		
	}
	
	//check for more recent logs than currently known and report if false.
	public boolean validateLastClientLog()
	{
		dB.runQuery("SELECT client_log_id from client_logs ORDER BY client_log_id DESC LIMIT 1");
		dB.commit();
		int newID = dB.getNextInt(1);
		if(newID == lastClientLogID)
			return true;
		else if(newID == -9999)
			return true;
		else
		{
			lastClientLogID = newID;
			return false;
		}
	}
	//check for recent changes in admin settings(for example product modification) and if so change price class
	public boolean validateProducts(ProductPriceClass ppcIn)
	{
		dB.runQuery("SELECT admin_change_id,current_product_price_class_id FROM admin_changes ORDER BY admin_change_id DESC LIMIT 1");
		dB.commit();
		int newID = dB.getNextInt(1);
		if(lastAdminChangeID == newID){
			ppcIn.id = dB.getInt(2);
			return true;
		}
		else if(newID == -9999)
		{
			ppcIn.id = 1; // if no admin change has been admitted set ppc.id to the first class
			return true;
		}
		else{
			ppcIn.id = dB.getInt(2);
			lastAdminChangeID = newID;
			return false;
		}
	}
}
