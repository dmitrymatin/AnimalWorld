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
