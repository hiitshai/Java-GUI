import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class Database {
	
	// JDBC connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/usersdb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";	
	
	PreparedStatement stmt = null;
	Statement statement = null;
	Connection c = null;
	
	
	
	public Database() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/usersdb",
					"postgres", "password");
			System.out.println("Connected to the database.");
			
			//createTable();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
		}
	}
				
	public void createTable() {
		
		try {
			
			String sql = "CREATE TABLE COMPANY " +
			"(USERNAME TEXT PRIMARY KEY NOT NULL," +
			" PASSWORD TEXT NOT NULL," +
			" FirstNAME TEXT NOT NULL," +
			" LastNAME TEXT NOT NULL," +
			"Email TEXT NOT NULL, " +
			" ADDRESS CHAR(50), " +
			"POSITION TEXT NOT NULL, " +
			"ITEM TEXT NULL, " + 
			"TOTAL_COST TEXT NULL" +
			"QUANTITIY TEXT NULL)";
			statement.executeUpdate(sql);
			statement.close();
			c.close();
			System.out.println("Table has been created.");
			
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
		}
	}
	
		//How do i add information to my database using a seperate class
		/*try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "INSERT INTO COMPANY("
					+ "USERNAME,PASSWORD,FirstNAME,LastNAME,EMAIL,ADDRESS)"+
					"VALUES('BOB', 'BOBISCOOL' ,'BOB' ,'COOL' ,'BOBISCOOL@GMAIL.COM' ,'ARIZONA' );";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.commit();
			c.close();
			System.out.println("Added element to table.");
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
	}*/
	
	
		/*try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from company;");
			
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String email = rs.getString("email");
				String address = rs.getString("address");
				
				System.out.println("Username: "+username);
				System.out.println("Password: "+password);
				System.out.println("First Name: "+firstname);
				System.out.println("Last Name:" +lastname);
				System.out.println("Email: "+email);
				System.out.println("Address: "+address);
				System.out.println();
			}
			System.out.println("Done adding query");
			rs.close();
			stmt.close();
			c.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
	}*/
	
	
	
	
	public boolean function(String password, String username) throws SQLException {
		
		c = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/usersdb",
				"postgres", "password");
		stmt = c.prepareStatement("SELECT * FROM company WHERE username = ? and password = ?");
		boolean works = false;
		stmt.setString(1, username);
		stmt.setString(2, password);
		
		ResultSet searchQuery = stmt.executeQuery();
		
		if(searchQuery.next()) {
			
			System.out.println("working");
			searchQuery.close();
			stmt.close();
			c.close();
			works = true;
		} else {
			System.out.println("password is incorrect");
		}
		return works;
		
	}
	
	public boolean existsInDatabase(String username) throws SQLException {
		
		c = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/usersdb",
				"postgres", "password");
		stmt = c.prepareStatement("SELECT * FROM company WHERE username = ? ");
		boolean works = false;
		stmt.setString(1, username);
		
		ResultSet searchQuery = stmt.executeQuery();
		
		if(searchQuery.next()) {
			
			System.out.println("working");
			searchQuery.close();
			stmt.close();
			c.close();
			works = true;
		} else {
			System.out.println("username is not found");
		}
		return works;
		
	}	
	
	
	public void addAccount(String user,String pass,String first,String last,String email,String addr, String position) {
		
		try {
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/usersdb",
					"postgres", "password");
			Class.forName("org.postgresql.Driver");
			System.out.println("Connected to the database.");
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
				
		}
		
		try {
			c.setAutoCommit(false);
			//stmt = c.createStatement();
			String sql = "INSERT INTO COMPANY("
					+ "USERNAME,PASSWORD,FirstNAME,LastNAME,EMAIL,ADDRESS,POSITION,ITEM,QUANTITIY)"+
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
			stmt = c.prepareStatement(sql);
			
			stmt.setString(1, user);
			stmt.setString(2, pass);
			stmt.setString(3, first);
			stmt.setString(4, last);
			stmt.setString(5, email);
			stmt.setString(6, addr);
			stmt.setString(7, position);
			stmt.setString(8, " ");
			stmt.setString(9, Integer.toString(0));
			
			//stmt.executeUpdate(sql);
			stmt.execute();
			
			stmt.close();
			c.commit();
			c.close();
			System.out.println("Added element to table.");
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
	
		}		
	}
	
	public void addItem(String item, int quantity, String user) {
		
		try {
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/usersdb",
					"postgres", "password");
			Class.forName("org.postgresql.Driver");
			System.out.println("Connected to the database - getting items.");
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
		}
		
		
		
		try {
			c.setAutoCommit(false);
			String sql = "UPDATE COMPANY set ITEM = '"+item+"', QUANTITY = '"+quantity+"' WHERE USERNAME = '"+user+"' ";
			stmt = c.prepareStatement(sql);
			stmt.setString(1, item);
			stmt.setLong(2, quantity);
			stmt.setString(3, user);
			
			
			stmt.execute();
			stmt.close();
			c.commit();
			c.close();
			System.out.println("Added item to table.");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+ e.getMessage());
			System.exit(0);
	
		}	
	}

	public String getUserPosition(String username) {
		// Method to check the user type (customer or employee)
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String userType = "unknown"; // Default to 'unknown' if user type cannot be determined.

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL query
            String query = "SELECT position FROM company WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            // Execute the query and retrieve the result
            resultSet = preparedStatement.executeQuery();

            // Check if the result set has a row
            if (resultSet.next()) {
                userType = resultSet.getString("position");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userType;		
	}

	
	
    // Method to retrieve the existing cart items for a specific user
    private LinkedList<CartItem> getCartItems(String username) {
        LinkedList<CartItem> cartItems = new LinkedList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL query to fetch cart items for the specified user
            String query = "SELECT item, quantitiy, total_cost FROM company WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            // Execute the query and retrieve the cart items
            resultSet = preparedStatement.executeQuery();

            // Check if the result set has a row
            while (resultSet.next()) {
                String itemName = resultSet.getString("item");
                int quantity = resultSet.getInt("quantitiy");
                double totalCost = resultSet.getDouble("total_cost");
                CartItem cartItem = new CartItem(itemName, quantity, totalCost);
                cartItems.add(cartItem);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cartItems;
    }

    // Method to add or update an item in the cart for a specific user
    
	public void updateCartItem(String username, String item, int newquantity, double newtotalCost) {
		// TODO Auto-generated method stub
		LinkedList<CartItem> existingCartItems = getCartItems(username);
        boolean foundItem = false;

        // Update or add the item in the cart
        for (CartItem cartItem : existingCartItems) {
            if (cartItem.getItemName().equals(item)) {
                cartItem.setQuantity(newquantity);
                cartItem.setTotalCost(newtotalCost);
                foundItem = true;
                break;
            }
        }

        if (!foundItem) {
            CartItem newItem = new CartItem(item, newquantity, newtotalCost);
            existingCartItems.add(newItem);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL query to update the cart for the specified user
            String query = "UPDATE company SET item = ?, quantitiy = ?, total_cost = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);

            // Execute the update query for each item in the cart
            for (CartItem cartItem : existingCartItems) {
                String itemName = cartItem.getItemName();
                int quantity = cartItem.getQuantity();
                double totalCost = cartItem.getTotalCost();
                preparedStatement.setString(1, itemName);
                preparedStatement.setInt(2, quantity);
                preparedStatement.setDouble(3, totalCost);
                preparedStatement.setString(4, username);
                preparedStatement.addBatch();
            }

            // Execute the batch update
            preparedStatement.executeBatch();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	// Method to list all usernames
    public List<String> listAllUsernames() {
        List<String> usernames = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL query to fetch all usernames from the users table
            String query = "SELECT username FROM company";
            preparedStatement = connection.prepareStatement(query);

            // Execute the query and retrieve the usernames
            resultSet = preparedStatement.executeQuery();

            // Add each username to the list
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                usernames.add(username);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usernames;
    }

	public void listUserCartItems(String username) {
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            // Load the PostgreSQL JDBC driver
	            Class.forName("org.postgresql.Driver");

	            // Create the database connection
	            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            // Prepare the SQL query to fetch cart items for the specified user
	            String query = "SELECT item, quantitiy, totalcost FROM company WHERE username = ?";
	            preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setString(1, username);

	            // Execute the query and retrieve the cart items
	            resultSet = preparedStatement.executeQuery();

	            // Check if the result set has any rows
	            while (resultSet.next()) {
	                String itemName = resultSet.getString("item");
	                int quantity = resultSet.getInt("quantitiy");
	                double totalCost = resultSet.getDouble("totalcost");

	                System.out.println("Item Name: " + itemName);
	                System.out.println("Quantity: " + quantity);
	                System.out.println("Total Cost: " + totalCost);
	                System.out.println("---------------------------");
	            }
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Close the resources
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }		
	}
	
	// Method to fetch cart items for the specified user from the database
    public List<String> getUserCartItems(String username) {
        List<String> cartItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Create the database connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL query to fetch cart items for the specified user
            String query = "SELECT item FROM company WHERE username = '"+username+"'";
            preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setString(1, username);

            // Execute the query and retrieve the cart items
            resultSet = preparedStatement.executeQuery();

            // Check if the result set has a row
            if (resultSet.next()) {
                // The "cart" column is assumed to contain comma-separated items
                String cart = resultSet.getString("item");
                if (cart != null) {
                    String[] cartItemsArray = cart.split(",");
                    for (String item : cartItemsArray) {
                        cartItems.add(item.trim());
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cartItems;
    }
}

	
	