package data;

public class ProductClass {
	public int id;
	public String name;
	public Product[] products;
	public java.awt.Color color;
	
	public ProductClass(){}
	public ProductClass(int idIn, String nameIn, Product[] prIn, String colorIn)
	{
		id = idIn;
		name= nameIn;
		products=prIn;
		//create the color from the string 'red,gre,blu'
		int red = Integer.parseInt(colorIn.substring(0, 3));
		int green = Integer.parseInt(colorIn.substring(4,7));
		int blue = Integer.parseInt(colorIn.substring(8,11));
		color = new java.awt.Color(red,green,blue);
	}
	
	public ProductClass(int idIn, String nameIn, String colorIn)
	{
		id = idIn;
		name= nameIn;
		//create the color from the string 'red,gre,blu'
		int red = Integer.parseInt(colorIn.substring(0, 3));
		int green = Integer.parseInt(colorIn.substring(4,7));
		int blue = Integer.parseInt(colorIn.substring(8,11));
		color = new java.awt.Color(red,green,blue);
	}
	
	
	
}
