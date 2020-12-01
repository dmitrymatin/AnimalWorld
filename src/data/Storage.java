package data;

import model.Grass;
import model.Herbivore;
import model.Predator;

import java.io.*;
import java.util.*;

public class Storage {
    private static Storage uniqueInstance = null;

    private Map<Integer, Predator> predators = new HashMap<>();
    private Map<Integer, Herbivore> herbivores = new HashMap<>();
    private Map<Integer, Grass> grasses = new HashMap<>();

    private int predatorCount = 0;
    private int herbivoreCount = 0;
    private int grassCount = 0;

    private Storage() {
    }

    public static Storage getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Storage();
        }
        return uniqueInstance;
    }

    public void addPredator(Predator predator) {
        predators.put(++predatorCount, predator);
    }

    public void updatePredator(int id, Predator predator) {
        predators.replace(id, predator);
    }

    public void removePredator(int id) {
        predators.remove(id);
    }

    public void addHerbivore(Herbivore herbivore) {
        herbivores.put(++herbivoreCount, herbivore);
    }

    public void updateHerbivore(int id, Herbivore herbivore) {
        herbivores.replace(id, herbivore);
    }

    public void removeHerbivore(int id) {
        herbivores.remove(id);
    }

    public void addGrass(Grass grass) {
        grasses.put(++grassCount, grass);
    }

    public void updateGrass(int id, Grass grass){
        grasses.replace(id, grass);
    }

    public void removeGrass(int id) {
        grasses.remove(id);
    }

    public boolean save() {
        return savePredators() & saveHerbivores() & saveGrass();
    }

    private boolean savePredators() {
        try {
            FileOutputStream fos = new FileOutputStream("predators.dat");
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(predators);

            ous.close();
            fos.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean saveHerbivores() {
        try {
            FileOutputStream fos = new FileOutputStream("herbivores.dat");
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(herbivores);

            ous.close();
            fos.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean saveGrass() {
        try {
            FileOutputStream fos = new FileOutputStream("grasses.dat");
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(grasses);

            ous.close();
            fos.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void load() {
        loadPredators();
        loadHerbivores();
        loadGrass();
    }

    private void loadPredators() {
        try {
            FileInputStream fis = new FileInputStream("predators.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if (obj instanceof Map) {
                predators = (Map<Integer, Predator>) obj;
                System.out.println("successfully loaded predators");
            }

            ois.close();
            fis.close();

            predatorCount = Collections.max(predators.keySet());
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadHerbivores() {
        try {
            FileInputStream fis = new FileInputStream("herbivores.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if (obj instanceof Map) {
                herbivores = (Map<Integer, Herbivore>) obj;
                System.out.println("successfully loaded herbivores");
            }

            ois.close();
            fis.close();

            herbivoreCount = Collections.max(herbivores.keySet());
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadGrass() {
        try {
            FileInputStream fis = new FileInputStream("grasses.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if (obj instanceof ArrayList) {
                grasses = (Map<Integer, Grass>) obj;
                System.out.println("successfully loaded grasses");
            }

            ois.close();
            fis.close();

            grassCount = Collections.max(grasses.keySet());
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public Map<Integer, Predator> getPredators() {
        return predators;
    }

    public Map<Integer, Herbivore> getHerbivores() {
        return herbivores;
    }

    public Map<Integer, Grass> getGrasses() {
        return grasses;
    }
}