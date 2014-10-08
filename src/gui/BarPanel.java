package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import main.Run;
import net.miginfocom.swing.MigLayout;
import database.Initializer;
import database.Transaction;
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
	private JPanel orderPanel;
	private JPanel leftPanel;
	private JPanel keyPadPanel;
	private JPanel paymentPanel;
	private JPanel orderButtonPanel;
	private JPanel accountPanel;
	//Declare GUI attributes
	private JTextArea infoArea;
	private JTextArea testArea;
	private JButton[][] productButtons;
	private JButton orderButton;
	private int[][] orders;
	private JButton[][] orderedButtons;
	
	private JButton[] paymentChoiceButtons;
	//keypad buttons
	private JButton[] multiplierButtons;
	private JButton multiplierButton;
	private JTextArea multiplierArea;
	private int multiplication;
	//-----------------------------------------------------
	//ENd of variables for BAR screen

	public BarPanel(Initializer initIn, CardLayout screenCardsIn,JPanel contentPanelIn)
	{
		//set initialization arguments
		init = initIn;
		screenCards = screenCardsIn;
		contentPanel = contentPanelIn;

		//create the layout
		this.setLayout(new MigLayout("fillx"));

		//initialize the panels
		productPanel = new JPanel(); //shows all the productbuttons
		orderPanel = new JPanel();//shows all current orders as buttons
		orderPanel.setLayout(new MigLayout("wrap 1"));
		leftPanel = new JPanel();//shows orders and keypad
		keyPadPanel = new JPanel(); //shows the keypad
		paymentPanel = new JPanel(); //contants buttons for ways of payment and the orderbutton
		orderButtonPanel = new JPanel();
		accountPanel = new JPanel();
		//initialize the orderbutton
		orderButton = new JButton("Bestel");
		//initialize the multiplier buttons
		multiplierButtons = new JButton[10];
		multiplierArea = new JTextArea("");
		multiplierArea.setEditable(false);
		multiplierButton = new JButton("X");
	}


	public void initBarComponents()
	{
		//read data from database
		curClient = init.getCurClient();
		curBar = init.getCurBar();
		multiplication = 1;

		paymentPanel.setLayout(new MigLayout("wrap 1, insets 0"));
		orderButtonPanel.setLayout(new MigLayout("wrap 1, insets 0"));
		keyPadPanel.setLayout(new MigLayout("wrap 3","100!","100!"));
		leftPanel.setLayout(new MigLayout());
		//Declare GUI attributes
		testArea = new JTextArea();
		infoArea = new JTextArea();
		//create the payment buttons
		paymentChoiceButtons = new JButton[4];
		paymentChoiceButtons[0] = new JButton("PIN");
		paymentChoiceButtons[1] = new JButton("Contant");
		paymentChoiceButtons[2] = new JButton("Schrap");
		paymentChoiceButtons[3] = new JButton("WegBoeken");
		for(int i =0; i < paymentChoiceButtons.length;i++)
			paymentChoiceButtons[i].addActionListener(this);

		//create the multiplierButtons
		Font font = new Font("Teen",Font.BOLD,72);
		for(int i =0; i < 10; i++){
			multiplierButtons[i] = new JButton(Integer.toString(i));
			multiplierButtons[i].addActionListener(this);
			multiplierButtons[i].setFont(font);
			multiplierButtons[i].setForeground(new Color(0,80,255));
			multiplierButtons[i].setBackground(new Color(255,255,255));
		}
		multiplierButton.addActionListener(this);
		multiplierButton.setFont(font);
		multiplierButton.setForeground(new Color(0,80,255));
		multiplierButton.setBackground(new Color(255,255,255));
		for(int i =0; i < 3;i++)
			for(int j=2;j>=0;j--)
				keyPadPanel.add(multiplierButtons[9-(3*i)-j],"grow");//get the default calculator look
		keyPadPanel.add(multiplierButtons[0],"grow");
		keyPadPanel.add(multiplierArea,"grow");
		keyPadPanel.add(multiplierButton,"grow");
		multiplierArea.setFont(font);
		multiplierArea.setForeground(new Color(0,80,255));

		//set orderButton on
		orderButton.addActionListener(this);

		//add orders and the keypad to docked panel on the left
		orderPanel.add(Box.createHorizontalStrut(200),"wrap");
		leftPanel.add(orderPanel,"h 500:500:");
		leftPanel.add(keyPadPanel,"south");
		this.add(leftPanel,"west");

		//add the different components
		this.add(testArea,"w 200!,h 500!");
		for(int i =0; i < paymentChoiceButtons.length;i++)
			paymentPanel.add(paymentChoiceButtons[i],"w 200! , h 90! , wrap");
		paymentPanel.add(new JLabel("BETAALWIJZE"),"w 200!,h 90!");
		this.add(paymentPanel);
		orderButtonPanel.add(Box.createVerticalStrut(350));
		orderButtonPanel.add(orderButton,"w 200!, h 150!");
		this.add(orderButtonPanel);
		this.add(accountPanel);
		this.add(infoArea,"w 200!,h 500!, wrap");
		screenCards.show(contentPanel, "BAR");
		this.add(productPanel,"south");
	}

	public void initBarProducts()
	{
		productPanel.removeAll();
		//set the productPanel layout
		productPanel.setLayout(new MigLayout("wrap 7, insets 0,fillx",
				"", //column size
				"100!")); // row size
		//reInitialize the ppc
		init.reInitializeBar(curBar.getID());
		ppc = init.getPPC();
		//initialize the productButtons
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
				productPanel.add(productButtons[i][j],"grow");
			}
		}
		this.validate();
		this.repaint();
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
					orders[i][j] += multiplication;
					orderedButtons[i][j].setText(orders[i][j] + " x " + ppc.getProductName(i, j));
					multiplierArea.setText("");
					multiplication = 1;

				}
				if(aE.getSource() == orderedButtons[i][j])
				{
					orderPanel.remove(orderedButtons[i][j]);
					orders[i][j] = 0;
					this.validate();
					this.repaint();
				}
			}
		}

		//create the event handler for the "orderButton" or Afrekenknop
		if(aE.getSource() == orderButton)
		{
			testArea.setText(Transaction.doProductTransaction(init, orders, Transaction.CARD, curClient.getID(), 1));
			testArea.append("\n"+"U heeft de volgende producten besteld: \n");
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

		//create the event handlers for the multiplication buttons
		for(int i =0; i < 10; i++){
			if(aE.getSource() == multiplierButtons[i])
			{
				//get the text from the multiplication area and create a multiplication string
				String multiplicationString = multiplierArea.getText()+Integer.toString(i);
				multiplication = Integer.parseInt(multiplicationString);
				multiplierArea.setText(multiplicationString);
			}
		}
		if(aE.getSource() == multiplierButton)
		{
			multiplication = 1;
			multiplierArea.setText("");
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
							this.validate();
							this.repaint();
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
