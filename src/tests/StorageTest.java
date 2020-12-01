package tests;

import data.Storage;
import model.Predator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void class_Storage_is_singleton() {
        Storage storage1 = Storage.getInstance();
        Storage storage2 = Storage.getInstance();

        assertSame(storage1, storage2);
    }

    @Test
    void addPredator() {
//        Storage storage = Storage.getInstance();
//
//        Predator p = new Predator("test", 4f);
//        storage.addPredator(p);

    }

    @Test
    void removePredator() {
    }

    @Test
    void addHerbivore() {
    }

    @Test
    void removeHerbivore() {
    }

    @Test
    void addGrass() {
    }

    @Test
    void removeGrass() {
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