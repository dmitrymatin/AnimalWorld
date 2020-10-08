package model;

public abstract class Food {
    protected String name;
    protected float m;

    protected Food(String name, float m) throws IllegalArgumentException {
        if (m <= 0)
            throw new IllegalArgumentException("Cannot create entity of type"
                    + this.getClass() + " with negative mass");
        this.name = name;
        this.m = m;
    }

    public String getInfo(){
        return "name = " + name + " mass = " + m;
    }
}
