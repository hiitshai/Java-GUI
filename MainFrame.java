
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;




public class MainFrame implements ActionListener {

	private JFrame frame;
	private JPanel buttonPanel;
	private JButton loginButton;
	private JButton createAnAccountButton;
	private JButton logoutButton;
	private JPanel loginPanel;
	private JPanel shopPanel;
	private JLabel searchLabel;
	private JTextField searchTextfield; 
	private JLabel signInLabel;
	private JButton signInButton;

	private Database database;
	private AccountPanel accPanel;
	private JLabel usernameLabel;
	private JTextField usernameTextfield; 
	private JLabel passwordLabel;
	private JTextField passwordTextfield; 
	private Boolean created = false;
	
	private JComboBox<?> comboBox;
	private JButton addToCart; 
	private String item;
	private int quantity;
	private JTextField quantityTextField;
	private JLabel quantityLabel;
	private Boolean loggingIn;
	String user;
	private JButton homeButton;
	private JButton checkoutButton;
	private GridBagConstraints gbc;
	private JButton poButton;
	
	private PendingOrderPanel poPanel;
	private CheckoutPanel checkoutPanel;
	private String userPosition;
	private JPanel logoutPanel;
	private JLabel logoutLabel;
	private double totalCost;
	

	public MainFrame() {
		
		database = new Database();
		accPanel = new AccountPanel();
		createLoginScreen();
	}
	
	
	
	private JFrame createLoginScreen() {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(900, 600));
		
		addButtonPanel();
		if(addLoginPanel()) {
			addShoppingPanel();
		}
		//addShoppingPanel();
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}
	
	private void addButtonPanel() {
		
		// the blue panel with buttons
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(Color.blue);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		
		loginButton = new JButton("Login");
		createAnAccountButton = new JButton("Create an account");
		
		// Login Button 
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(loginButton, gbc);
        
        // Create An Account Button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        buttonPanel.add(createAnAccountButton, gbc);		
		
		loginButton.addActionListener(this);
		createAnAccountButton.addActionListener(this);
		
		frame.getContentPane().add(buttonPanel, BorderLayout.WEST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==loginButton) {
			
			// display the login panel
			if (accPanel.isVisible()) {
				accPanel.setVisible(false);
				loginPanel.setVisible(true);
			}
			else 
				loginPanel.setVisible(true);	
		} 
		
		if (e.getSource()==createAnAccountButton){
			
			// display the create account panel
			if (loginPanel.isVisible()) {
				loginPanel.setVisible(false);
				addCreateAnAccountPanel();
				accPanel.setVisible(true);
			} else 
				accPanel.setVisible(true);
			
		} 
		
		if (e.getSource()==signInButton) {
			// when the Sign in button is pressed, the user's info needs to 
			// be checked if it matches an account in the database
			
			try {
				user = usernameTextfield.getText();
				boolean correctPassword = database.function(passwordTextfield.getText(), usernameTextfield.getText());
				
				
				if (loginPanel.isVisible() && correctPassword == true) {
					loginPanel.setVisible(false);
					addLoginButtonPanel();
					//user = usernameTextfield.getText();
					addShoppingPanel();
					loggingIn = true;
				} else 
					loginPanel.setVisible(true);
				
			} catch (SQLException e1) {	
			
				e1.printStackTrace();
			}
		}
		
		if(e.getSource()==accPanel.createAccountButton) {
			// when the Create Account button is pressed, the user's info needs to 
			// be inserted to the database
			
			if (accPanel.position.equalsIgnoreCase("Customer")) {
				
				database.addAccount(accPanel.createUserTextfield.getText(), accPanel.createPasswordTextfield.getText(), 
					accPanel.firstnameTextfield.getText(), accPanel.lastnameTextfield.getText(), 
					accPanel.emailTextfield.getText(), accPanel.addressTextfield.getText(), 
					"Customer");
				
			} else if (accPanel.position.equalsIgnoreCase("Employee")) {
				
				accPanel.addressTextfield.setText(null);
				
				database.addAccount(accPanel.createUserTextfield.getText(), accPanel.createPasswordTextfield.getText(), 
						accPanel.firstnameTextfield.getText(), accPanel.lastnameTextfield.getText(), 
						accPanel.emailTextfield.getText(), accPanel.addressTextfield.getText(), 
						"Employee");
			}
			
			accPanel.setVisible(false);
			loginPanel.setVisible(true);
			
		}
		
		if (e.getSource()==addToCart) {
			// save the cart to the database
			System.out.println(user + " added the following to the cart: " + quantityTextField.getText()
					+ comboBox.getSelectedItem().toString());
			item = comboBox.getSelectedItem().toString();
			quantity = Integer.parseInt(quantityTextField.getText());
			
			totalCost = 150.00;
			database.updateCartItem(user, item, quantity, totalCost);
			
		}
		
		// store home button is pressed
		if (e.getSource() == homeButton) {
			if (!shopPanel.isVisible()) {
				
				if (poPanel != null)
					poPanel.setVisibility(false);
				
				if (checkoutPanel != null)
					checkoutPanel.setVisible(false);

				shopPanel.setVisible(true);
			}
		}
	
		// checkout button is pressed
		if (e.getSource()==checkoutButton) {
			
			shopPanel.setVisible(false);
			if (poPanel != null)  
				poPanel.setVisibility(false);
			
			addCheckOutPanel();
			checkoutPanel.setVisible(true);
		}
		
		// pending order button is pressed
		if (e.getSource() == poButton) {
			
				
				if (shopPanel != null) 
					shopPanel.setVisible(false);
				
				if (checkoutPanel != null) 
					checkoutPanel.setVisible(false);
				
				addPendingOrderPanel();
				poPanel.setVisibility(true);
			
		}
		
		// logout button is pressed
		if (e.getSource()==logoutButton) {
			
			shopPanel.setVisible(false);
			
			if (poPanel != null) 
				poPanel.setVisibility(false);
			
			if (checkoutPanel != null)
				checkoutPanel.setVisible(false);
			
			addLogoutPanel();
			logoutPanel.setVisible(true);
		}
	}
	
	private void addCreateAnAccountPanel() {
		
		// create account panel
		accPanel = new AccountPanel();
		accPanel.createAccountButton.addActionListener(this);
		frame.getContentPane().add(accPanel, BorderLayout.CENTER);
	}

	private Boolean addLoginPanel() {
		
		loggingIn = false;
		loginPanel = new JPanel(new GridBagLayout());
		loginPanel.setBackground(Color.pink);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
		
		// Sign in Label
		signInLabel = new JLabel("Sign in");
		signInLabel.setFont(signInLabel.getFont().deriveFont(24f));
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(signInLabel, gbc);
        	
        usernameLabel = new JLabel("Username: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(usernameLabel, gbc);
        		
        usernameTextfield = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; 
        loginPanel.add(usernameTextfield,gbc);
        		
        passwordLabel = new JLabel("Password: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passwordLabel, gbc);
        		
        passwordTextfield = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; 
        loginPanel.add(passwordTextfield,gbc);

        // Login Button
        signInButton = new JButton("Sign in");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
       	gbc.anchor = GridBagConstraints.CENTER;
		signInButton.addActionListener(this);
		loginPanel.add(signInButton, gbc);
		
		frame.getContentPane().add(loginPanel, BorderLayout.CENTER);
		return loggingIn; 
	}
	
	public void addLoginButtonPanel() {
		// the blue panel with buttons that the customers will see after they successfully login
		homeButton = new JButton("Store home");
		checkoutButton = new JButton("Checkout");
		poButton = new JButton("Pending Orders");
		logoutButton = new JButton("Logout");		
		
		homeButton.addActionListener(this);
		checkoutButton.addActionListener(this);
		poButton.addActionListener(this);
		logoutButton.addActionListener(this);
		
		// Replace the Create Account Button with the Checkout Button
		buttonPanel.remove(loginButton);
		buttonPanel.remove(createAnAccountButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		buttonPanel.add(homeButton, gbc);
					
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		buttonPanel.add(checkoutButton, gbc);
		
		// if the user is an employee, display pending order button 
		if (database.getUserPosition(user).equalsIgnoreCase("Employee")) {
			System.out.println(user + " position is" + database.getUserPosition(user));
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			buttonPanel.add(poButton, gbc);		
		}

		
		// Add the Logout Button
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		buttonPanel.add(logoutButton, gbc);
					
		// Revalidate and repaint the panel to the changes
		buttonPanel.revalidate();
		buttonPanel.repaint();
		
		frame.getContentPane().add(buttonPanel, BorderLayout.WEST);
	}
	
	public void addShoppingPanel() {
	
		shopPanel = new JPanel(new GridBagLayout());
		
		shopPanel.setLayout(new GridBagLayout());
		shopPanel.setBackground(Color.pink);
    	gbc.insets = new Insets(5, 5, 5, 5);
    	
    	searchLabel = new JLabel("Search Item: ");
		searchLabel.setFont(searchLabel.getFont().deriveFont(14f));
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        	
        shopPanel.add(searchLabel, gbc);
        
        String[] items = {"", "CPU", "Graphic Card", "Storage", "Ram"};
        
        comboBox = new JComboBox<String>(items);
        comboBox.addActionListener(this);
        comboBox.setEditable(true);
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	gbc.gridwidth = 2;
    	gbc.anchor = GridBagConstraints.EAST;
    	shopPanel.add(comboBox, gbc);

		quantityLabel = new JLabel("Enter quantity: ");
		quantityLabel.setFont(searchLabel.getFont().deriveFont(14f));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		shopPanel.add(quantityLabel,gbc);

		quantityTextField = new JTextField(3);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		shopPanel.add(quantityTextField, gbc);

		addToCart = new JButton("Add Cart");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.SOUTH;
		addToCart.addActionListener(this);
		shopPanel.add(addToCart, gbc);
		
		frame.getContentPane().add(shopPanel, BorderLayout.CENTER);
	}
    		
	private void addPendingOrderPanel() {
		
		poPanel = new PendingOrderPanel();
		frame.getContentPane().add(poPanel, BorderLayout.CENTER);
	}
        
	private void addCheckOutPanel() {
		
		checkoutPanel = new CheckoutPanel();
		checkoutPanel.setUsername(usernameTextfield.getText());
		checkoutPanel.setOrderTotal(totalCost);
		
		
		frame.getContentPane().add(checkoutPanel, BorderLayout.CENTER);
	}

	private void addLogoutPanel() {
		
		frame.remove(buttonPanel);
		
		logoutPanel = new JPanel();
		
		logoutPanel.setLayout(new GridBagLayout());
		logoutPanel.setBackground(null);
    	gbc.insets = new Insets(5, 5, 5, 5);
    	
    	logoutLabel = new JLabel("You have logged out.");
    	logoutLabel.setForeground(Color.red);
    	logoutLabel.setOpaque(true);
    	logoutLabel.setFont(searchLabel.getFont().deriveFont(14f));
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        	
        logoutPanel.add(logoutLabel, gbc);
        frame.getContentPane().add(logoutPanel, BorderLayout.CENTER);
	}
}

    