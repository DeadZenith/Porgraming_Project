package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

public class PatronTableModel {
    private final SimpleStringProperty patronId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty status;


    //----------------------------------------------------------------------------
    public PatronTableModel(Vector<String> patronData)
    {
        patronId =  new SimpleStringProperty(patronData.elementAt(0));
        name =  new SimpleStringProperty(patronData.elementAt(1));
        address =  new SimpleStringProperty(patronData.elementAt(2));
        city = new SimpleStringProperty(patronData.elementAt(3));
        stateCode =  new SimpleStringProperty(patronData.elementAt(4));
        zip = new SimpleStringProperty(patronData.elementAt(5));
        email = new SimpleStringProperty(patronData.elementAt(6));
        dateOfBirth = new SimpleStringProperty(patronData.elementAt(7));
        status = new SimpleStringProperty(patronData.elementAt(8));

    }

    //----------------------------------------------------------------------------
    public String getPatronId() {
        return patronId.get();
    }

    //----------------------------------------------------------------------------
    public void setPatronId(String number) { patronId.set(number); }

    //----------------------------------------------------------------------------
    public String getName() {
        return name.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String nam) {
        name.set(nam);
    }

    //----------------------------------------------------------------------------
    public String getAddress() {
        return address.get();
    }

    //----------------------------------------------------------------------------
    public void setAddress(String adr) {
        address.set(adr);
    }

    //----------------------------------------------------------------------------
    public String getZip() { return zip.get(); }

    //----------------------------------------------------------------------------
    public void setZip(String zp)
    {
        zip.set(zp);
    }
    //----------------------------------------------------------------------------
    public String getEmail() { return email.get(); }

    //----------------------------------------------------------------------------
    public void setEmail(String eam)
    {
        email.set(eam);
    }
    //----------------------------------------------------------------------------

    public String getDOB() { return dateOfBirth.get(); }

    //----------------------------------------------------------------------------
    public void setDOB(String dob)
    {
        stateCode.set(dob);
    }
    //----------------------------------------------------------------------------

    public String getStatus() { return status.get(); }

    //----------------------------------------------------------------------------
    public void setStatus(String stat)
    {
        status.set(stat);
    }
}