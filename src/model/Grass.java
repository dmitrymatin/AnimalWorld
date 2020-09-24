package model;

public class Grass {

    private String name;
    private float m;

    public Grass(String name, float m) {
        this.name = name;
        this.m = m;
    }

    public String GetInfo() {
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
