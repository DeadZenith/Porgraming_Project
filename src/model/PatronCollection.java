package model;

//import impresario.IView;

import java.util.Properties;
import java.util.Vector;

public class PatronCollection extends EntityBase
{
    private static final String myTableName = "Patron";

    private Vector<Patron> patronList;

    // constructor for this class
    //----------------------------------------------------------
    public PatronCollection() {
        super(myTableName);
        patronList = new Vector<>(); // new Vector<Patron>();
    }

    public Vector findPatronsOlderThan(String date) {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth > " + date + ") ORDER BY name ASC;";
        return doQuery(query);
    }

    public Vector findPatronsYoungerThan(String date) {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < " + date + ") ORDER BY name ASC;";
        return doQuery(query);
    }

    public Vector findPatronsAtZipCode(String zip) {
        String query = "SELECT * FROM " + myTableName + " WHERE (zip=" + zip + ") ORDER BY name ASC;";
        return doQuery(query);
    }

    public Vector findPatronsWithNameLike(String name) {

        String query = "SELECT * FROM " + myTableName + " WHERE (name LIKE '%" + name + "%') ORDER BY name ASC;";
        return doQuery(query);
    }

    private Vector doQuery(String query) {
        try {
            Vector allDataRetrieved = getSelectQueryResult(query);
            if (allDataRetrieved != null) {
//                patronList = new Vector<Patron>();
                for (int index = 0; index < allDataRetrieved.size(); index++) {
                    Properties data = (Properties) allDataRetrieved.elementAt(index);
                    Patron patron = new Patron(data);
                    patronList.add(patron);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return patronList;
    }
    //----------------------------------------------------------
    public void queryHelper(String query) {
        patronList = new Vector<Patron>();

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {


            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextPatron = (Properties) allDataRetrieved.elementAt(cnt);

                Patron pc = new Patron(nextPatron);

                if (pc != null) {
                    addPatron(pc);
                }
            }

        }
    }
    private void addPatron(Patron p)
    {
        //users.add(u);
        int index = findIndexToAdd(p);
        patronList.insertElementAt(p, index); // To build up a collection sorted on some key
    }
    private int findIndexToAdd(Patron p)
    {
        //users.add(u);
        int low=0;
        int high = patronList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Patron midSession = patronList.elementAt(middle);

            int result = Patron.compare(p, midSession);

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

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("patrons"))
            return patronList;
        else
        if (key.equals("patronList"))
            return this;
        return null;
    }
    public void display() {
        if (patronList != null) {
            for (int cnt = 0; cnt < patronList.size(); cnt++) {
                Patron p = patronList.get(cnt);
                System.out.println("--------");
                System.out.println(p.toString());
            }
        }
    }

    @Override
    public void stateChangeRequest(String key, Object value) {

    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}