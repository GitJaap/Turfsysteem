package gui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import database.*;
import database.data.*;
import main.Run;

public class TurfGuiMain extends JFrame{
	//Global variables
		//create the database initialization and validation objects
		private Initializer init;
		//for gui
		private WindowListener exitListener = new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//check if a client is logged in and log out accordingly
				if(init.getCurClient() != null){
					init.getDB().runUpdate(String.format("INSERT INTO client_logs(client_id,client_log_type,log_date) VALUES (%d , false, NOW())",init.getCurClient().id));
					init.getDB().runUpdate(String.format("UPDATE clients SET client_is_active=false, last_client_update = NOW() WHERE client_id = %d", init.getCurClient().id));
					init.getDB().commit();
				}
				System.exit(0);
			}
		};
		
		//Gui variables
		private CardLayout screenCards;
		private JPanel contentPanel;
		private BarPanel barPanel;
		private StartPanel startPanel;
		
		
		
		public TurfGuiMain()
		{
			//set standard variables
			// set Standard initialization variables
			setTitle("Proteus Turfsysteem");
			this.setLocation(0,0);
			this.setSize(Run.screenSize);
			//create a close operation(this takes care of logout dB operations)
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.addWindowListener(exitListener);
			
			//initialize the variables
			init = new Initializer();
			screenCards = new CardLayout();
			contentPanel = new JPanel();
			barPanel = new BarPanel(init,screenCards,contentPanel);
			startPanel = new StartPanel(init,screenCards,barPanel,contentPanel);
			
			//start with the startPanel
			contentPanel.setLayout(screenCards);
			contentPanel.add(startPanel, "START");
			contentPanel.add(barPanel, "BAR");
			screenCards.show(contentPanel, "START");
			
			
			
			
		}
}
