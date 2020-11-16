package data;

import model.Animal;
import model.Herbivore;
import model.Predator;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Storage<E> {
    private ArrayList<E> data = new ArrayList<>();

    public void add(E element) {
        data.add(element);
    }

    public void remove(E element) {
        data.remove(element);
    }

    public boolean save() {
        try {
            FileOutputStream fos = new FileOutputStream("objects.dat");
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(data);

            ous.close();
            fos.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void load() {
        try {
            FileInputStream fis = new FileInputStream("objects.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if (obj instanceof ArrayList) {
                data = (ArrayList<E>) obj;
                System.out.println("successfully loaded data");
            }

            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<E> getData() {
        return data;
    }

    public Stream<E> filter(Predicate<E> predicate) {
        return data.stream().filter(predicate);
    }

    public ArrayList<Predator> findPredators() {
        ArrayList<Predator> resultingCollection = new ArrayList<>();
        Iterator<E> iterator = data.iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (element instanceof Predator)
                resultingCollection.add((Predator) element);
        }
        return resultingCollection;
    }

    public ArrayList<Herbivore> findHerbivores() {
        ArrayList<Herbivore> resultingCollection = new ArrayList<>();
        Iterator<E> iterator = data.iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (element instanceof Herbivore)
                resultingCollection.add((Herbivore) element);
        }
        return resultingCollection;
    }

    public ArrayList<Animal> findAllWithIsAlive(boolean isAliveValue) {
        ArrayList<Animal> resultingCollection = new ArrayList<>();
        Iterator<E> iterator = data.iterator();
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (element instanceof Animal) {
                Animal animal = ((Animal) element);
                if (animal.isAlive() == isAliveValue)
                    resultingCollection.add(animal);
            }
        }
        return resultingCollection;
    }
}