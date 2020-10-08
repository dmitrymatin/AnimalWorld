package model;

public class Grass extends Food {

    public Grass(String name, float m) {
        super(name, m);
    }

    public String getInfo() {
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
