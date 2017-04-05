package misc;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Hoang Nguyen on 4/4/2017.
 */
public class ColaResult {
    /* Class attributes */
    private final SimpleStringProperty id;
    private final SimpleStringProperty source;
    private final SimpleStringProperty type;
    private final SimpleStringProperty name;

    /* Class constructors */
    public ColaResult(String id, String source, String type, String name) {
        this.id = new SimpleStringProperty(id);
        this.source = new SimpleStringProperty(source);
        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
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
}
