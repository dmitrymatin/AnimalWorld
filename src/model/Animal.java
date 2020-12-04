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
        return "name = " + name + " mass = " + mass + " is alive: " + isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}

