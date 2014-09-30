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
	
	//check if two bars are equal by comparing their id's only(names should always be the same when ids match)
	public boolean equals(Bar barIn){
		if(id == barIn.id && clients.length == barIn.clients.length)
		{
			for(int i = 0; i < clients.length;i++)
			{
				if(!(clients[i].id == barIn.clients[i].id))
					return false;
			}
			return true;
		}
		else
			return false;
	}
}
