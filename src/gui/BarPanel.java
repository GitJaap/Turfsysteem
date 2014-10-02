package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import database.Initializer;
import database.data.Bar;
import database.data.Client;
import database.data.ProductPriceClass;

public class BarPanel extends JPanel implements ActionListener, MouseListener{

	// VARIABLES for the BAR screen
		//----------------------------------------------------------
		//main data attributes
		private ProductPriceClass ppc;
		private Initializer init;
		private Bar curBar;
		private Client curClient;
		//set a pointer to the panel above this one in the panel hierarchy
		private JPanel previousPanel;
		//declare panels used
		private JPanel productPanel;
		private JPanel topPanel;
		private JPanel orderPanel;
		private JPanel bottomPanel;
		private JPanel sidePanel;
		//Declare GUI attributes
		private JTextArea testArea;
		private JButton[][] productButtons;
		private JButton orderButton;
		private int[][] orders;
		private JButton[][] orderedButtons;
		//-----------------------------------------------------
		//ENd of variables for BAR screen
	
	public BarPanel(Initializer initIn,Bar curBarIn,Client curClientIn, JPanel previousPanelIn)
	{
		//set initialization arguments
		curBar = curBarIn;
		curClient = curClientIn;
		init = initIn;
		previousPanel = previousPanelIn;
		this.setLayout(new BorderLayout());
		
		//initialize the panels
		productPanel = new JPanel();
		topPanel = new JPanel();
		orderPanel = new JPanel();
		bottomPanel = new JPanel();
		sidePanel = new JPanel();
	}
	
	
	public void initBarComponents()
	{
		//read data from database
		init.reInitializeBar(curBar.id);
		ppc = init.getPPC();


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
					productPanel.add(productButtons[i][j]);
				}
			}
			else{
				productButtons[i] = null;
			}
		}
		
		topPanel.add(new JLabel("PROTEUS ERETES BARGILDE"));
		productPanel.setLayout(new GridLayout(5,3));
		
		this.add(topPanel,"North");
		this.add(productPanel,"Center");
		this.add(bottomPanel,"South");
		this.add(sidePanel,"East");
		this.repaint();
		this.validate();;
	}







	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}







	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
