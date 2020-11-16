package tests;

import data.Storage;
import model.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    private Storage<Food> foodStorage;

    @BeforeEach
    void setUp() {
        foodStorage = new Storage<>();

        foodStorage.add(new Herbivore("Заяц Игорь", 5.2f));
        foodStorage.add(new Herbivore("Заяц Джон", 5.2f));
        foodStorage.add(new Predator("Лиса Алиса", 14.5f));
        foodStorage.add(new Predator("Волк Вася", 24.5f));
        foodStorage.add(new Predator("Волк Петя", 19.5f));
        foodStorage.add(new Grass("Клевер", 0.7f));
    }

    @org.junit.jupiter.api.Test
    void add() {
        Food herbivoreToAdd = new Herbivore("test", 5f);
        Food predatorToAdd = new Predator("test", 5f);
        Food grassToAdd = new Grass("test", 5f);
        foodStorage.add(herbivoreToAdd);
        foodStorage.add(predatorToAdd);
        foodStorage.add(grassToAdd);

        assertTrue(foodStorage.getData().contains(herbivoreToAdd));
        assertTrue(foodStorage.getData().contains(predatorToAdd));
        assertTrue(foodStorage.getData().contains(grassToAdd));
    }

    @org.junit.jupiter.api.Test
    void remove() {
        Food herbivoreToAddAndRemove = new Herbivore("test", 5f);
        Food predatorToAddAndRemove = new Predator("test", 5f);
        Food grassToAddAndRemove = new Grass("test", 5f);

        foodStorage.add(herbivoreToAddAndRemove);
        foodStorage.add(predatorToAddAndRemove);
        foodStorage.add(grassToAddAndRemove);

        foodStorage.remove(herbivoreToAddAndRemove);
        foodStorage.remove(predatorToAddAndRemove);
        foodStorage.remove(grassToAddAndRemove);

        assertFalse(foodStorage.getData().contains(herbivoreToAddAndRemove));
        assertFalse(foodStorage.getData().contains(predatorToAddAndRemove));
        assertFalse(foodStorage.getData().contains(grassToAddAndRemove));
    }

    @org.junit.jupiter.api.Test
    void save() {
        Food herbivoreToAdd = new Herbivore("test", 5f);
        Food predatorToAdd = new Predator("test", 5f);
        Food grassToAdd = new Grass("test", 5f);

        foodStorage.add(herbivoreToAdd);
        foodStorage.add(predatorToAdd);
        foodStorage.add(grassToAdd);

        assertTrue(foodStorage.save());
    }

    @org.junit.jupiter.api.Test
    void load() {
        Food herbivoreToAdd = new Herbivore("test", 5f);
        Food predatorToAdd = new Predator("test", 5f);
        Food grassToAdd = new Grass("test", 5f);

        foodStorage.add(herbivoreToAdd);
        foodStorage.add(predatorToAdd);
        foodStorage.add(grassToAdd);
        foodStorage.save();

        Storage<Food> newInstance = new Storage<>();
        newInstance.load();

        assertEquals(newInstance.getData().stream().filter(
                f -> f.getId() == herbivoreToAdd.getId()).count(), 1);
        assertEquals(newInstance.getData().stream().filter(
                f -> f.getId() == predatorToAdd.getId()).count(), 1);
        assertEquals(newInstance.getData().stream().filter(
                f -> f.getId() == grassToAdd.getId()).count(), 1);
    }

    @org.junit.jupiter.api.Test
    void filter() {
        Stream<Food> deadAnimalsFromCustomFilter = foodStorage.filter(
                food -> food instanceof Animal
                        && !((Animal) food).isAlive());

        Stream<Food> deadAnimalsFromBuiltInFilter = foodStorage.getData().stream().filter(
                f -> f instanceof Animal
                        && !((Animal) f).isAlive());

        assertArrayEquals(deadAnimalsFromBuiltInFilter.toArray(), deadAnimalsFromCustomFilter.toArray());
    }

    @org.junit.jupiter.api.Test
    void findPredators() {
        ArrayList<Predator> predators = foodStorage.findPredators();

        Stream<Food> predatorsViaStream = foodStorage.getData().stream().filter(f -> f instanceof Predator);

        assertArrayEquals(predators.toArray(), predatorsViaStream.toArray());
    }

    @org.junit.jupiter.api.Test
    void findHerbivores() {
        ArrayList<Herbivore> herbivores = foodStorage.findHerbivores();

        Stream<Food> herbivoresViaStream = foodStorage.getData().stream().filter(f -> f instanceof Herbivore);

        assertArrayEquals(herbivores.toArray(), herbivoresViaStream.toArray());
    }

    @org.junit.jupiter.api.Test
    void findAllWithIsAliveWorksForAlive() {
        ArrayList<Animal> liveAnimals = foodStorage.findAllWithIsAlive(true);

        Stream<Food> liveAnimalsViaStream = foodStorage.getData().stream().filter(
                f -> f instanceof Animal && ((Animal) f).isAlive());

        assertArrayEquals(liveAnimals.toArray(), liveAnimalsViaStream.toArray());
    }
}