package data;


public class Bar {
	public int id;
	public String name;
	public Client[] clients;
	public boolean visible;
	public int cash;
	
	public Bar(int idIn, String nameIn, boolean visIn, int cashIn,Client[] clientsIn){
		id=idIn;
		name=nameIn;
		visible = visIn;
		cash=cashIn;
		clients = clientsIn;
		
	}

	public Bar(int idIn, String nameIn,int cashIn, boolean visIn){
		id=idIn;
		name=nameIn;
		cash=cashIn;
		visible = visIn;
		
	}
	
	
	public Bar()
	{}
}
