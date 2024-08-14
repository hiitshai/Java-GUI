import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountPanel extends JPanel implements ActionListener {

	private GridBagConstraints gbc;
	
	private JLabel firstnameLabel;
	protected JTextField firstnameTextfield;
	
	private JLabel lastnameLabel;
	protected JTextField lastnameTextfield;
	
	private JLabel emailLabel;
	protected JTextField emailTextfield;
	
	private JLabel addressLabel;
	protected JTextField addressTextfield;
	
	private JLabel createUserLabel;
	protected JTextField createUserTextfield;
	
	private JLabel createPasswordLabel;
	protected JTextField createPasswordTextfield;
	
	protected String position;
	
	boolean addressLabelIsOnPanel;
	boolean addressTextfieldIsOnPanel;
	
	protected JButton createAccountButton;

	private JRadioButton customerRadioButton;

	private JRadioButton employeeRadioButton;
	
	
    public AccountPanel() {
    	
        createAccountPanel();
        
    }

    private void createAccountPanel() {
    	
        // Set layout for the panel
        setLayout(new GridBagLayout());
        setBackground(Color.pink);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add the Create Account Label at the top
        JLabel createAccountLabel = new JLabel("Create Account");
        createAccountLabel.setFont(createAccountLabel.getFont().deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(createAccountLabel, gbc);

        // Are you a customer or an employee? Label
        JLabel label = new JLabel("Are you a customer or an employee? ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(label, gbc);
        
        // Add radio buttons to panel
        addRadioButton();
        

		firstnameLabel = new JLabel("First name: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.EAST;
		add(firstnameLabel, gbc);
		
		firstnameTextfield = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1; 
		add(firstnameTextfield,gbc);
		
		lastnameLabel = new JLabel("Last name: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.EAST;
		add(lastnameLabel, gbc);
		
		lastnameTextfield = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1; 
		add(lastnameTextfield,gbc);
		
		emailLabel = new JLabel("Email: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.EAST;
		add(emailLabel, gbc);
		
		emailTextfield = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1; 
		add(emailTextfield,gbc);
		
		addressLabel = new JLabel("Address: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.EAST;
		add(addressLabel, gbc);
		
		addressTextfield = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1; 
		add(addressTextfield,gbc);
		
		createUserLabel = new JLabel("Username: ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.EAST;
		add(createUserLabel, gbc);
		
		createUserTextfield = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1; 
		add(createUserTextfield,gbc);
		
		createPasswordLabel = new JLabel("Password: ");
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.EAST;
		add(createPasswordLabel, gbc);
		
		createPasswordTextfield = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.gridwidth = 1; 
		add(createPasswordTextfield,gbc);
		
        
        // Add the Create Account button at the bottom
        createAccountButton = new JButton("Create Account");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createAccountButton, gbc);  
    }
    
    private void addRadioButton() {
		
    	// Create a button group to group the radio buttons together
        ButtonGroup buttonGroup = new ButtonGroup();

        // Create the radio buttons
        customerRadioButton = new JRadioButton("Customer");
        employeeRadioButton = new JRadioButton("Employee");

        // Add the radio buttons to the button group
        buttonGroup.add(customerRadioButton);
        buttonGroup.add(employeeRadioButton);
        
        // Add ActionListeners to the radio buttons
        customerRadioButton.addActionListener(this);
        employeeRadioButton.addActionListener(this);
        
        // Add the Ribbon Buttons to the panel using GridBagConstraints
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(customerRadioButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(employeeRadioButton, gbc);
        
	}
    
    public boolean isPanelVisible() {
    	return isVisible();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==customerRadioButton) 
		{
			// Address JLabel and JTextField is displayed to the panel
			addComponentToLayout();
			
			// When the customerRadioButton is pressed, the user's position is set to Customer
        	position = customerRadioButton.getText();
            
		}
		else if (e.getSource()==employeeRadioButton) 
		{
			// Address JLabel and JTextField is removed from the panel
			removeComponentFromLayout();
			
			// When the employeeRadioButton is pressed, the user's position is set to Employee
            position = employeeRadioButton.getText();
		}
		
	}

	private void removeComponentFromLayout() {
		
		addressLabelIsOnPanel = isAncestorOf(addressLabel);
		addressTextfieldIsOnPanel = isAncestorOf(addressTextfield);
		
		// Check if the address components are already on the panel
		if (addressLabelIsOnPanel && addressTextfieldIsOnPanel) 
		{			
			// Remove the address components from the layout
			remove(addressLabel);
			remove(addressTextfield);
			
			// Revalidate and repaint the panel to reflect the changes
	        revalidate();
	        repaint();
		}
	}

	private void addComponentToLayout() {
		
		addressLabelIsOnPanel = isAncestorOf(addressLabel);
		addressTextfieldIsOnPanel = isAncestorOf(addressTextfield);
		
		// Checks if the component is already on the layout
		if (!addressLabelIsOnPanel && !addressTextfieldIsOnPanel) 
		{			
			// Add the Address JLabel and JTextField component to the panel
			// Create GridBagConstraints for the addressLabel component
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.EAST;
			
			// Add the new component to the layout
			add(addressLabel, gbc);
			
			// Create GridBagConstraints for the addressTextfield component
			gbc.gridx = 1;
			gbc.gridy = 5;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			add(addressTextfield, gbc);
			
			// Revalidate and repaint the panel to reflect the changes
			revalidate();
			repaint();
		}
	}
}