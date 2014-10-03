package gui;

import javax.swing.UIManager.*;
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
					init.getDB().runUpdate(String.format("INSERT INTO client_logs(client_id,client_log_type,log_date) VALUES (%d , false, NOW())",init.getCurClient().getID()));
					init.getDB().runUpdate(String.format("UPDATE clients SET client_is_active=false, last_client_update = NOW() WHERE client_id = %d", init.getCurClient().getID()));
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
			//set the look and feel to nimbus
			try {
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			} catch (Exception e) {
			    // If Nimbus is not available, you can set the GUI to another look and feel.
			}
			//set standard initialization variables
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
			this.add(contentPanel, "Center");
			this.setVisible(true);
			
		}
}
