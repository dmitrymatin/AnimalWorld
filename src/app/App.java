package app;

import data.Storage;
import model.*;

public class App {

    public static void main(String[] args) {
        Animal wolf = new Predator("Волк Вася", 60);
        Animal fox = new Predator("Лиса Алиса", 40);
        Animal rabbit = new Herbivore("Зайка Игорь", 10);
        Grass clover = new Grass("Трава Капуста", 0.25f);

        System.out.println(wolf.getInfo());
        System.out.println(fox.getInfo());
        System.out.println(rabbit.getInfo());
        System.out.println(clover.getInfo());

        System.out.println("Заяц умирает...");
        rabbit.kill();
        System.out.println(rabbit.getInfo());

        // hunting
        Predator foxAsHunter = (Predator) fox;

        try {
            foxAsHunter.seeFood(clover); // пытаемся покормить не своей едой (4)
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            foxAsHunter.seeFood(rabbit); // пытаемся покормить хищника мертвым животным (5)
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        fox.kill();
        try {
            fox.kill();    // пытаемся убить мертвое в kill (1)
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        try {
            fox.seeFood(wolf); // пытаемся покормить мертвое животное (3)
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }


        try {
            Food unrealFox = new Predator("Unreal fox", -3.5f); // пытаемся создать животное (наследник Food) с отрицательной массой (2)
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        Storage<Predator> storage = new Storage<>();
        storage.add((Predator) fox);
        storage.add((Predator) wolf);
//        storage.add((Predator) rabbit);
        storage.save();
        storage.load();
    }
}
