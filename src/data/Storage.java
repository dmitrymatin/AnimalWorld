package data;

import model.Predator;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Storage<E> {
    private Map<Integer, E> elements = new HashMap<>();

    private int elementsCount = 0;

    public void addElement(E element) {
        elements.put(++elementsCount, element);
    }

    public void updateElement(int id, E element) {
        elements.replace(id, element);
    }

    public void removeElement(int id) {
        elements.remove(id);
    }

    public boolean saveElements(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(elements);

            ous.close();
            fos.close();

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void loadElements(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if (obj instanceof Map) {
                elements = (Map<Integer, E>) obj;
                System.out.println("successfully loaded elements (" + elements.getClass() + ")");
            }

            ois.close();
            fis.close();

            elementsCount = Collections.max(elements.keySet());
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public Map<Integer, E> getElements() {
        return elements;
    }
}
