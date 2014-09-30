//This class takes care of all local data-validation by checking if there has not been an update to certain data since last check
public class Validation {
	private DBConnection dB;
	private int lastClientLogID;
	
	public Validation(DBConnection dBIn)
	{
		dB = dBIn;
		lastClientLogID = 0;
	}
	
	//check for more recent logs than currently known and report if false.
	public boolean validateClients()
	{
		dB.runQuery("SELECT client_log_id from client_logs ORDER BY client_log_id DESC LIMIT 1");
		dB.commit();
		int newID = dB.getNextInt(1);
		if(newID == lastClientLogID)
			return true;
		else
		{
			lastClientLogID = newID;
			return false;
		}
	}
}
