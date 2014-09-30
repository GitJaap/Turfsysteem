import java.util.*;
import java.text.*;

import data.*;
//This class reads the database to initialize the GUI variables
public class Initializer {

	//variables
	private DBConnection dB = new DBConnection(); //initialize database connection	

	//startup variables
	private Bar[] bars;
	//bar variables
	private ProductPriceClass ppc;


	//create a format for displaying cash amount
	private DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
	private DecimalFormat df;

	public Initializer()
	{
		reInitializeStart();
		// create the right format for displaying the cash amounts
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('.'); 
		df = new DecimalFormat("\u20ac0.00", otherSymbols);
	}
	public void reInitializeStart(){
		//retrieve only the visible bars from the database
		dB.runQuery("Select bar_id, bar_name, current_bar_cash from bars where bar_visibility = true");
		dB.commit();
		bars= new Bar[dB.getNumRows()];
		dB.gofirst();
		int i = 0;
		do
		{
			bars[i] = new Bar(dB.getInt(1),dB.getStr(2),dB.getInt(3),true);
			i++;
		}while(dB.next());

		//retrieve visible client Pc's from the database and store them with their respective bars
		for(int j =0; j < bars.length; j++)
		{
			Client [] clients;
			dB.runQuery(String.format("Select client_id,client_name FROM clients WHERE bar_id = %d AND client_visibility = true",bars[j].id));;
			dB.commit();
			int nClients = dB.getNumRows();
			if(nClients > 0){
				clients = new Client[nClients];
				dB.gofirst();
				i=0;
				do{
					clients[i] = new Client(dB.getInt(1),dB.getStr(2),true);
					i++;
				}while(dB.next());
				//Now check if those clients are already logged in or if they have not send a log action in the last half hour
				boolean[] clientsValid = new boolean[nClients];
				int nClientsActual = 0;
				for(i = 0; i< nClients;i++)
				{
					dB.runQuery(String.format("SELECT client_log_type,MINUTE(TIMEDIFF(NOW(),log_date)) from client_logs WHERE client_id = %d ORDER BY client_log_id DESC LIMIT 1;",clients[i].id));
					dB.commit();
					if(dB.next()){
						if(dB.getBool(1) && dB.getInt(2)<31)
						{
							clientsValid[i] = false;
							System.out.println(String.format("Client_id %d is false",clients[i].id));
							System.out.println(dB.getInt(2));
						}
						else
						{
							clientsValid[i] = true;
							nClientsActual++;
						}
					}
					else{
						clientsValid[i] = true;
						nClientsActual++;
					}
				}
				//fill the bars with the available clients
				if(nClientsActual > 0){
					bars[j].clients = new Client[nClientsActual];
					int k = 0;
					for(i=0; i < nClients;i++)
					{
						if(clientsValid[i]){
							bars[j].clients[k] = clients[i];
							k++;
						}
					}
				}
			}
			else{
				bars[j].clients = null;
			}
		}
	}
	public void reInitializeBar(){
		//Get the currently active product price class
		dB.runQuery("Select product_price_class_id, class_name from product_price_class where activated = true LIMIT 1");
		dB.commit();
		ppc = new ProductPriceClass(dB.getNextInt(1),dB.getStr(2));

		//now insert for this price_class the product_classes like eten drinken shift
		dB.runQuery("Select * from product_class");
		dB.commit();
		ppc.productClasses = new ProductClass[dB.getNumRows()];
		dB.gofirst();
		int i = 0;
		do
		{
			ppc.productClasses[i] = new ProductClass(dB.getInt(1),dB.getStr(2),dB.getBool(3),dB.getStr(4));
			i++;
		}while(dB.next());

		//insert for each class and price class the relevant products into ppc
		for(int j=0; j < ppc.productClasses.length;j++)
		{
			dB.runQuery(String.format("Select product_version_id, product_type_id,product_price,product_name,product_visibility FROM product_types WHERE product_class_id = %d AND product_price_class_id = %d",ppc.productClasses[j].id,ppc.id));
			dB.commit();
			int nProducts = dB.getNumRows();
			if(nProducts > 0){
				ppc.productClasses[j].products = new Product[nProducts];
				dB.gofirst();
				i=0;
				do{
					ppc.productClasses[j].products[i] = new Product(dB.getInt(1),dB.getInt(2),dB.getInt(3),dB.getStr(4),dB.getBool(5));
					i++;
				}while(dB.next());
			}
			else
				ppc.productClasses[j] = null;
		}
	}

	//returns an array with all visible bars
	public Bar[] getBars(){
		return bars;
	}
	//return the current active productpriceClass
	public ProductPriceClass getPPC(){
		return ppc;
	}
	//returns a decimalformat used to format product prices
	public DecimalFormat getdf()
	{return df;}
	//returns the DBConnection. Used for updates.
	public DBConnection getDB()
	{
		return dB;
	}

}
