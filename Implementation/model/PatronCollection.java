package model;

import impresario.IView;

import java.util.Properties;
import java.util.Vector;

public class PatronCollection extends EntityBase
{
    private static final String myTableName = "Patron";

    private Vector<Patron> patronList;
    // GUI Components

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
    public Object getState(String key)
    {
        if (key.equals("patrons"))
            return patronList;
        else
        if (key.equals("patronList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key,this);
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