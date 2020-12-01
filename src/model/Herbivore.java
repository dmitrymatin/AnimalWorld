package model;

import java.util.Random;

public class Herbivore extends Animal {

    public Herbivore(String name, float m) {
        super(name, m);
    }

    public void seeFood(Food food) throws IllegalStateException, IllegalArgumentException {
        if (!this.isAlive)
            throw new IllegalStateException("Cannot perform actions on dead herbivore");

        if (!(food instanceof Grass))
            throw new IllegalArgumentException("Herbivore can only view grass as food");

        boolean gotHungry = new Random().nextBoolean();
        if (gotHungry) {
            Grass grassToEat = (Grass) food;
            eat(grassToEat);
        }
    }

    protected void eat(Food food) {
        float massTaken = food.mass / 5f;

        food.mass -= massTaken;
        this.mass += massTaken;
    }
}
