import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CheckoutPanel extends JPanel {

	private JLabel checkoutLabel;
	private JLabel userNameLabel;
	private JTextField usernameTextfield;
	private JLabel itemsLabel;
	private Database database;
	private JLabel firstnameLabel;
	private JTextField firstnameTextfield;
	private JLabel lastnameLabel;
	private JTextField lastnameTextfield;
	private String username;
	private String firstname;
	private String lastname;
	private JButton checkoutButton;
	private JLabel messageLabel;
	private GridBagConstraints gbc;
	
	private String orderNumber;
	private int number;
	protected double orderTotal;
	private JLabel totalCostLabel;
	private JTextArea cartItemsTextArea;
	protected String cousername;

	public CheckoutPanel() {
		
		database = new Database();
		setLayout(new GridBagLayout());
        setBackground(null);
        
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
		
		// Checkout Label
		checkoutLabel = new JLabel("Check Out");
		checkoutLabel.setFont(checkoutLabel.getFont().deriveFont(24f));
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(checkoutLabel, gbc);
        
        // Items in Cart Label
        itemsLabel = new JLabel("Items in cart: ");
		gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(itemsLabel, gbc); 
        
        
        displayUserListOfItems();
        
        
        // Total Cost Label
        totalCostLabel = new JLabel("Total Cost: " + getOrderTotal()); //     ----------------------------- ordertotal
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(totalCostLabel, gbc);
        
        
        // Checkout Button
        checkoutButton = new JButton("Checkout");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(checkoutButton, gbc); 
        
        // fix this
        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println("Button clicked!");
                try {
					if (userExists()==true) {
						// Save a new "order" to the database ----------------------------------------------
								
						displayMessageLabel();
						
						remove(checkoutButton);
						revalidate();
						repaint();
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        };
        checkoutButton.addActionListener(buttonActionListener);
	}
	
	private void displayUserListOfItems() {
		// TODO Auto-generated method stub
		cartItemsTextArea = new JTextArea(10, 30);
        cartItemsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cartItemsTextArea);
        
        
        List<String> cartItems = database.getUserCartItems(getUsername()); 
        System.out.println(cartItems);
        displayCartItems(cartItems);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(scrollPane, gbc);
	}

	// Method to display the cart items in the JTextArea
    private void displayCartItems(List<String> cartItems) {
        cartItemsTextArea.setText("");
        for (String item : cartItems) {
            cartItemsTextArea.append(item + "\n");
        }
    }
    
	private boolean userExists() throws SQLException {
		// Check if the username matches an account in the database
        return database.existsInDatabase(cousername);
	}
	
	public void displayMessageLabel() {
        // Display a message "Your order for ​number ​items ($​price)​ was completed successfully.”
        messageLabel = new JLabel("Your order for number items ($price) was completed successfully.");
        messageLabel.setForeground(Color.red);
        messageLabel.setOpaque(true);
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(messageLabel, gbc);		        		
	}

	public void setUsername(String user) {
		cousername = user;
	}
	
	public String getUsername() {
		return cousername;
	}

	public void setOrderTotal(double totalCost) {
		orderTotal = totalCost;
	}
	
	public double getOrderTotal() {
		return orderTotal;
	}
}