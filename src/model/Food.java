package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"name", "mass"})
public abstract class Food implements Serializable {
    protected String name;
    protected float mass;

    protected Food(String name, float mass) throws IllegalArgumentException {
        if (mass <= 0)
            throw new IllegalArgumentException("Cannot create entity of type "
                    + this.getClass().getName() + " with negative mass");
        this.name = name;
        this.mass = mass;
    }

    public String getInfo() {
        return name + " массой " + mass;
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
