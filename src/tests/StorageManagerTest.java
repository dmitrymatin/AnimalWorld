package tests;

import data.StorageManager;
import model.Food;
import model.Grass;
import model.Herbivore;
import model.Predator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageManagerTest {

    static StorageManager storageManager = StorageManager.getInstance();

    @Test
    void class_StorageManager_is_singleton() {
        StorageManager storageManager1 = StorageManager.getInstance();
        StorageManager storageManager2 = StorageManager.getInstance();

        assertSame(storageManager1, storageManager2);
    }

    @Test
    void addFood() {
        Food predator = new Predator("testPredator", 40f);
        Food herbivore = new Herbivore("testHerbivore", 30f);
        Food grass = new Grass("testGrass", 4f);

        storageManager.addFood(predator);
        storageManager.addFood(herbivore);
        storageManager.addFood(grass);

        assertTrue(storageManager.getPredators().values().contains(predator));
        assertTrue(storageManager.getHerbivores().values().contains(herbivore));
        assertTrue(storageManager.getGrasses().values().contains(grass));
    }

    @Test
    void updateFood() {
    }

    @Test
    void removeFood() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }

    @Test
    void getPredators() {
    }

    @Test
    void getHerbivores() {
    }

    @Test
    void getGrasses() {
    }
}