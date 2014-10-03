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

import main.Run;
import net.miginfocom.swing.MigLayout;
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
	//declare panels used and layout
	private MigLayout mig;
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

		//create the layout
		mig = new MigLayout("wrap 4","200!","100!");
		this.setLayout(mig);
		//initialize the panels
		productPanel = new JPanel();
		topPanel = new JPanel();
		orderPanel = new JPanel();
		orderPanel.setLayout(new MigLayout("wrap 1"));
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
		testArea = new JTextArea();
		testArea.setMinimumSize(new Dimension(200,Run.screenSize.height));
		testArea.setMaximumSize(new Dimension(200,Run.screenSize.height));
		testArea.setPreferredSize(new Dimension(200,Run.screenSize.height));
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
				this.add(productButtons[i][j],"grow");
			}
		}

		topPanel.add(new JLabel("PROTEUS ERETES BARGILDE"));
		sidePanel.add(testArea);
		this.add(sidePanel,"east");
		this.add(topPanel,"north");
		bottomPanel.add(orderPanel);
		this.add(bottomPanel,"south");
		screenCards.show(contentPanel, "BAR");
	}

	@Override
	public void actionPerformed(ActionEvent aE) {
		for(int i = 0; i < ppc.getProductClassesSize();i++){
			//create eventhandlers for product buttons that are pressed
			for(int j=0; j < ppc.getProductsSize(i);j++)
			{
				if(aE.getSource() == productButtons[i][j])
				{
					testArea.setText("U heeft een " + ppc.getProductName(i, j) + " besteld!");
					if(orders[i][j] == 0)
					{
						orderedButtons[i][j] = new JButton();
						orderedButtons[i][j].setPreferredSize(new Dimension(200,50));
						orderedButtons[i][j].addActionListener(this);
						orderedButtons[i][j].addMouseListener(this);
						orderPanel.add(orderedButtons[i][j]);
					}							
					orders[i][j]++;
					orderedButtons[i][j].setText(orders[i][j] + " x " + ppc.getProductName(i, j));

				}
				if(aE.getSource() == orderedButtons[i][j])
				{
					orderPanel.remove(orderedButtons[i][j]);
					orders[i][j] = 0;
					this.validate();
				}
			}
		}

		//create the event handler for the "orderButton" or Afrekenknop
		if(aE.getSource() == orderButton)
		{
			testArea.setText("U heeft de volgende producten besteld: \n");
			int totalPrice = 0;
			for(int i =0; i < ppc.getProductClassesSize();i++)
			{
				for(int j =0; j < ppc.getProductsSize(i);j++)
				{
					if(orders[i][j] > 0){
						totalPrice += orders[i][j] * ppc.getProductPrice(i, j);
						orderPanel.remove(orderedButtons[i][j]);
						orderPanel.validate();
						repaint();
						testArea.append(orders[i][j] + " x " + ppc.getProductName(i, j) +" voor "+ init.getdf().format((double)ppc.getProductPrice(i, j)*orders[i][j]/100) + "\n");
						orders[i][j] = 0;
					}
				}
			}
			testArea.append("Voor een totaal van: " + init.getdf().format((double)totalPrice/100) + "!");
		}
		//----------------------------------------------------------------------------------------------------------------------------------------------------------
		//end of eventhandlers for the BAR panel

	}

	public void mouseClicked(MouseEvent e) {
		for(int i =0; i < ppc.getProductClassesSize();i++)
		{
			for(int j =0; j < ppc.getProductsSize(i);j++)
			{
				if(e.getButton() == MouseEvent.BUTTON3){
					if( e.getComponent() == productButtons[i][j] || e.getComponent() == orderedButtons[i][j])
					{
						if(orders[i][j] >1){
							orders[i][j]--;
							orderedButtons[i][j].setText(orders[i][j] + " x " + ppc.getProductName(i, j));
						}
						else if(orders[i][j] == 1){
							orderPanel.remove(orderedButtons[i][j]);
							orders[i][j]--;
							orderPanel.validate();
						}
					}
				}	
			}

		}
	}


		@Override
		public void mouseEntered(MouseEvent e) {// TODO Auto-generated method stu
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
	}
