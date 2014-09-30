package data;

public class ProductPriceClass {

	public int id;
	public ProductClass[] productClasses;
	public String name;
	
	public ProductPriceClass(int idIn,ProductClass[] pcIn,String nameIn)
	{
		id=idIn;
		productClasses = pcIn;
		name=nameIn;
	}
	public ProductPriceClass(int idIn,String nameIn){
		id = idIn;
		name= String.valueOf(nameIn);
	}
	public ProductPriceClass()
	{
		
	}
}
