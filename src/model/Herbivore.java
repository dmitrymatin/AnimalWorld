package model;

import java.util.Random;

public class Herbivore extends Animal {

    public Herbivore(String name, float m) {
        super(name, m);
    }

    public boolean seeFood(Food food) throws IllegalStateException, IllegalArgumentException {
        if (this == food)
            throw new IllegalArgumentException(rb.getString("OBJECTS_CANNOT_EQUAL"));

        if (food == null)
            throw new IllegalArgumentException(rb.getString("FOOD_NOT_SPECIFIED"));

        if (!this.isAlive)
            throw new IllegalStateException(rb.getString("DEAD_HERBIVORE_ACTION_ILLEGAL"));

        if (!(food instanceof Grass))
            throw new IllegalArgumentException(rb.getString("ILLEGAL_HERBIVORE_PREY"));

        boolean gotHungry = new Random().nextBoolean();
        if (gotHungry) {
            Grass grassToEat = (Grass) food;
            eat(grassToEat);
            return true;
        }
        return false;
    }

    protected void eat(Food food) {
        float massTaken = food.mass / 5f;

        food.mass -= massTaken;
        this.mass += massTaken;
    }
}
