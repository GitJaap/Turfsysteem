import javax.swing.*;

import data.*;

import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ProteusGui2 extends JFrame implements ActionListener, MouseListener {

	//Global variables

	private JPanel startPanel = new JPanel();
	private JOptionPane barSelectPane = new JOptionPane();
	private JButton barButton = new JButton("BarGilde");
	private JButton beheerButton = new JButton("Beheer");
	private Initializer init = new Initializer();
	private Bar[] bars;
	private Bar curBar;


	// VARIABLES for the BAR screen
	//----------------------------------------------------------
	//Read the products from the database
	private DecimalFormat df;

	//declare panels used
	private JPanel barPanel;
	private JPanel orderPanel;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(Run.screenSize.width/2-100,Run.screenSize.height/2-100);
		this.setSize(240,200);
		
		//initialize the bars variable
		bars = init.getBars();

		//create the startup panel by adding a bar and beheer button
		barButton.addActionListener(this);
		beheerButton.addActionListener(this);
		barButton.setPreferredSize(new Dimension(100,150));
		beheerButton.setPreferredSize(new Dimension(100,150));
		startPanel.add(barButton);
		startPanel.add(beheerButton);


		this.add(startPanel);
		//start the main screen
		this.setVisible(true);

		//initBarComponents();
	}
	/*public void initBarComponents()
	{
		//set the screentitle
		setTitle("Proteus Bargilde");
		// initialize Variables read from database
		productClass = turf.getProductClass();
		productPriceClass = turf.getProductPriceClass();
		classProducts = turf.getClassProducts();
		classProductsPrice = turf.getClassProductsPrice();
		df = turf.getdf();
		
		//initialize the panels
		barPanel = new JPanel();
		orderPanel = new JPanel();
		

		//Declare GUI attributes
		productButtons =  new JButton[productClass.length][];
		orders = new int[productClass.length][];
		orderedButtons = new JButton[productClass.length][];

		//loop through all available product classes and create buttons accordingly
		for(int i = 0; i < productClass.length;i++){
			//create empty orders array
			orders[i] = new int[classProducts[i].length];
			orderedButtons[i] = new JButton[classProducts[i].length];
			//create productbuttons
			productButtons[i] = new JButton[classProducts[i].length];
			for(int j = 0; j < classProducts[i].length;j++){
				productButtons[i][j] = new JButton();
				productButtons[i][j].setText(classProducts[i][j] +" "+ df.format((double)classProductsPrice[i][j]/100));
				productButtons[i][j].addActionListener(this);
				productButtons[i][j].addMouseListener(this);
				productButtons[i][j].setPreferredSize(new Dimension(150,80));
			}
		}
		// add class and productbuttons to the south area of the main panel
	}*/

	//main eventhandler for buttons being pressed
	public void actionPerformed(ActionEvent aE) {
		
		//eventhandlers for the START panel
		//------------------------------------------------------------------------------------------------------------------------------------
		
		if(aE.getSource() == barButton)
		{
			//list the visible bars in an input dialog
			String[] barOptions = new String[bars.length];
			for(int i =0;i <bars.length;i++)
			{
				barOptions[i] = bars[i].name;
			}
			String curBarName = (String) JOptionPane.showInputDialog(this,"Selecteer Bar", "Selecteer Bar", JOptionPane.PLAIN_MESSAGE, null,barOptions,barOptions[0]);
			for(int i = 0; i < bars.length;i++)
			{
				if(bars[i].name == curBarName)
					curBar = bars[i];
			}
			System.out.println(curBar.name);
			//this.remove(startPanel);
			
			//initBarComponents();
		}
		if(aE.getSource() == beheerButton)
		{
			//TODO beheer shit hier
		}
		
		
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

