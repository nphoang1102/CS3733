package misc;

import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaResult {
    /* Class attributes */
    private final SimpleStringProperty id, permit, serial, date, fname, name, source, aclass, type;

    /* Class constructors */
    public ColaResult(String id, String permit, String serial, String date, String fname, String name, String source, String aclass, String type) {
        this.id = new SimpleStringProperty(id);
        this.permit = new SimpleStringProperty(permit);
        this.serial = new SimpleStringProperty(serial);
        this.date = new SimpleStringProperty(date);
        this.fname = new SimpleStringProperty(fname);
        this.name = new SimpleStringProperty(name);
        this.source = new SimpleStringProperty(source);
        this.aclass = new SimpleStringProperty(aclass);
        this.type = new SimpleStringProperty(type);
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
}
