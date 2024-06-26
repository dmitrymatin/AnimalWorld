package data;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Storage<E> {
    private Map<Integer, E> elements = new HashMap<>();

    public void addElement(int id, E element) {
        elements.put(id, element);
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

    public int loadElements(String fileName) throws IllegalAccessException {
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

            return elements.keySet().size() == 0 ? 0 : Collections.max(elements.keySet());
        } catch (ClassNotFoundException | IOException ex) {
            throw new IllegalAccessException("could not load data from file " + ex.getMessage());
        }
    }

    public Map<Integer, E> getElements() {
        return elements;
    }
}
