//package userinterface;
//
//import impresario.IModel;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
////import model.Account;
////import model.AccountCollection;
//import model.Book;
//import model.PatronCollection;
//
//import java.util.Enumeration;
//import java.util.Vector;
//
//public class PatronCollectionView extends View{
//    // GUI components
//    protected TableView<PatronCollection> tableOfPatrons;
//    protected Button doneButton;
//
//    // For showing error message
//    protected MessageView statusLog;
//
//    // constructor for this class -- takes a model object
//    //----------------------------------------------------------
//    public PatronCollectionView(IModel book)
//    {
//        super(book, "PatronCollectionView");
//
//        // create a container for showing the contents
//        VBox container = new VBox(10);
//        container.setPadding(new Insets(15, 5, 5, 5));
//
//        // Add a title for this panel
//        container.getChildren().add(createTitle());
//
//        // create our GUI components, add them to this Container
//        container.getChildren().add(createFormContent());
//
//        container.getChildren().add(createStatusLog("             "));
//
//        getChildren().add(container);
//
//        populateFields();
//
//
//        myModel.subscribe("TransactionError", this);
//    }
//    //--------------------------------------------------------------------------
//    protected void populateFields()
//    {
//        getEntryTableModelValues();
//    }
//
//    protected void getEntryTableModelValues()
//    {
//
//        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
//        try
//        {
//            PatronCollection patronCollection = (PatronCollection) myModel.getState("patronList");
//
//            Vector entryList = (Vector)patronCollection.getState("Books");
//            Enumeration entries = entryList.elements();
//
//            while (entries.hasMoreElements() == true)
//            {
//                Book nextBook = (Book)entries.nextElement();
//                System.out.println("Inserting Nexgt Book: " + nextBook);
//                Vector<String> view = nextBook.getEntryListView();
//
//                // add this list entry to the list
//                BookTableModel nextTableRowData = new BookTableModel(view);
//                tableData.add(nextTableRowData);
//
//            }
//
//          //  tableOfPatrons.setItems(tableData);
//        }
//        catch (Exception e) {//SQLException e) {
//            // Need to handle this exception
//        }
//    }
//
//    // Create the title container
//    //-------------------------------------------------------------
//    private Node createTitle()
//    {
//        HBox container = new HBox();
//        container.setAlignment(Pos.CENTER);
//
//        Text titleText = new Text(" Library System ");
//        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//        titleText.setWrappingWidth(300);
//        titleText.setTextAlignment(TextAlignment.CENTER);
//        titleText.setFill(Color.DARKGREEN);
//        container.getChildren().add(titleText);
//
//        return container;
//    }
//
//    // Create the main form content
//    //-------------------------------------------------------------
//    private VBox createFormContent()
//    {
//        VBox vbox = new VBox(10);
//
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        Text prompt = new Text("Book List");
//        prompt.setWrappingWidth(350);
//        prompt.setTextAlignment(TextAlignment.CENTER);
//        prompt.setFill(Color.DARKGREEN);
//        grid.add(prompt, 0, 0, 2, 1);
//
//       // tableOfPatrons = new TableView<patronTableModel>();
//        tableOfPatrons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//
//        TableColumn patronIdCol = new TableColumn("Patron Id") ;
//        patronIdCol.setMinWidth(100);
//        patronIdCol.setCellValueFactory(
//                new PropertyValueFactory<patronTableModel, String>("patronId"));
//
//        TableColumn authCol = new TableColumn("Author") ;
//        authCol.setMinWidth(100);
//        authCol.setCellValueFactory(
//                new PropertyValueFactory<patronTableModel, String>("author"));
//
//        TableColumn titleColumn = new TableColumn("Title") ;
//        titleColumn.setMinWidth(100);
//        titleColumn.setCellValueFactory(
//                new PropertyValueFactory<BookTableModel, String>("title"));
//
//        TableColumn publicationYearColumn = new TableColumn("Publication Year") ;
//        publicationYearColumn.setMinWidth(100);
//        publicationYearColumn.setCellValueFactory(
//                new PropertyValueFactory<BookTableModel, String>("publicationYear"));
//
//        TableColumn statusColumn = new TableColumn("Status") ;
//        statusColumn.setMinWidth(100);
//        statusColumn.setCellValueFactory(
//                new PropertyValueFactory<BookTableModel, String>("status"));
//
//        tableOfBooks.getColumns().addAll(bookIdColumn,
//                authorColumn, titleColumn, publicationYearColumn, statusColumn);
//
//        tableOfBooks.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event)
//            {
//                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
//                    //processAccountSelected();
//                }
//            }
//        });
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setPrefSize(115, 150);
//        scrollPane.setContent(tableOfBooks);
//
//        //TODO need to look into switching this
//        doneButton = new Button("Back");
//        doneButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                /**
//                 * Process the Cancel button.
//                 * The ultimate result of this action is that the transaction will tell the teller to
//                 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
//                 * It simply tells its model (controller) that the transaction was canceled, and leaves it
//                 * to the model to decide to tell the teller to do the switch back.
//                 */
//                //----------------------------------------------------------
//                clearErrorMessage();
//                myModel.stateChangeRequest("CancelTransaction", null);
//            }
//        });
//
//        HBox btnContainer = new HBox(100);
//        btnContainer.setAlignment(Pos.CENTER);
//        btnContainer.getChildren().add(doneButton);
//
//        vbox.getChildren().add(grid);
//        vbox.getChildren().add(scrollPane);
//        vbox.getChildren().add(btnContainer);
//
//        return vbox;
//    }
//
//
//
//    //--------------------------------------------------------------------------
//    public void updateState(String key, Object value)
//    {
//    }
//
//    //--------------------------------------------------------------------------
//    /*protected void processAccountSelected()
//    {
//        BookTableModel selectedItem = tableOfBooks.getSelectionModel().getSelectedItem();
//        if(selectedItem != null)
//        {
//            String selectedAcctNumber = selectedItem.getAccountNumber();
//            myModel.stateChangeRequest("AccountSelected", selectedAcctNumber);
//        }
//    }*/
//
//    //--------------------------------------------------------------------------
//    protected MessageView createStatusLog(String initialMessage)
//    {
//        statusLog = new MessageView(initialMessage);
//
//        return statusLog;
//    }
//
//
//    /**
//     * Display info message
//     */
//    //----------------------------------------------------------
//    public void displayMessage(String message)
//    {
//        statusLog.displayMessage(message);
//    }
//
//    /**
//     * Clear error message
//     */
//    //----------------------------------------------------------
//    public void clearErrorMessage()
//    {
//        statusLog.clearErrorMessage();
//    }
//
//
//}