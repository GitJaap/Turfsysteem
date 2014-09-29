import java.sql.SQLException;
import java.util.*;
import java.awt.*;

import data.*;


public class Run {
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static void main(String[] args)
	{
		//ProteusGui pe = new ProteusGui();
		//Interface_1 pe = new Interface_1();
		//ProteusGui2 pe = new ProteusGui2();
		/*long t = System.currentTimeMillis();
		Initializer init = new Initializer();
		init.reInitializeBar();
		Bar[] bars = init.getBars();
		ProductPriceClass ppc = init.getPPC();
		t = t-System.currentTimeMillis();
		System.out.println(t );*/
		DBConnection dB = new DBConnection();
		long t = System.currentTimeMillis();
		int i = 0;
		long h;
		while(i < 10){
			if(System.currentTimeMillis() - t > 500)
			{
				h = System.currentTimeMillis();
				dB.runQuery("Select admin_change_date from admin_changes order by admin_change_id limit 1");
				System.out.println(dB.getNextStr(1));
				System.out.println(System.currentTimeMillis()-h);
				//Kijk eens of dit werkt!
			}
		}
		
	
	}

	
	/*public static void main(String[] args)
	{
		/*Initializer turf = new Initializer();
		String[] productClass = turf.getProductClass();
		String[] productPriceClass = turf.getProductPriceClass();
		String[][] classProducts = turf.getClassProducts();
		for(String item: productClass)
		{
			System.out.println(item);
		}
		for(String item: productPriceClass)
		{
			System.out.println(item);
		}
		for(int j =0; j < classProducts.length;j++)
		for(int i = 0; i < classProducts[j].length; i++)
		{
			System.out.println(classProducts[j][i]);
		}
		
		ProteusGui GUI = new ProteusGui();
	}*/
}
