package misc;

import database.BasicDataSet;
import database.DataSet;
import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaResult {
    /* Class attributes */
    private final SimpleStringProperty id, permit, serial, date, fname, name, source, aclass, type, alCon, year, ph;
    private final DataSet sourceMap = new BasicDataSet();

    /* Class constructors */
    public ColaResult(String id, String permit, String serial, String date, String fname, String name, String source, String aclass, String type, String alCon, String year, String ph) {
        if (id.equals("")) this.id = new SimpleStringProperty("N/A");
        else this.id = new SimpleStringProperty(id);
        if (permit.equals("")) this.permit = new SimpleStringProperty("N/A");
        else this.permit = new SimpleStringProperty(permit);
        if (serial.equals("")) this.serial = new SimpleStringProperty("N/A");
        else this.serial = new SimpleStringProperty(serial);
        if (date.equals("")) this.date = new SimpleStringProperty("N/A");
        else this.date = new SimpleStringProperty(date);
        if (fname.equals("")) this.fname = new SimpleStringProperty("N/A");
        else this.fname = new SimpleStringProperty(fname);
        if (name.equals("")) this.name = new SimpleStringProperty("N/A");
        else this.name = new SimpleStringProperty(name);
        if (source.equals("")) this.source = new SimpleStringProperty("N/A");
        else this.source = new SimpleStringProperty(source);
        if (aclass.equals("")) this.aclass = new SimpleStringProperty("N/A");
        else this.aclass = new SimpleStringProperty(aclass);
        if (type.equals("")) this.type = new SimpleStringProperty("N/A");
        else this.type = new SimpleStringProperty(type);
        if (alCon.equals("")) this.alCon = new SimpleStringProperty("N/A");
        else this.alCon = new SimpleStringProperty(alCon);
        if (year.equals("")) this.year = new SimpleStringProperty("N/A");
        else this.year = new SimpleStringProperty(year);
        if (ph.equals("")) this.ph = new SimpleStringProperty("N/A");
        else this.ph = new SimpleStringProperty(ph);
    }

    /* Class setters and getters */
    public String getId() {
        return this.id.get();
    }
    public String getSource() {
        return this.source.get();
    }
    public String getType() {
        return this.type.get();
    }
    public String getName() {
        return this.name.get();
    }
    public void setId(String i) {
        this.id.set(i);
    }
    public void setSource(String s) {
        this.id.set(s);
    }
    public void setType(String t) {
        this.id.set(t);
    }
    public void setName(String n) {
        this.id.set(n);
    }

    /* Getters for additional fields to be displayed in the popup */
    public String getPermit() { return this.permit.get(); }
    public String getSerial() { return this.serial.get(); }
    public String getDate() { return this.date.get(); }
    public String getFname() { return this.fname.get(); }
    public String getAclass() { return this.aclass.get(); }
    public String getAlCon() { return this.alCon.get(); }
    public String getYear() { return this.year.get(); }
    public String getPh() { return this.ph.get(); }

    /* Setup the source states for the end-user */
    public void initState() {
        this.sourceMap.addField("00", "American");
        this.sourceMap.addField("01", "California");
        this.sourceMap.addField("02", "New York");
        this.sourceMap.addField("00", "American");
        this.sourceMap.addField("00", "American");
        this.sourceMap.addField("00", "American");
        this.sourceMap.addField("00", "American");
        this.sourceMap.addField("00", "American");
        this.sourceMap.addField("00", "American");
        this.sourceMap.addField("00", "American");

    }
}
