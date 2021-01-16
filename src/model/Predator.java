package model;

import java.util.Random;

public class Predator extends Animal {

    public Predator(String name, float m) {
        super(name, m);
    }

    public boolean seeFood(Food food) throws IllegalStateException, IllegalArgumentException {
        if (this == food)
            throw new IllegalArgumentException(rb.getString("OBJECTS_CANNOT_EQUAL"));

        if (food == null)
            throw new IllegalArgumentException(rb.getString("FOOD_NOT_SPECIFIED"));

        if (!this.isAlive)
            throw new IllegalStateException(rb.getString("DEAD_PREDATOR_ACTION_ILLEGAL"));

        if (!(food instanceof Animal))
            throw new IllegalArgumentException(rb.getString("ILLEGAL_PREDATOR_PREY"));
        else{
            Animal animalToEat = (Animal) food;

            if (!animalToEat.isAlive())
                throw new IllegalArgumentException(rb.getString("PREDATOR_HUNT_DEAD_ILLEGAL"));

            boolean gotHungry = new Random().nextBoolean();
            if (gotHungry) {
                if (hunt(animalToEat)) {
                    eat(animalToEat);
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean hunt(Animal animal) throws IllegalArgumentException {
        return new Random().nextBoolean();
    }

    protected void eat(Food food) {
        Animal animalToEat = (Animal) food;

        animalToEat.kill();
        float massTaken = animalToEat.mass / 2f;

        animalToEat.mass -= massTaken;
        this.mass += massTaken;

        System.out.println(this.name + " " + rb.getString("PREDATOR_HUNT_DEAD_ILLEGAL") + " " + animalToEat.name);
    }
}
