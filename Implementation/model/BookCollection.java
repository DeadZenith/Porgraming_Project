package model;
import impresario.IView;
//import javafx.scene.Scene;
import model.EntityBase;


import java.util.Properties;
import java.util.Vector;

// project imports

/**
 * The class containing the BookCollection for the Assignment 1 application
 */
//==============================================================
public class BookCollection extends EntityBase {
    private static final String myTableName = "Book";
    private static final String queryTemplate = "SELECT * FROM " + myTableName + " WHERE ";

    private Vector<Book> bookList;

    // constructor for this class
    public BookCollection()
    {
        super(myTableName);
        bookList = new Vector<>();
    }

    public BookCollection(String title)
    {
        super(myTableName);
        setDependencies();
        findBooksWithTitleLike(title);
    }

    public Vector findBooksOlderThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear <>> " + year + ") ORDER BY author ASC";
        return doQuery(query);
    }

    public Vector findBooksNewerThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > " + year + ") ORDER BY author ASC";
        return doQuery(query);
    }

    public Vector findBooksWithTitleLike(String title) {
        String query = "SELECT * FROM " + myTableName + " WHERE title LIKE '%" + title + "%' ORDER BY author ASC";
        return doQuery(query);
    }

    public Vector findBooksWithAuthorLike(String author) {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%' ORDER BY author ASC";
        return doQuery(query);
    }

    private Vector doQuery(String query) {
        try {
            Vector allDataRetrieved = getSelectQueryResult(query);
            if (allDataRetrieved != null) {
                bookList = new Vector<>();
                for (int index = 0; index < allDataRetrieved.size(); index++) {
                    Properties data = (Properties) allDataRetrieved.elementAt(index);
                    Book book = new Book(data);
                    bookList.add(book);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return bookList;
    }

    private void setDependencies() {
        Properties dependencies = new Properties();
        dependencies.setProperty("CancelSearchBook", "SearchBookView");

        myRegistry.setDependencies(dependencies);
    }
    public Object getState(String key) {
        if (key.equals("books"))
            return bookList;
        else if (key.equals("bookList"))
            return this;
        return null;
    }

    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Called via the IView relationship
     */
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}