package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
		private JPanel contentPanel;
		private CardLayout screenCards;
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
	
	public BarPanel(Initializer initIn, CardLayout screenCardsIn,JPanel contentPanelIn)
	{
		//set initialization arguments
		init = initIn;
		screenCards = screenCardsIn;
		contentPanel = contentPanelIn;
		this.setLayout(new BorderLayout());
		
		//initialize the panels
		productPanel = new JPanel();
		topPanel = new JPanel();
		orderPanel = new JPanel();
		bottomPanel = new JPanel();
		sidePanel = new JPanel();
	}
	
	
	public void initBarComponents(Bar curBarIn, Client curClientIn)
	{
		//read data from database
		curBar = curBarIn;
		curClient = curClientIn;
		init.reInitializeBar(curBar.getID());
		ppc = init.getPPC();
		


		//Declare GUI attributes
		productButtons =  new JButton[ppc.getProductClassesSize()][];
		orders = new int[ppc.getProductClassesSize()][];
		orderedButtons = new JButton[ppc.getProductClassesSize()][];

		//loop through all available product classes and create buttons accordingly
		for(int i = 0; i < ppc.getProductClassesSize();i++){
				int nProducts = ppc.getProductsSize(i);
				//initiate the orderbuttons and orderarrays
				orders[i] = new int[nProducts];
				orderedButtons[i] = new JButton[nProducts];
				//create productbuttons
				productButtons[i] = new JButton[nProducts];
				for(int j = 0; j < nProducts;j++){
					productButtons[i][j] = new JButton();
					productButtons[i][j].setText(ppc.getProductName(i, j) +" "+ init.getdf().format((double)ppc.getProductPrice(i, j)/100));
					productButtons[i][j].addActionListener(this);
					productButtons[i][j].addMouseListener(this);
					productButtons[i][j].setBackground(ppc.getProductClass(i).getColor());
					productButtons[i][j].setPreferredSize(new Dimension(150,80));
					productPanel.add(productButtons[i][j]);
				}
			}
		
		topPanel.add(new JLabel("PROTEUS ERETES BARGILDE"));
		productPanel.setLayout(new GridLayout(5,3));
		
		this.add(topPanel,"North");
		this.add(productPanel,"Center");
		this.add(bottomPanel,"South");
		this.add(sidePanel,"East");
		screenCards.show(contentPanel, "BAR");
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
