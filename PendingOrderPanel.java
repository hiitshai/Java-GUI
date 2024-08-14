import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PendingOrderPanel extends JPanel implements ActionListener {

	private GridBagConstraints gbc;
	private JLabel poLabel;
	private JLabel order;
	private JComboBox<String> cb;
	private JButton submitButton;
    private Database database;
	private JLabel completedLabel;
	private JTextArea cartItemsTextArea;

	public PendingOrderPanel() {
		
		database = new Database();
		setLayout(new GridBagLayout());
        setBackground(null);
        
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
		
		// Pending Order Label
		poLabel = new JLabel("Pending Orders");
		poLabel.setFont(poLabel.getFont().deriveFont(24f));
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(poLabel, gbc);
        
        
        addUserCartItemsList();
        
		
	}
	
	
	private void addUserCartItemsList() {
		
		List<String> usernames = database.listAllUsernames();

		int i = 1;
        for (String username: usernames) {
            System.out.println(usernames);
            database.listUserCartItems(username);
            
            // Order Label
            order = new JLabel(username + "'s Order: "); 
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.gridwidth = 1;
            add(order, gbc); 
            
            // Order Drop Down Menu
            String[] choices = { "Not Completed","Completed"};
            cb = new JComboBox<String>(choices);
            cb.setVisible(true);
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.gridwidth = 1;
            cb.addActionListener(this);
            add(cb, gbc);
            
            // Submit Button
            submitButton = new JButton("Submit");
            gbc.gridx = 2;
            gbc.gridy = i;
            gbc.gridwidth = 1;
            add(submitButton, gbc);
            submitButton.addActionListener(this);
            i++;
        }        
        
	}
	
	public void setVisibility(boolean t) {
		setVisible(t);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==submitButton) {
			cb.setEnabled(false);
		}
	}
}