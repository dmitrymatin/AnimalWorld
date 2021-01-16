package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"alive", "name", "mass"})
public abstract class Animal extends Food {

    protected boolean isAlive;

    public Animal(String name, float m) {
        super(name, m);
        this.isAlive = true;
    }

    public abstract boolean seeFood(Food food) throws IllegalStateException, IllegalArgumentException; // former seePrey

    protected abstract void eat(Food food);

    public void kill() throws IllegalStateException {
        if (!isAlive)
            throw new IllegalStateException(rb.getString("ERROR_KILL_DEAD_ANIMAL_ILLEGAL"));
        isAlive = false;
    }

    public String getInfo() {
        return super.getInfo() + (isAlive ? " (" + rb.getString("ALIVE_VALUE") + ")" : " (" + rb.getString("DEAD_VALUE") + ")");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}

