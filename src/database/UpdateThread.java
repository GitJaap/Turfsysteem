package database;

import gui.BarPanel;
import database.data.ProductPriceClass;
/**
 * Synchronises the bar with the database every 500ms
 * Uses a separate connection to the database for transaction integrity.
 * @author Jaap
 *
 */
public class UpdateThread extends Thread{
	private Initializer init;
	private DBConnection dBUpdate; // for separate transaction to be performed simultaniously
	private Validation vn;
	private ProductPriceClass ppc;
	private BarPanel barPanel;
	public UpdateThread(Initializer initIn, BarPanel barPanelIn){
		init = initIn;
		dBUpdate = new DBConnection();
		vn = new Validation(dBUpdate);
		barPanel = barPanelIn;
		}

	public void run()
	{
		while(true) {
			try{
				long t = System.currentTimeMillis();
				//check if products are still up to date and put the new ppc id in ppc otherwise
				if(!vn.validateProducts(init.getPPC()))
				{
					barPanel.initBarProducts();
					System.out.println("Er is een product geupdate");
				}
				//Set the last update date to now
				dBUpdate.runUpdate(String.format("UPDATE clients SET last_client_update = NOW() WHERE client_id = %d",init.getCurClient().getID()));
				dBUpdate.commit();
				System.out.println("Tijd nodig: " + Long.toString(System.currentTimeMillis()-t));
				Thread.sleep(500);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}

		}
	}
}
