package database;

import database.data.ProductPriceClass;

/** 
 * Handles the product transactions
 * @author Jaap
 *
 */
public abstract class Transaction {
	public static final int CASH = 1;
	public static final int PIN = 2;
	public static final int CARD = 3;

	/**
	 * Executes a product transaction 
	 * @param init
	 * @param orders
	 * @param transactionType
	 */
	public static String doProductTransaction(Initializer init, int[][] orders, int transactionType, int clientID,int adminID){

		switch(transactionType){
		case CASH:

		case PIN:

		case CARD:
			int accountID = CardReader.readCard();
			//now select the account and check its balance also locking the row so no one else can now read from it
			long t = System.currentTimeMillis();
			init.getDB().runQuery(String.format("SELECT balance FROM accounts WHERE account_id = %d FOR UPDATE",accountID ));
			int balance = init.getDB().getNextInt(1);
			//first calculate the total price of the transaction
			int totalPrice = 0;
			for(int i =0; i < init.getPPC().getProductClassesSize();i++)
			{
				for(int j =0; j < init.getPPC().getProductsSize(i);j++)
				{
					if(orders[i][j] > 0){
						totalPrice += orders[i][j] * init.getPPC().getProductPrice(i, j);
					}
				}
			}
			//now check if balance>totalprice and quit otherwise
			if(balance >= totalPrice)
			{
				//Check the last transaction_id and read for update so no one else can do a transaction in the meantime
				init.getDB().runQuery("SELECT MAX(transaction_credit_id) from transactions_credit FOR UPDATE");
				int transactionID = init.getDB().getNextInt(1)+1; //set the current id to one transaction higher
				//create the transaction one id higher 
				if(transactionID != -9998){//error check
					init.getDB().runUpdate(String.format("INSERT INTO transactions_credit VALUES (%d, %d, %d, %d, %d, NOW(), %d)",transactionID, accountID,adminID,clientID,transactionType, totalPrice));
					//again run through all bought products
					for(int i =0; i < init.getPPC().getProductClassesSize();i++)
					{
						for(int j =0; j < init.getPPC().getProductsSize(i);j++)
						{
							if(orders[i][j] > 0){
								init.getDB().runUpdate(String.format("INSERT INTO product_transactions"
										+ "(product_version_id,product_type_id,transaction_credit_id,product_amount)"
										+ " VALUES( %d,%d,%d,%d)", init.getPPC().getProductVersion(i, j),init.getPPC().getProductID(i, j),transactionID,orders[i][j] ));
							}
						}
					}
					//update the account balance
					init.getDB().runUpdate(String.format("UPDATE accounts SET balance=balance-%d WHERE account_id = %d",totalPrice,accountID));
					//finally commit the transaction to free the locked rows
					init.getDB().commit();
					System.out.println("Tijd nodig voor de transactie was: "+ Long.toString(System.currentTimeMillis()-t)+ " ms");
					return "Transactie Geslaagd!";
				}
				else{
					init.getDB().rollback();
					System.out.println("Tijd nodig voor de transactie was: "+ Long.toString(System.currentTimeMillis()-t)+ " ms");
					return "Er is een fout opgetreden met de index";
				}
			}
			else{
				init.getDB().rollback();
				System.out.println("Tijd nodig voor de transactie was: "+ Long.toString(System.currentTimeMillis()-t)+ " ms");
				return "Niet genoeg geld op de pas";
			
			}
		default:
			return "fout";
		}
	}
}
