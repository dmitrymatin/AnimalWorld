package model;

public abstract class Animal extends Food {

    protected boolean isAlive;

    public Animal(String name, float m) {
        super(name, m);
        this.isAlive = true;
    }

    public abstract void seeFood(Food food); // former seePrey
    protected abstract void eat(Food food);

    public void kill() throws IllegalStateException {
        if (!isAlive)
            throw new IllegalStateException("Cannot kill a dead animal");
        isAlive = false;
   }

    public String getInfo() {
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

