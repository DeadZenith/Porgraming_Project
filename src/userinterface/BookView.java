// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class BookView extends View
{

    // GUI components
    protected TextField authorName;
    protected TextField titleField;
    protected TextField publicationYear;
    protected ComboBox actStatus;

    protected Button submitButton;
    protected Button DoneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookView(IModel account)
    {
        super(account, "Bookview");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("UpdateStatusMessage", this);
    }

    // Overide the paint method to ensure we can set the focus when made visible
    //-------------------------------------------------------------

    // Create the labels and fields
    //------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Books!! ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }
    // Create the main data entry fields
    //-------------------------------------------------------------
    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text authorLab = new Text(" Author Name: ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        authorLab.setFont(myFont);
        authorLab.setWrappingWidth(150);
        authorLab.setTextAlignment(TextAlignment.RIGHT);
        grid.add(authorLab, 0, 1);

        authorName = new TextField();
        authorName.setEditable(true);
        grid.add(authorName, 1, 1);

        Text bookTitle = new Text(" Book Title : ");
        bookTitle.setFont(myFont);
        bookTitle.setWrappingWidth(150);
        bookTitle.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bookTitle, 0, 2);

        titleField = new TextField();
        titleField.setEditable(false);
        grid.add(titleField, 1, 2);

        Text pubLabel = new Text(" Publication Year : ");
        pubLabel.setFont(myFont);
        pubLabel.setWrappingWidth(150);
        pubLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubLabel, 0, 3);

        publicationYear = new TextField();
        publicationYear.setEditable(false);
        grid.add(pubLabel, 1, 3);

        Text statLab = new Text(" Status : ");
        statLab.setFont(myFont);
        statLab.setWrappingWidth(150);
        statLab.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statLab, 0, 4);


        // Create a combo box
        actStatus = new ComboBox();
        actStatus.getItems().addAll(
                "Active","Inactive"
        );
        grid.add(actStatus, 1, 4);



        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.BOTTOM_RIGHT);
        submitButton = new Button("SUBMIT");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("AccountCancelled", null);
            }
        });
        doneCont.getChildren().add(submitButton);
        HBox soCont = new HBox(10);
        doneCont.setAlignment(Pos.BOTTOM_LEFT);
        DoneButton = new Button("Done");
        DoneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        DoneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("AccountCancelled", null);
            }
        });
        soCont.getChildren().add(DoneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);
        vbox.getChildren().add(soCont);

        return vbox;
    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        authorName.setText((String)myModel.getState("Author Name"));
        titleField.setText((String)myModel.getState(""));
        publicationYear.setText((String)myModel.getState("Balance"));
        actStatus.setValue((String)myModel.getState("Active"));
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("ServiceCharge") == true)
        {
            String val = (String)value;
            //serviceCharge.setText(val);
            displayMessage("Service Charge Imposed: $ " + val);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

}

//---------------------------------------------------------------
//	Revision History:
//


