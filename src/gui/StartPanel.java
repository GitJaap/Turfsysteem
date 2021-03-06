package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import database.Initializer;
import database.UpdateThread;
import database.data.Bar;
import database.data.Client;
import main.Run;

public class StartPanel extends JPanel implements ActionListener{
	
	//private variables
	private Initializer init;
	private CardLayout screenCards;
	private BarPanel barPanel;
	private JPanel contentPanel;
	
	private JPanel startPanelOutside = new JPanel();
	private JPanel startPanelInside = new JPanel();
	private JButton barButton = new JButton("BarGilde");
	private JButton beheerButton = new JButton("Beheer");
	private ArrayList<Bar> bars;
	private Bar curBar;
	private Client curClient;
	private JButton[] barButtons;
	private JButton[] clientButtons;
	private JLabel startLabel;
	private boolean needBarSelect = false;
	private boolean needClientSelect = false;
	private boolean beheer;
	private boolean bar;
	
	
	public StartPanel(Initializer initIn, CardLayout screenCardsIn, BarPanel barPanelIn, JPanel contentPanelIn){
		//set the initializervariable
		init=initIn;
		screenCards = screenCardsIn;
		barPanel = barPanelIn;
		contentPanel = contentPanelIn;
		
		startLabel = new JLabel("Kies Gebruiker");
		startLabel.setHorizontalAlignment(SwingConstants.CENTER);
		barButton.addActionListener(this);
		beheerButton.addActionListener(this);
		Dimension d = new Dimension(200,100);
		barButton.setPreferredSize(d);
		barButton.setSize(d);
		barButton.setMinimumSize(d);
		barButton.setMaximumSize(d);
		beheerButton.setSize(d);
		beheerButton.setPreferredSize(d);
		beheerButton.setMaximumSize(d);
		beheerButton.setMinimumSize(d);
		startLabel.setMinimumSize(d);
		startLabel.setMaximumSize(d);
		startPanelOutside.setLayout(new BoxLayout(startPanelOutside, BoxLayout.LINE_AXIS));
		startPanelInside.setLayout(new BoxLayout(startPanelInside, BoxLayout.PAGE_AXIS));
		startPanelInside.add(Box.createVerticalStrut((Run.screenSize.height-300)/2));
		startPanelInside.add(startLabel);
		startPanelInside.add(barButton);
		startPanelInside.add(beheerButton);
		startPanelOutside.add(Box.createHorizontalStrut((Run.screenSize.width-200)/2));
		startPanelOutside.add(startPanelInside);
		startPanelOutside.add(Box.createHorizontalStrut((Run.screenSize.width-200)/2));


		this.add(startPanelOutside);
	}

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
			for(int i = 0; i < bars.size();i++)
			{
				if(aE.getSource() == barButtons[i])
				{
					//check if there has not been a more recent login
					if(init.getVN().validateLastClientLog()){
						init.setCurBar(bars.get(i));
						curBar = init.getCurBar();
						needBarSelect = false;
						needClientSelect = true;
						//remove the barbuttons
						for(int k =0; k< bars.size();k++)
							startPanelInside.remove(barButtons[k]);
						//initiate the clientbutton array
						if(bars.get(i).getClientsSize() != 0){
							clientButtons = new JButton[bars.get(i).getClientsSize()];
							//list the visible bars as buttons to select
							for(int j =0;j <bars.get(i).getClientsSize();j++)
							{
								clientButtons[j] = new JButton(bars.get(i).getClientName(j));
								clientButtons[j].addActionListener(this);
								Dimension d = new Dimension(200,50);
								clientButtons[j].setMinimumSize(d);
								clientButtons[j].setMaximumSize(d);
								clientButtons[j].setSize(d);
								clientButtons[j].setPreferredSize(d);
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
			for(int i = 0; i < init.getCurBar().getClientsSize();i++)
			{
				if(aE.getSource() == clientButtons[i])
				{
					if(init.getVN().validateLastClientLog()){ //again check if someone has not logged in before us
						init.setCurClient(init.getCurBar().getClient(i));
						needClientSelect = false;
						//create a log so other pc's can't select the same client
						init.getDB().runUpdate(String.format("INSERT INTO client_logs(client_id,client_log_type,log_date) VALUES (%d , true, NOW())",init.getCurClient().getID()));
						//set the current client_is_active state to true
						init.getDB().runUpdate(String.format("UPDATE clients SET client_is_active=true, last_client_update = NOW() WHERE client_id = %d ",init.getCurClient().getID()));
						init.getDB().commit();
						//now remove everything and either initiate the bar or the beheerpage
						this.remove(startPanelInside);
						this.remove(startPanelOutside);
						this.validate();
						this.repaint();
						if(bar == true)
						{
							//initialize the main bar components
							barPanel.initBarComponents();
							//start the updatethread for the bar
							UpdateThread updater = new UpdateThread(init,barPanel);
							updater.start();
							
						}
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
	}
	
	private void createBarButtons()
	{
		//get the current visible bars from the database and the last client log id to verify them later
		init.getVN().validateLastClientLog();//set the last login-id at the last pk of the client_logs table
		init.reInitializeStart(); // re-read the available clients,bars from the database
		bars = init.getBars();			
		//remove any current buttons
		startPanelInside.removeAll();
		startPanelInside.setLayout(new BoxLayout(startPanelInside, BoxLayout.PAGE_AXIS));
		startPanelInside.add(Box.createVerticalStrut((Run.screenSize.height-300)/2));
		startPanelInside.add(startLabel);
		//initiate the barbutton array
		barButtons = new JButton[bars.size()];
		//list the visible bars as buttons to select
		for(int i =0;i <bars.size();i++)
		{
			barButtons[i] = new JButton(bars.get(i).getName());
			barButtons[i].addActionListener(this);
			Dimension d = new Dimension(200,50);
			barButtons[i].setMinimumSize(d);
			barButtons[i].setMaximumSize(d);
			barButtons[i].setSize(d);
			barButtons[i].setPreferredSize(d);
			startPanelInside.add(barButtons[i]);
		}
		startLabel.setText("Selecteer Bar");
		this.validate();
		this.repaint();
		needBarSelect = true;
		needClientSelect = false;
	}
}
