// specify the package
package model;

// system imports

import exception.InvalidPrimaryKeyException;
import impresario.IView;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
//==============================================================
public class Patron extends EntityBase
{
    private static final String myTableName = "Patron";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Patron(String patronId)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one patron. Else error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Too many Patrons matching this id : "
                        + patronId + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedPatronData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedPatronData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedPatronData.getProperty(nextKey);
                    // patronId = Integer.parseInt(retrievedPatronData.getProperty("patronId"));

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // No matching patron found, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No matching id : "
                    + patronId + " found.");
        }
    }

    //----------------------------------------------------------
    public Patron(Properties patronInfo)
    {
        super(myTableName);

        persistentState = new Properties();
        Enumeration allKeys = patronInfo.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = patronInfo.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }
    //-----------------------------------------------------------------------------------

    public String toString()
    {
        return "Name: " + persistentState.getProperty("name") +  "/n Address: "  +
                persistentState.getProperty("address")  + "/n City: "  +
                persistentState.getProperty("City") + "/n State Code: "  +
                persistentState.getProperty("stateCode") + "/n Zip: "  +
                persistentState.getProperty("zip") + "/n Email: "  +
                persistentState.getProperty("email") + "/n DOB: "  +
                persistentState.getProperty("dateOfBirth") + "/n Status: "  +
                persistentState.getProperty("status");
    }

    //-----------------------------------------------------------------------------------
    @Override
    public Object getState(String key) {
        if (key.equals("status"))
            return updateStatusMessage;

        return persistentState.getProperty("status");
    }
    //-----------------------------------------------------------------------------------
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





		