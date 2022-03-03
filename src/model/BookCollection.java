package model;
//import impresario.IView;
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

    private Vector<Book> bookList;

    // constructor for this class
    public BookCollection()
    {
        super(myTableName);
        bookList = new Vector<Book>();
    }

    public void findBooksOlderThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear <>> " + year + ") ORDER BY author ASC";
        queryHelper(query);
    }

    public void findBooksNewerThanDate(String year) {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > " + year + ") ORDER BY author ASC";
        queryHelper(query);
    }

    public void findBooksWithTitleLike(String title) {
        String query = "SELECT * FROM " + myTableName + " WHERE title LIKE '%" + title + "%' ORDER BY author ASC";
        queryHelper(query);
    }

    public void findBooksWithAuthorLike(String author) {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%' ORDER BY author ASC";
        queryHelper(query);
    }

    public void queryHelper(String query) {
        bookList = new Vector<Book>();

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {


            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextBookData = (Properties) allDataRetrieved.elementAt(cnt);

                Book bk = new Book(nextBookData);

                if (bk != null) {
                    addBook(bk);
                }
            }

        }
    }
        private void addBook(Book b)
        {
            //users.add(u);
            int index = findIndexToAdd(b);
            bookList.insertElementAt(b, index); // To build up a collection sorted on some key
        }
        private int findIndexToAdd(Book a)
        {
            //users.add(u);
            int low=0;
            int high = bookList.size()-1;
            int middle;

            while (low <=high)
            {
                middle = (low+high)/2;

                Book midSession = (Book)bookList.elementAt(middle);

                int result = Book.compare(a, midSession);

                if (result ==0)
                {
                    return middle;
                }
                else if (result<0)
                {
                    high=middle-1;
                }
                else
                {
                    low=middle+1;
                }


            }
            return low;
        }

        public void display() {
            if (bookList != null) {
                for (int cnt = 0; cnt < bookList.size(); cnt++) {
                    Book b = bookList.get(cnt);
                    System.out.println("--------");
                    System.out.println(b.toString());
                }
            }
        }

//    private Vector doQuery(String query) {
//        try {
//            Vector allDataRetrieved = getSelectQueryResult(query);
//            if (allDataRetrieved != null) {
//                bookList = new Vector<>();
//                for (int index = 0; index < allDataRetrieved.size(); index++) {
//                    Properties data = (Properties) allDataRetrieved.elementAt(index);
//                    Book book = new Book(data);
//                    bookList.add(book);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Exception: " + e);
//        }
//        return bookList;
//    }

    private void setDependencies() {
        Properties dependencies = new Properties();
        dependencies.setProperty("CancelSearchBook", "SearchBookView");

    }
    @Override
    public Object getState(String key)
    {
        if (key.equals("books"))
            return bookList;
        else
        if (key.equals("patronList"))
            return this;
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value) {

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