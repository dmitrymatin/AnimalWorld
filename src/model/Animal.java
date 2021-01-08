package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"alive", "name", "mass"})
public abstract class Animal extends Food {

    protected boolean isAlive;

    public Animal(String name, float m) {
        super(name, m);
        this.isAlive = true;
    }

    public abstract void seeFood(Food food) throws IllegalStateException, IllegalArgumentException; // former seePrey

    protected abstract void eat(Food food);

    public void kill() throws IllegalStateException {
        if (!isAlive)
            throw new IllegalStateException("Cannot kill a dead animal");
        isAlive = false;
    }

    public String getInfo() {
        return super.getInfo() + (isAlive ? " (живое)" : " (мертвое)");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}

