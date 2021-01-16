package data;

import app.GeneralController;
import model.*;

import java.util.*;

public class StorageManager {
    private final ResourceBundle rb = ResourceBundle.getBundle("ResourceBundle", Locale.getDefault());
    private String PREDATORS_FILENAME;
    private String HERBIVORES_FILENAME;
    private String GRASSES_FILENAME;

    private static StorageManager uniqueInstance = null;

    private int elementsCount = 0;

    private final Storage<Predator> predatorStorage = new Storage<>();
    private final Storage<Herbivore> herbivoreStorage = new Storage<>();
    private final Storage<Grass> grassStorage = new Storage<>();


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
            predatorStorage.addElement(++elementsCount, (Predator) food);
        else if (food instanceof Herbivore)
            herbivoreStorage.addElement(++elementsCount, (Herbivore) food);
        else if (food instanceof Grass)
            grassStorage.addElement(++elementsCount, (Grass) food);
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

    public void load() throws Exception {
        PREDATORS_FILENAME = GeneralController.getProperties().getProperty("PREDATORS_FILENAME");
        HERBIVORES_FILENAME = GeneralController.getProperties().getProperty("HERBIVORES_FILENAME");
        GRASSES_FILENAME = GeneralController.getProperties().getProperty("GRASSES_FILENAME");
        try {
            int maxPredatorId = predatorStorage.loadElements(PREDATORS_FILENAME);
            int maxHerbivoreId = herbivoreStorage.loadElements(HERBIVORES_FILENAME);
            int maxGrassId = grassStorage.loadElements(GRASSES_FILENAME);
            int[] maxIds = new int[]{maxPredatorId, maxHerbivoreId, maxGrassId};

            elementsCount = Arrays.stream(maxIds).max().getAsInt();
        } catch (IllegalAccessException ex) {
            throw new Exception(rb.getString("ERROR_LOAD_DATA_FAIL") + ": " + ex.getMessage());
        }
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

    public Map<Integer, Animal> getAnimals() {
        Map<Integer, Predator> predators = getPredators();
        Map<Integer, Herbivore> herbivores = getHerbivores();

        Map<Integer, Animal> animals = new HashMap<>(predators.size() + herbivores.size());
        animals.putAll(predators);
        animals.putAll(herbivores);

        return animals;
    }

    public Map<Integer, Food> getAll() {
        Map<Integer, Grass> grasses = getGrasses();
        Map<Integer, Animal> animals = getAnimals();

        Map<Integer, Food> allFoods = new HashMap<>(animals.size() + grasses.size());
        allFoods.putAll(animals);
        allFoods.putAll(grasses);

        return allFoods;
    }
}