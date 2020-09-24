package model;

import java.util.Random;

public class Predator extends Animal {

    public Predator(String name, float m) {
        super(name, m);
    }

    public void hunt(Animal animal) {
        Random random = new Random();
        boolean success = random.nextBoolean();
        if (success) {
            this.eat(animal);
        }
    }

}
