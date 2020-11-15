package data;

import java.io.*;
import java.util.ArrayList;

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

            System.out.println("successfully saved data");
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean load() {
        try {
            FileInputStream fis = new FileInputStream("objects.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if (obj instanceof ArrayList){
                data = (ArrayList<E>) obj;
                System.out.println("successfully loaded data");
                return true;
            }
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}