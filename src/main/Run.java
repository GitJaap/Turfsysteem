package main;
import gui.ProteusGui2;

import java.sql.SQLException;
import java.util.*;
import java.awt.*;

import database.*;


public class Run {
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static void main(String[] args)
	{
		//ProteusGui2 pe = new ProteusGui2();
		Initializer init = new Initializer();
		if(init.getCurClient() == null)
			{
			System.out.println("het is null");
			};
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
