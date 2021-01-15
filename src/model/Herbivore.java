package model;

import java.util.Random;

public class Herbivore extends Animal {

    public Herbivore(String name, float m) {
        super(name, m);
    }

    public boolean seeFood(Food food) throws IllegalStateException, IllegalArgumentException {
        if (this == food)
            throw new IllegalArgumentException("Food cannot be same object as hunter");

        if (food == null)
            throw new IllegalArgumentException("Food not specified");

        if (!this.isAlive)
            throw new IllegalStateException("Cannot perform actions on dead herbivore");

        if (!(food instanceof Grass))
            throw new IllegalArgumentException("Herbivore can only view grass as food");

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
