//This class takes care of all local data-validation by checking if there has not been an update to certain data since last check
import data.*;
public class Validation {
	private DBConnection dB;
	private int lastClientLogID;
	private int lastAdminChangeID;
	
	public Validation(DBConnection dBIn)
	{
		dB = dBIn;
		lastClientLogID = 0;
		lastAdminChangeID = 0;
	}
	
	//check for more recent logs than currently known and report if false.
	public boolean validateClients()
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
