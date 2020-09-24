package model;

public abstract class Animal extends Food {

    protected boolean isAlive;

    public Animal(String name, float m) {
        super(name, m);
        this.isAlive = true;
    }

    public void eat(Food food) {
        if (this instanceof Predator && food instanceof Animal) {
            // predator can eat any animal
            Animal animalToEat = (Animal) food;
            animalToEat.kill();
            this.m += animalToEat.m / 2f;
        } else if (this instanceof Herbivore && food instanceof Grass) {
            // herbivore can eat only grass
            this.m += food.m / 5f;
        }
    }

    public void kill() {
        if (isAlive)
            isAlive = false;
    }

    public String GetInfo() {
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

