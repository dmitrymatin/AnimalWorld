package model;

import java.io.Serializable;

public abstract class Food implements Serializable {
    private static int objectCount = 0;
    private final int id;

    protected String name;
    protected float m;

    protected Food(String name, float m) throws IllegalArgumentException {
        if (m <= 0)
            throw new IllegalArgumentException("Cannot create entity of type "
                    + this.getClass().getName() + " with negative mass");
        this.name = name;
        this.m = m;

        this.id = ++objectCount;
    }

    public String getInfo() {
        return "name = " + name + " mass = " + m + " [" + id + "]";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getM() {
        return m;
    }

    public void setM(float m) {
        this.m = m;
    }
}
