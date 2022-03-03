//package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
//import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

//import impresario.IView;



//==============================================================
public class Book extends EntityBase {
    private static final String myTableName = "Book";
    public String[] fields = {"author", "bookTitle", "pubYear", "status"};

    // GUI Components
    private String statusState = "";

    // constructor for this class
    public Book(String bookId)
            throws InvalidPrimaryKeyException {
        super(myTableName);

        String query = String.format("SELECT * FROM %s WHERE (bookId = %s)",
                myTableName, bookId);

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();


            if (size != 1) {
                throw new InvalidPrimaryKeyException("Too many books found Matching this id : "
                        + bookId + " found.");
            } else {
                // copy all the retrieved data into persistent state
                Properties retrievedBookData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements()) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        else {
            throw new InvalidPrimaryKeyException("No book matching id : "
                    + bookId + " found.");
        }
    }

    public Book(Properties bookInfo) {
        super(myTableName);

        persistentState = new Properties();
        Enumeration allKeys = bookInfo.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = bookInfo.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public Book() {
        super(myTableName);

        persistentState = new Properties();
    }


    /**
     * Called via the IView relationship
     */
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    private void processNewBook(Properties bookInfo) {
        persistentState = new Properties();
        Enumeration allKeys = bookInfo.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = bookInfo.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }
    public void update()
    {
        try
        {
            if (persistentState.getProperty("bookId") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("bookId",
                        persistentState.getProperty("bookId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                statusState = "book data for account number : " + persistentState.getProperty("bookId") + " updated successfully in database!";
            }
            else
            {
                Integer bookId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("AccountNumber", "" + bookId.intValue());
                statusState = "Account data for new account : " +  persistentState.getProperty("AccountNumber")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            statusState = "Error in installing account data in database!";
        }
    }


    public String toString() {
        return "Title: " + persistentState.getProperty("bookTitle") + "; Author:" +
                persistentState.getProperty("author") + "; Year: " +
                persistentState.getProperty("pubYear");
    }
    public static int compare(Book a, Book b)
    {
        String aNum = (String)a.getState("bookId");
        String bNum = (String)b.getState("bookId");

        return aNum.compareTo(bNum);
    }

    @Override
    public Object getState(String key) {
        if (key.equals("status"))
            return statusState;

        return persistentState.getProperty(key);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {

    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}