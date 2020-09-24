package model;

public abstract class Animal {
    protected String name;
    protected float m;
    protected boolean isAlive;

    protected Animal(String name, float m) {
        this.name = name;
        this.m = m;
        isAlive = true;
    }

    public void kill() {
        if (isAlive)
            isAlive = false;
    }

    public String GetInfo() {
        return "name = " + name + " mass = " + m + " is alive: " + isAlive;
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}

