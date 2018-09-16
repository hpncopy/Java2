import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
 
public class ticketSystem extends Application {
    
    // Create controls for display
    ComboBox cbTicketStatus = new ComboBox();
    TextField tfSummary = new TextField();
    ComboBox cbApp = new ComboBox();
    ComboBox cbAppStatus = new ComboBox();
    ComboBox cbPriority = new ComboBox();
    ComboBox cbAssignee = new ComboBox();
    TextField tfAssigneeEmail = new TextField();
    TextArea taDescription = new TextArea();

    @Override
    public void start(Stage primaryStage) { // Screen1
        displayTicket(primaryStage);
        
        primaryStage.setOnCloseRequest(e -> Platform.exit()); // Close Screen1
    }

    public void confirmation(Stage stage) { // Screen2
        Stage confStage = new Stage(); // Create Stage

        Text confText = new Text("Your action is successful. Email Sent."); // Display confirmation message

        HBox hBox = new HBox(5); // Create HBox to hold buttons
        hBox.setAlignment(Pos.CENTER);

        Button btnTicket = new Button(); // create new ticket button
        btnTicket.setText("New Ticket");
        btnTicket.setOnAction((ActionEvent e) -> { // Event handler for new ticket button
            start(stage); // Open Screen1 stage
            confStage.close(); // Close Screen2 stage
        });
        
        Button btnList = new Button(); // Create ticket list button
        btnList.setText("Ticket List");
        btnList.setOnAction((ActionEvent event) -> { // Event handler for ticket list button
            ticketList(stage); // Open Screen3 stage
            confStage.close(); // Close Screen2 stage
        });
            
        hBox.getChildren().addAll(btnTicket, btnList); // Add buttons to HBox

        BorderPane confLayout = new BorderPane(); // Create BorderPane to hold HBox
        confLayout.setCenter(confText); // Center buttons
        confLayout.setBottom(hBox); // Set HBox at botton of border pane
             
        Scene confScene = new Scene(confLayout, 1200, 400); // Create Scene to hold BorderPane
             
        confStage.setTitle("Ticket Submission Result");
        confStage.setScene(confScene); // Add scene to Stage
             
        confStage.show(); // Show stage
        stage.close(); // Close stage
        
    }
    
    public void error(Stage stage) { // Error Screen
        Stage confStage = new Stage(); // Create Stage

        Text confText = new Text("Ticket does not exist."); // Display confirmation message

        HBox hBox = new HBox(5); // Create HBox to hold buttons
        hBox.setAlignment(Pos.CENTER);

        Button btnTicket = new Button(); // create new ticket button
        btnTicket.setText("New Ticket");
        btnTicket.setOnAction((ActionEvent e) -> { // Event handler for new ticket button
            start(stage); // Open Screen1 stage
            confStage.close(); // Close Screen2 stage
        });
        
        Button btnList = new Button(); // Create ticket list button
        btnList.setText("Ticket List");
        btnList.setOnAction((ActionEvent event) -> { // Event handler for ticket list button
            ticketList(stage); // Open Screen3 stage
            confStage.close(); // Close Screen2 stage
        });
            
        hBox.getChildren().addAll(btnTicket, btnList); // Add buttons to HBox

        BorderPane confLayout = new BorderPane(); // Create BorderPane to hold HBox
        confLayout.setCenter(confText); // Center buttons
        confLayout.setBottom(hBox); // Set HBox at botton of border pane
             
        Scene confScene = new Scene(confLayout, 1200, 400); // Create Scene to hold BorderPane
             
        confStage.setTitle("Ticket Submitted");
        confStage.setScene(confScene); // Add scene to Stage
             
        confStage.show(); // Show stage
        stage.close(); // Close stage
        
    }

    public void ticketList(Stage stage) { // Screen3

        ResultSet rs; // Holds results from SQL query
        Stage listStage = new Stage(); // Create Stage

        HBox hBox = new HBox(5); // Create HBox to hold buttons
        hBox.setAlignment(Pos.CENTER);

        // Make a grid pane to hold values from db
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Column headers; Place nodes in gridPane (node, column, row, column span, row span)
        gridPane.add(new Label("ID Number "), 0, 0);
        gridPane.add(new Label("Ticket Status"), 1, 0);
        gridPane.add(new Label("Summary"), 2, 0);
        gridPane.add(new Label("Application"), 3, 0);
        gridPane.add(new Label("Application Status"), 4, 0);
        gridPane.add(new Label("Priority"), 5, 0);
        gridPane.add(new Label("Assignee"), 6, 0);
        gridPane.add(new Label("Assignee Email"), 7, 0);
        gridPane.add(new Label("Description"), 8, 0);

        try { // Get data from db to populate table
            Class.forName("com.mysql.jdbc.Driver");
           // "jdbc:mysql://localhost/javabook", "root", "root");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/team_assignment_table" , "root" , "4lU^L1vNaP");
            System.out.println("Database is connected");
            
            String query1 = "SELECT * FROM ticket";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query1);

            int rowCount = 1; // Need a new row for each ticket
                while (rs.next()) { // While there is still data in the result set
                    gridPane.add(new Label(rs.getString(1)), 0, rowCount); // add each field to gridPane
                    gridPane.add(new Label(rs.getString(2)), 1, rowCount);
                    gridPane.add(new Label(rs.getString(3)), 2, rowCount);
                    gridPane.add(new Label(rs.getString(4)), 3, rowCount);
                    gridPane.add(new Label(rs.getString(5)), 4, rowCount);
                    gridPane.add(new Label(rs.getString(6)), 5, rowCount);
                    gridPane.add(new Label(rs.getString(7)), 6, rowCount);
                    gridPane.add(new Label(rs.getString(8)), 7, rowCount);
                    gridPane.add(new Label(rs.getString(9)), 8, rowCount);

                    rowCount++; // increment rowCount
                }
            }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        
        Label lblIDnumber = new Label("ID Number"); // Label for button for user to select a particular ticket
        TextField tfIDnumber = new TextField(); // User enters an IDnumber to select a particular ticket
        Button btnTicket = new Button(); // Create new ticket button
        btnTicket.setText("New Ticket");
        btnTicket.setOnAction((ActionEvent e) -> { // Event handler for new ticket button
            start(stage); // Open screen1 stage
            listStage.close(); // Close ticket list stage
        });
        
        Button btnDetails = new Button(); // Create ticket details button
        btnDetails.setText("Ticket Details");
        btnDetails.setOnAction((ActionEvent event) -> { // Event handler for ticket details button
            displayTicketDetails(stage, Integer.valueOf(tfIDnumber.getText())); // Open Screen1 stage
            listStage.close(); // Close ticket details stage
        });
        hBox.getChildren().addAll(btnTicket, lblIDnumber, tfIDnumber, btnDetails); // Add buttons to HBox

        BorderPane listLayout = new BorderPane(); // Create BorderPane to hold HBox
        listLayout.setCenter(gridPane);
        listLayout.setBottom(hBox); // Set HBox at bottom of border pane
             
        Scene listScene = new Scene(listLayout, 1200, 400); // Create Scene to hold BorderPane
             
        listStage.setTitle("List of Tickets");
        listStage.setScene(listScene); // Add Scene to stage
             
        listStage.show(); // Show stage
        stage.close(); // Close stage
        
    }

    public void ticketDetails(Stage stage, Integer IDnumber) { // Screen4
        displayTicketDetails(stage, IDnumber); // Call code to access db and get info for a ticket
        
        stage.setOnCloseRequest(e -> Platform.exit()); // Close Screen4
    }

    public void displayTicket(Stage primaryStage) { // This code displays the gui for a ticket; Calls dbPut which opens DB and enters data to DB
        
        Integer IDnumber = 0; // This is a new ticket, so there is no IDnumber yet
        
        HBox hBox = new HBox(5); // Create an HBox to hold buttons
        hBox.setAlignment(Pos.CENTER);
        Button btnSubmit = new Button(); // Submit button
        btnSubmit.setText("Submit ticket");
        btnSubmit.setOnAction((ActionEvent event) -> { // Event handler to create a new ticket; Opens DB; Updates DB with fields entered by user
            
            // Create a new Ticket object
            Ticket ticket = new Ticket(IDnumber, (String)cbTicketStatus.getValue(), (String)tfSummary.getText(), (String)cbApp.getValue(), (String)cbAppStatus.getValue(), (String)cbPriority.getValue(),
                    (String)cbAssignee.getValue(), (String)tfAssigneeEmail.getText(), (String)taDescription.getText());
            
            try { // Try to update DB with Ticket instance
                dbPut(ticket);
                sendEmail(ticket.getAssigneeEmail());
            }
            catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            confirmation(primaryStage); // If succesfull, calls Screen2
        });

        Button btnList = new Button(); // Create button for Screen3
        btnList.setText("Ticket List");
        btnList.setOnAction((ActionEvent event) -> { // Event handler to call Screen3
            ticketList(primaryStage);
//            primaryStage.setOnCloseRequest(e -> Platform.exit()); // Didn't work to close main window
//            primaryStage.close(); // Didn't work to close main window
        });

        hBox.getChildren().addAll(btnSubmit, btnList); // Add buttons to HBox
        
        cbTicketStatus.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
        cbTicketStatus.getItems().addAll("Open", "Closed"); // Add available options to drop down menu
        cbTicketStatus.setValue("Open");

        cbApp.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
        cbApp.getItems().addAll("App1", "App2", "App3"); // Add available options to drop down menu
        cbApp.setValue("App1");

        cbAppStatus.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
        cbAppStatus.getItems().addAll("Open", "Closed"); // Add available options to drop down menu
        cbAppStatus.setValue("Open");

        cbPriority.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
        cbPriority.getItems().addAll("Low", "Medium", "High", "Desperate"); // Add available options to drop down menu
        cbPriority.setValue("Medium");

        cbAssignee.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
        cbAssignee.getItems().addAll("Jas", "Jason", "John"); // Add available options to drop down menu
        cbAssignee.setValue("John");

        tfSummary.setText(""); // Set default text fields and areas to empty; DOES THIS CLEAR BUG
        tfAssigneeEmail.setText("");
        taDescription.setText("");
        
        taDescription.setWrapText(true); // Allow text to wrap in description field
//    taDescription.setPrefRowCount(4);
    
        GridPane display = new GridPane(); // Create GridPane to hold display controls
        display.setAlignment(Pos.CENTER_LEFT);
        display.setPadding(new Insets(10, 10, 10, 10));
        display.setHgap(5);
        display.setVgap(5);

        // Place nodes in pane (node, column, row, column span, row span)
        display.add(new Label("IDnumber: "), 0, 0);
        display.add(new Label("" + IDnumber), 1, 0); // Add empty string to convert Integer IDnumber to String
        display.add(new Label("Ticket Status: "), 2, 0);
        display.add(cbTicketStatus, 3, 0);
        display.add(new Label("Summary: "), 0, 1);
        display.add(tfSummary, 1, 1, 3, 1);
        display.add(new Label("Application: "), 0, 2);
        display.add(cbApp, 1, 2);
        display.add(new Label("Application Status: "), 2, 2);
        display.add(cbAppStatus, 3, 2);
        display.add(new Label("Priority: "), 0, 3);
        display.add(cbPriority, 1, 3);
        display.add(new Label("Assignee: "), 0, 4);
        display.add(cbAssignee, 1, 4);
        display.add(new Label("Assignee email: "), 0, 5);
        display.add(tfAssigneeEmail, 1, 5, 3, 1);
        display.add(new Label("Description"), 0, 6);
        display.add(taDescription, 1, 6, 3, 1);
       
        BorderPane root = new BorderPane(); // Create BorderPane to hold Hbox and display of controls
        root.setBottom(hBox); // Set HBox buttons at bottom
        root.setCenter(display); // Set controls display in center
        Scene scene = new Scene(root, 1200, 400); // Create Scene

        primaryStage.setTitle("New Ticket");
        primaryStage.setScene(scene); //Set Scene to stage
        primaryStage.show(); // Show stage
    }

    public void displayTicketDetails(Stage primaryStage, Integer IDnumber) { // This code displays the gui for a ticket; Calls dbPut which opens DB and enters data to DB

        boolean error = false; // if number entered by user doesn't exist, then error switches to true
        ResultSet resultSet2;
        String cbTicketStatusValue = "";
        String tfSummaryValue = "";
        String cbApplicationValue = "";
        String cbApplicationStatusValue = "";
        String cbPriorityValue = "";
        String cbAssigneeValue = "";
        String tfAssigneeEmailValue = "";
        String taDescriptionValue = "";
        
        // Get details for IDnumber from database
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Load driver
            System.out.println("Driver loaded");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/team_assignment_table" , "root" , "4lU^L1vNaP"); // Create new connection
            System.out.println("Database connected");

            Statement statement = connection.createStatement(); // Create SQL statement

            System.out.println("resultSet2 completed successfully."); // for testing
            resultSet2 = statement.executeQuery("select * from ticket where IDnumber = " + IDnumber); // Get all ticket numbers for DB
            if (!resultSet2.isBeforeFirst()) { // if result set is empty, meaning the IDnumber the user entered doesn't exist then set error to true
                System.out.println("Ticket does not exist.");
                error(primaryStage); // Call error screen
                error = true;
            }
            else { // if result set is not empty, meaning the user entered an existing IDnumber then get the field values from resultSet2

                while (resultSet2.next()) {
                System.out.println(resultSet2.getString(1) + " " + resultSet2.getString(2) + " " + resultSet2.getString(3)); // for testing
                cbTicketStatusValue = resultSet2.getString(2);
                tfSummaryValue = resultSet2.getString(3);
                cbApplicationValue = resultSet2.getString(4);
                cbApplicationStatusValue = resultSet2.getString(5);
                cbPriorityValue = resultSet2.getString(6);
                cbAssigneeValue = resultSet2.getString(7);
                tfAssigneeEmailValue = resultSet2.getString(8);
                taDescriptionValue = resultSet2.getString(9);
                }
            }
        }
        catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }


        if (!error) { // if there was no error then display ticket gui with ticket detail fields populated
            HBox hBox = new HBox(5); // Create an HBox to hold buttons
            hBox.setAlignment(Pos.CENTER);
            Button btnSubmit = new Button(); // Submit button
            btnSubmit.setText("Submit ticket");
            btnSubmit.setOnAction((ActionEvent event) -> { // Event handler to create a new ticket; Opens DB; Updates DB with fields entered by user

                // Create a new Ticket object
                Ticket ticket = new Ticket(IDnumber, (String)cbTicketStatus.getValue(), (String)tfSummary.getText(), (String)cbApp.getValue(), (String)cbAppStatus.getValue(), (String)cbPriority.getValue(),
                        (String)cbAssignee.getValue(), (String)tfAssigneeEmail.getText(), (String)taDescription.getText());

                try { // Try to update DB with Ticket instance
                    dbUpdate(ticket);
                }
                catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                confirmation(primaryStage); // If succesfull, calls Screen2
            });

            Button btnList = new Button(); // Create button for Screen3
            btnList.setText("Ticket List");
            btnList.setOnAction((ActionEvent event) -> { // Event handler to call Screen3
                ticketList(primaryStage);
            });

            hBox.getChildren().addAll(btnSubmit, btnList); // Add buttons to HBox

            cbTicketStatus.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
            cbTicketStatus.getItems().addAll("Open", "Closed"); // Add available options to drop down menu
            cbTicketStatus.setValue(cbTicketStatusValue);

            tfSummary.setText(tfSummaryValue);
            cbApp.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
            cbApp.getItems().addAll("App1", "App2", "App3"); // Add available options to drop down menu
            cbApp.setValue(cbApplicationValue);

            cbAppStatus.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
            cbAppStatus.getItems().addAll("Open", "Closed"); // Add available options to drop down menu
            cbAppStatus.setValue(cbApplicationStatusValue);

            cbPriority.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
            cbPriority.getItems().addAll("Low", "Medium", "High", "Desperate"); // Add available options to drop down menu
            cbPriority.setValue(cbPriorityValue);

            cbAssignee.getItems().clear(); // Clear options so they don't appear when new ticket screen is called
            cbAssignee.getItems().addAll("Jas", "Jason", "John"); // Add available options to drop down menu
            cbAssignee.setValue(cbAssigneeValue);

            tfAssigneeEmail.setText(tfAssigneeEmailValue);
            taDescription.setWrapText(true); // Allow text to wrap in description field
            taDescription.setText(taDescriptionValue);

            GridPane display = new GridPane(); // Create GridPane to hold display controls
            display.setAlignment(Pos.CENTER_LEFT);
            display.setPadding(new Insets(10, 10, 10, 10));
            display.setHgap(5);
            display.setVgap(5);

            // Place nodes in pane (node, column, row, column span, row span)
            display.add(new Label("IDnumber: "), 0, 0);
            display.add(new Label("" + IDnumber), 1, 0); // Add empty string to convert Integer IDnumber to String
            display.add(new Label("Ticket Status: "), 2, 0);
            display.add(cbTicketStatus, 3, 0);
            display.add(new Label("Summary: "), 0, 1);
            display.add(tfSummary, 1, 1, 3, 1);
            display.add(new Label("Application: "), 0, 2);
            display.add(cbApp, 1, 2);
            display.add(new Label("Application Status: "), 2, 2);
            display.add(cbAppStatus, 3, 2);
            display.add(new Label("Priority: "), 0, 3);
            display.add(cbPriority, 1, 3);
            display.add(new Label("Assignee: "), 0, 4);
            display.add(cbAssignee, 1, 4);
            display.add(new Label("Assignee email: "), 0, 5);
            display.add(tfAssigneeEmail, 1, 5, 3, 1);
            display.add(new Label("Description"), 0, 6);
            display.add(taDescription, 1, 6, 3, 1);

            BorderPane root = new BorderPane(); // Create BorderPane to hold Hbox and display of controls
            root.setBottom(hBox); // Set HBox buttons at bottom
            root.setCenter(display); // Set controls display in center
            Scene scene = new Scene(root, 1200, 400); // Create Scene

            primaryStage.setTitle("New Ticket");
            primaryStage.setScene(scene); //Set Scene to stage
            primaryStage.show(); // Show stage
        }
    }

    class Ticket { // Ticket class holds fields entered by user for entered ticket
        private Integer IDnumber;
        private String ticketStatus;
        private String summary;
        private String app;
        private String appStatus;
        private String priority;
        private String assignee;
        private String assigneeEmail;
        private String description;
        
        Ticket() { // Default constructor
            
        }
        
        // Ticket constructor with all fields
        Ticket (Integer newIDnumber, String newTicketStatus, String newSummary, String newApplication,
                String newApplicationStatus, String newPriority, String newAssignee, String newAssigneeEmail, String newDescription) {
            IDnumber = newIDnumber;
            ticketStatus = newTicketStatus;
            summary = newSummary;
            app = newApplication;
            appStatus = newApplicationStatus;
            priority = newPriority;
            assignee = newAssignee;
            assigneeEmail = newAssigneeEmail;
            description = newDescription;
        }

        // Getters
        public Integer getIDnumber() {
            return IDnumber;
        }

        public String getTicketStatus() {
            return ticketStatus;
        }

        public String getSummary() {
            return summary;
        }

        public String getApplication() {
            return app;
        }

        public String getApplicationStatus() {
            return appStatus;
        }

        public String getPriority() {
            return priority;
        }

        public String getAssignee() {
            return assignee;
        }

        public String getAssigneeEmail() {
            return assigneeEmail;
        }

        public String getDescription() {
            return description;
        }
    }
    
    public void dbPut(Ticket ticket) throws SQLException, ClassNotFoundException { // Open DB and insert info entered by user
        ArrayList<Integer> IDnumberList = new ArrayList<>(); // ArrayList to hold IDnumbers from database

        Class.forName("com.mysql.jdbc.Driver"); // Load driver
        System.out.println("Driver loaded");
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/team_assignment_table" , "root" , "4lU^L1vNaP"); // Create new connection
        System.out.println("Database connected");
        
        Statement statement = connection.createStatement(); // Create SQL statement

        // Code to create a unique IDnumber
        ResultSet resultSet1 = statement.executeQuery("select IDnumber from ticket"); // Get all ticket numbers for DB
        while (resultSet1.next()) {
            IDnumberList.add(Integer.valueOf(resultSet1.getString(1))); // Add each IDnumber from database into ArrayList IDnumberList
            ticket.IDnumber = Collections.max(IDnumberList) + 1; // Find max IDnumber and increment by 1
            System.out.println(resultSet1.getString(1)); // For testing
        }

        // Update DB
        statement.executeUpdate("insert into ticket (IDnumber, ticketStatus, summary, application, applicationStatus, priority, assignee, assigneeEmail, description)"
                + "values (" + ticket.IDnumber + ", '" + ticket.ticketStatus + "', '" + ticket.summary + "', '" + ticket.app + "', '"
                + ticket.appStatus + "', '" + ticket.priority + "', '" + ticket.assignee + "', '" + ticket.assigneeEmail + "', '" + ticket.description + "')");

        // For testing; makes sure DB is being updated
        ResultSet resultSet = statement.executeQuery("select IDnumber, ticketStatus, summary from ticket where priority = 'High'");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
        }

        connection.close(); // Close connection
    }
    
    public void dbUpdate(Ticket ticket) throws SQLException, ClassNotFoundException { // Open DB and insert NEW info entered by user

        Class.forName("com.mysql.jdbc.Driver"); // Load driver
        System.out.println("Driver loaded");
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/team_assignment_table" , "root" , "4lU^L1vNaP"); // Create new connection
        System.out.println("Database connected");
        
        Statement statement = connection.createStatement(); // Create SQL statement

        statement.executeUpdate("update ticket set IDnumber = " + ticket.IDnumber + ", ticketStatus = '" + ticket.ticketStatus + // SQL statement
                "', summary = '" + ticket.summary + "', application = '" + ticket.app + "', applicationStatus = '" + ticket.appStatus + 
                "', priority = '" + ticket.priority + "', assignee = '" + ticket.assignee + "', assigneeEmail = '" + ticket.assigneeEmail + 
                "', description = '" + ticket.description + "' where IDnumber = " + ticket.IDnumber);

        connection.close(); // Close connection
    }

    public static void main(String[] args) { // Launch program
        launch(args);
    }
    
   public void sendEmail(String assigneeEmail){
    
        String to = assigneeEmail;
        String from = "TeamAassignment@gmail.com";
        final String username = "xxxxxxxxx";//change accordingly
        final String password = "xxxxxxxxx";//change accordingly
        String host = "smtp.gmail.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }}
	);
         
        try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("Ticket Assignment");

         // Now set the actual message
         message.setText("A ticket has been assigned to you");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException ex) {
         ex.printStackTrace();
      }
   }
   
}

