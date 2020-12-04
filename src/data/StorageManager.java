package data;

import model.*;

import java.util.*;

public class StorageManager {
    private final String PREDATORS_FILENAME = "predators.dat";
    private final String HERBIVORES_FILENAME = "herbivores.dat";
    private final String GRASSES_FILENAME = "grasses.dat";

    private static StorageManager uniqueInstance = null;

    private Storage<Predator> predatorStorage = new Storage<>();
    private Storage<Herbivore> herbivoreStorage = new Storage<>();
    private Storage<Grass> grassStorage = new Storage<>();

    private StorageManager() {
    }

    public static StorageManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new StorageManager();
        }
        return uniqueInstance;
    }

    public void addFood(Food food) {
        if (food instanceof Predator)
            predatorStorage.addElement((Predator) food);
        else if (food instanceof Herbivore)
            herbivoreStorage.addElement((Herbivore) food);
        else if (food instanceof Grass)
            grassStorage.addElement((Grass) food);
    }

    public void updateFood(int id, Food food) {
        if (food instanceof Predator)
            predatorStorage.updateElement(id, (Predator) food);
        else if (food instanceof Herbivore)
            herbivoreStorage.updateElement(id, (Herbivore) food);
        else if (food instanceof Grass)
            grassStorage.updateElement(id, (Grass) food);
    }

    public void removeFood(int id, Food food) {
        if (food instanceof Predator)
            predatorStorage.removeElement(id);
        else if (food instanceof Herbivore)
            herbivoreStorage.removeElement(id);
        else if (food instanceof Grass)
            grassStorage.removeElement(id);
    }

    public boolean save() {
        return predatorStorage.saveElements(PREDATORS_FILENAME)
                & herbivoreStorage.saveElements(HERBIVORES_FILENAME)
                & grassStorage.saveElements(GRASSES_FILENAME);
    }

    public void load() {
        predatorStorage.loadElements(PREDATORS_FILENAME);
        herbivoreStorage.loadElements(HERBIVORES_FILENAME);
        grassStorage.loadElements(GRASSES_FILENAME);
    }

    public Map<Integer, Predator> getPredators() {
        return predatorStorage.getElements();
    }

    public Map<Integer, Herbivore> getHerbivores() {
        return herbivoreStorage.getElements();
    }

    public Map<Integer, Grass> getGrasses() {
        return grassStorage.getElements();
    }
}