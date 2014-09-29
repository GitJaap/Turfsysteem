import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class Interface_1 extends JFrame implements ActionListener{
	//variables
	private JTextArea display = new JTextArea(25,25);
	private JTextField queryField = new JTextField(20);
	private JTextField updateField = new JTextField(20);
	private JLabel queryLabel = new JLabel("Input queries");
	private JPanel queryPanel = new JPanel();
	private JPanel updatePanel = new JPanel();
	private JLabel updateLabel = new JLabel("Input updates");
	private JPanel inputPanel = new JPanel();
	private JButton queryButton = new JButton("Run Query");
	private JButton updateButton = new JButton("Run Update");
	private JButton commitButton = new JButton("Commit");
	private JButton rollbackButton = new JButton("Rollback");
	private JPanel centerPanel = new JPanel();
	
	private DBConnection dB = new DBConnection();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//functions
	public Interface_1()
	{
		setTitle("Proteus interface");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0,0);
		setSize(1600,1000);
		queryPanel.add("North",queryLabel);
		queryPanel.add("Center",queryField);
		queryPanel.add("South",queryButton);
		
		updatePanel.add("North",updateLabel);
		updatePanel.add("Center",updateField);
		updatePanel.add("South",updateButton);
		
		inputPanel.add("West",queryPanel);
		inputPanel.add("East",updatePanel);
		
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(commitButton);
		centerPanel.add(rollbackButton);
		
		add("North",inputPanel);
		add("South", display);
		add("Center", centerPanel);
		
		updateButton.addActionListener(this);
		queryButton.addActionListener(this);
		commitButton.addActionListener(this);
		rollbackButton.addActionListener(this);
		setVisible(true);
		
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == updateButton)
		{
			String update = updateField.getText();
			dB.runUpdate(update);
			display.setText("Update succesfull!");
			}
		if(e.getSource() == queryButton)
		{
			String query = queryField.getText();
			display.setText("");
				dB.runQuery(query);
				String result = dB.getNextStr();
				while(!result.isEmpty())
				{
					display.append(result+"\n");
					result = dB.getNextStr();
				}	
		}
		if(e.getSource() == commitButton)
		{
			dB.commit();
		}
		if(e.getSource() == rollbackButton)
		{
			dB.rollback();
		}
	}
	
	
}
