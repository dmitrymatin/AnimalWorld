package model;

import java.util.Random;

public class Predator extends Animal {

    public Predator(String name, float m) {
        super(name, m);
    }

    public boolean seeFood(Food food) throws IllegalStateException, IllegalArgumentException {
        if (this == food)
            throw new IllegalArgumentException("Food cannot be same object as hunter");

        if (food == null)
            throw new IllegalArgumentException("Food not specified");

        if (!this.isAlive)
            throw new IllegalStateException("Cannot perform actions on dead predator");

        if (!(food instanceof Animal))
            throw new IllegalArgumentException("Predator can only view animals as food");

        boolean gotHungry = new Random().nextBoolean();
        if (gotHungry) {
            Animal animalToEat = (Animal) food;
            if (hunt(animalToEat)) {
                eat(animalToEat);
                return true;
            }
//            else
//                System.out.println("(Debug) Predator got hungry but could not catch the animal " + animalToEat);
        }
        return false;
    }

    protected boolean hunt(Animal animal) throws IllegalArgumentException {
        if (!animal.isAlive)
            throw new IllegalArgumentException("Predator cannot hunt for dead animal");

        return new Random().nextBoolean();
    }

    protected void eat(Food food) {
        Animal animalToEat = (Animal) food;

        animalToEat.kill();
        float massTaken = animalToEat.mass / 2f;

        animalToEat.mass -= massTaken;
        this.mass += massTaken;

        System.out.println(this.name + " съедает " + animalToEat.name);
    }
}
