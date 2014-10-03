package gui;

import javax.swing.*;

import database.*;
import database.data.*;
import main.Run;

import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ProteusGui2 extends JFrame implements ActionListener, MouseListener {

	//Global variables
	//create the database initialization and validation objects
	private Initializer init = new Initializer();
	//for gui
	private WindowListener exitListener = new WindowAdapter(){
		public void windowClosing(WindowEvent e){
			//check if a client is logged in and log out accordingly
			if(curClient != null){
				init.getDB().runUpdate(String.format("INSERT INTO client_logs(client_id,client_log_type,log_date) VALUES (%d , false, NOW())",curClient.id));
				init.getDB().commit();
			}
			System.exit(0);
		}
	};

	private JPanel startPanelOutside = new JPanel();
	private JPanel startPanelInside = new JPanel();
	private JButton barButton = new JButton("BarGilde");
	private JButton beheerButton = new JButton("Beheer");
	private Bar[] bars;
	private Bar curBar;
	private Client curClient;
	private JButton[] barButtons;
	private JButton[] clientButtons;
	private JLabel startLabel;
	private boolean needBarSelect = false;
	private boolean needClientSelect = false;
	private boolean beheer;
	private boolean bar;

	//Variables used to check if all local data is still up to date
	// VARIABLES for the BAR screen
	//----------------------------------------------------------
	//main data attributes
	private ProductPriceClass ppc;
	//declare panels used
	private JPanel ProductPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel orderPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel sidePanel = new JPanel();
	//Declare GUI attributes
	private JTextArea testArea;
	private JButton[][] productButtons;
	private JButton orderButton;
	private int[][] orders;
	private JButton[][] orderedButtons;
	//-----------------------------------------------------
	//ENd of variables for BAR screen

	public ProteusGui2()
	{
		// set Standard initialization variables
		setTitle("Proteus Turfsysteem");
		this.setLocation(0,0);
		this.setSize(Run.screenSize);
		//create a close operation(this takes care of logout dB operations)
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(exitListener);
		//create the startup panel by adding a bar and beheer button
		startLabel = new JLabel("Kies Gebruiker");
		startLabel.setHorizontalAlignment(SwingConstants.CENTER);
		barButton.addActionListener(this);
		beheerButton.addActionListener(this);
		Dimension d = new Dimension(200,100);
		barButton.setMinimumSize(d);
		barButton.setMaximumSize(d);
		beheerButton.setMaximumSize(d);
		beheerButton.setMinimumSize(d);
		startLabel.setMinimumSize(d);
		startLabel.setMaximumSize(d);
		startPanelOutside.setLayout(new BoxLayout(startPanelOutside, BoxLayout.LINE_AXIS));
		startPanelInside.setLayout(new BoxLayout(startPanelInside, BoxLayout.PAGE_AXIS));
		startPanelInside.add(startLabel);
		startPanelInside.add(barButton);
		startPanelInside.add(beheerButton);
		startPanelOutside.add(Box.createHorizontalStrut((Run.screenSize.width-200)/2));
		startPanelOutside.add(startPanelInside);
		startPanelOutside.add(Box.createHorizontalStrut((Run.screenSize.width-200)/2));


		this.add(startPanelOutside);
		//start the main screen
		this.setVisible(true);

		//initBarComponents();
	}
	public void initBarComponents()
	{
		//read data from database
		init.reInitializeBar(curBar.id);
		ppc = init.getPPC();
		//set the screentitle
		setTitle("Proteus Bargilde");
		this.setLayout(new BorderLayout());

		//initialize the panels


		//Declare GUI attributes
		productButtons =  new JButton[ppc.productClasses.length][];
		orders = new int[ppc.productClasses.length][];
		orderedButtons = new JButton[ppc.productClasses.length][];

		//loop through all available product classes and create buttons accordingly
		for(int i = 0; i < ppc.productClasses.length;i++){
			if(ppc.productClasses[i].products != null){//check if there are products in the current class
				System.out.println(ppc.productClasses[i].products.length);
				int nProducts = ppc.productClasses[i].products.length;
				//initiate the orderbuttons and orderarrays
				orders[i] = new int[nProducts];
				orderedButtons[i] = new JButton[nProducts];
				//create productbuttons
				productButtons[i] = new JButton[nProducts];
				for(int j = 0; j < nProducts;j++){
					productButtons[i][j] = new JButton();
					productButtons[i][j].setText(ppc.productClasses[i].products[j].name +" "+ init.getdf().format((double)ppc.productClasses[i].products[j].price/100));
					productButtons[i][j].addActionListener(this);
					productButtons[i][j].addMouseListener(this);
					productButtons[i][j].setBackground(ppc.productClasses[i].color);
					productButtons[i][j].setPreferredSize(new Dimension(150,80));
					ProductPanel.add(productButtons[i][j]);
				}
			}
			else{
				productButtons[i] = null;
			}
		}
		
		topPanel.add(new JLabel("PROTEUS ERETES BARGILDE"));
		ProductPanel.setLayout(new GridLayout(5,3));
		
		this.add(topPanel,"North");
		this.add(ProductPanel,"Center");
		this.add(bottomPanel,"South");
		this.add(sidePanel,"East");
		this.repaint();
		this.validate();;
	}

	//main eventhandler for buttons being pressed
	public void actionPerformed(ActionEvent aE) {

		//eventhandlers for the START panel
		//------------------------------------------------------------------------------------------------------------------------------------

		if(aE.getSource() == barButton)
		{
			bar=true;
			// create the bar buttons to select from
			createBarButtons();
		}
		if(aE.getSource() == beheerButton)
		{
			beheer=true;
			//create the bar buttons to select from
			createBarButtons();
			beheer=true;
		}
		//check if the bar-selection buttons are being showed on the screen
		if(needBarSelect == true)
		{
			for(int i = 0; i < bars.length;i++)
			{
				if(aE.getSource() == barButtons[i])
				{
					//check if there has not been a more recent login
					if(init.getVN().validateLastClientLog()){
						curBar = bars[i];
						needBarSelect = false;
						needClientSelect = true;
						//remove the barbuttons
						for(int k =0; k< bars.length;k++)
							startPanelInside.remove(barButtons[k]);
						//initiate the clientbutton array
						if(!(bars[i].clients == null)){
							clientButtons = new JButton[bars[i].clients.length];
							//list the visible bars as buttons to select
							for(int j =0;j <bars[i].clients.length;j++)
							{
								clientButtons[j] = new JButton(bars[i].clients[j].name);
								clientButtons[j].addActionListener(this);
								Dimension d = new Dimension(200,50);
								clientButtons[j].setMinimumSize(d);
								clientButtons[j].setMaximumSize(d);
								startPanelInside.add(clientButtons[j]);
							}
						}
						startLabel.setText("Selecteer PC");
						this.validate();
						this.repaint();				
					}
					else //someone else has logged in before us so we need to reinitialize
					{
						JOptionPane.showMessageDialog(startPanelInside,"Een andere PC heeft al eerder een aanpassing gedaan. Selecteer Alstublieft opnieuw een bar","Synchronisatie Error",JOptionPane.ERROR_MESSAGE);
						createBarButtons();
					}
				}
			}
		}
		//check if the clientselectionbuttons are being showed on the screen
		if(needClientSelect == true)
		{
			for(int i = 0; i < curBar.clients.length;i++)
			{
				if(aE.getSource() == clientButtons[i])
				{
					if(init.getVN().validateLastClientLog()){ //again check if someone has not logged in before us
						curClient = curBar.clients[i];
						needClientSelect = false;
						//create a log so other pc's can't select the same client
						init.getDB().runUpdate(String.format("INSERT INTO client_logs(client_id,client_log_type,log_date) VALUES (%d , true, NOW())",curClient.id));
						init.getDB().commit();
						//set the current client_is_active state to true
						init.getDB().runUpdate(String.format("UPDATE clients SET client_is_active=true, last_client_update = NOW() WHERE client_id = %d ",curClient.id));
						init.getDB().commit();
						//now remove everything and either initiate the bar or the beheerpage
						this.remove(startPanelInside);
						this.remove(startPanelOutside);
						this.validate();
						this.repaint();
						if(bar == true)
							initBarComponents();
						if(beheer == true)
						{
							//TODO  stuff for the beheerscreen
						}
					}
					else{ //someone has logged in before us so we need to reinitialize
						JOptionPane.showMessageDialog(this,"Een andere PC heeft al eerder een aanpassing gedaan. Selecteer Alstublieft opnieuw een bar","Synchronisatie Error",JOptionPane.ERROR_MESSAGE);
						createBarButtons();

					}
				}
			}
		}
		//---------------------------------------------------------------------------------------------------------------------
		//END eventhandlers for the start panel


		//eventhandlers for the BAR panel
		//--------------------------------------------------------------------------------------------------------------------------------------
		/*
		for(int i = 0; i < productClass.length;i++){

			//create eventhandlers for product buttons that are pressed
			for(int j=0; j < classProducts[i].length;j++)
			{
				if(aE.getSource() == productButtons[i][j])
				{

					testArea.setText("U heeft een " + classProducts[i][j] + " besteld!");
					if(orders[i][j] == 0)
					{
						orderedButtons[i][j] = new JButton();
						orderedButtons[i][j].setPreferredSize(new Dimension(200,50));
						orderedButtons[i][j].addActionListener(this);
						orderedButtons[i][j].addMouseListener(this);
						orderPanel.add(orderedButtons[i][j]);
					}							
					orders[i][j]++;
					orderedButtons[i][j].setText(orders[i][j] + " x " + classProducts[i][j]);

				}
				if(aE.getSource() == orderedButtons[i][j])
				{
					orderPanel.remove(orderedButtons[i][j]);
					this.repaint();
					orders[i][j] = 0;
				}
			}
		}

		//create the event handler for the "orderButton" or Afrekenknop
		if(aE.getSource() == orderButton)
		{
			testArea.setText("U heeft de volgende producten besteld: \n");
			int totalPrice = 0;
			for(int i =0; i < productClass.length;i++)
			{
				for(int j =0; j < classProducts[i].length;j++)
				{
					if(orders[i][j] > 0){
						totalPrice += orders[i][j] * classProductsPrice[i][j];
						orderPanel.remove(orderedButtons[i][j]);
						orderPanel.validate();
						repaint();
						testArea.append(orders[i][j] + " x " + classProducts[i][j] +" voor "+ df.format((double)classProductsPrice[i][j]*orders[i][j]/100) + "\n");
						orders[i][j] = 0;
					}
				}
			}
			testArea.append("Voor een totaal van: " + df.format((double)totalPrice/100) + "!");
			this.repaint();
		}
		//----------------------------------------------------------------------------------------------------------------------------------------------------------
		//end of eventhandlers for the BAR panel

	}

	public void mouseClicked(MouseEvent e) {
		for(int i =0; i < productClass.length;i++)
		{
			for(int j =0; j < classProducts[i].length;j++)
			{
				if(e.getButton() == MouseEvent.BUTTON3){
					if( e.getComponent() == productButtons[i][j] || e.getComponent() == orderedButtons[i][j])
					{
						if(orders[i][j] >1){
							orders[i][j]--;
							orderedButtons[i][j].setText(orders[i][j] + " x " + classProducts[i][j]);
						}
						else if(orders[i][j] == 1){
							orderPanel.remove(orderedButtons[i][j]);
							orders[i][j]--;
							orderPanel.validate();
							repaint();
						}
					}
				}	
			}

		}
		 */
	}

	//lets the user select a client
	private void createBarButtons()
	{
		//get the current visible bars from the database and the last client log id to verify them later
		init.getVN().validateLastClientLog();//set the last login-id at the last pk of the client_logs table
		init.reInitializeStart(); // re-read the available clients,bars from the database
		bars = init.getBars();			
		//remove any current buttons
		startPanelInside.removeAll();
		startPanelInside.setLayout(new BoxLayout(startPanelInside, BoxLayout.PAGE_AXIS));
		startPanelInside.add(startLabel);
		//initiate the barbutton array
		barButtons = new JButton[bars.length];
		//list the visible bars as buttons to select
		for(int i =0;i <bars.length;i++)
		{
			barButtons[i] = new JButton(bars[i].name);
			barButtons[i].addActionListener(this);
			Dimension d = new Dimension(200,50);
			barButtons[i].setMinimumSize(d);
			barButtons[i].setMaximumSize(d);
			startPanelInside.add(barButtons[i]);
		}
		startLabel.setText("Selecteer Bar");
		this.validate();
		this.repaint();
		needBarSelect = true;
		needClientSelect = false;
	}

	//compares the known last log id to the current log id allowing the program to see if the client list is still up to date
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

