package model;

import java.io.Serializable;
import java.util.ResourceBundle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"name", "mass"})
public abstract class Food implements Serializable {
    protected static transient final ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle");
    protected String name;
    protected float mass;

    protected Food(String name, float mass) throws IllegalArgumentException {
        if (mass <= 0)
            throw new IllegalArgumentException(rb.getString("ERROR_NEG_MASS")
                    + " " + this.getClass().getName());
        this.name = name;
        this.mass = mass;
    }

    public String getInfo() {
        return name + ", " + rb.getString("MASS_VALUE") + ": " + mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}
