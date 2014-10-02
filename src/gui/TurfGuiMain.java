package gui;

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
		private Initializer init = new Initializer();
		//for gui
		private WindowListener exitListener = new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//check if a client is logged in and log out accordingly
				if(init.getCurClient != null){
					init.getDB().runUpdate(String.format("INSERT INTO client_logs(client_id,client_log_type,log_date) VALUES (%d , false, NOW())",curClient.id));
					init.getDB().commit();
				}
				System.exit(0);
			}
		};
}
