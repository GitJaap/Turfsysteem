package data;
import java.util.*;

public class Product {
	public int id;
	public int price;
	public int version;
	public String name;
	public boolean visible;
	
	public Product(){}
	public Product(int verIn, int idIn, int priceIn, String nameIn,boolean visIn)
	{
		id = idIn;
		price = priceIn;
		name = nameIn;
		visible = visIn;
		version = verIn;
	}
}
