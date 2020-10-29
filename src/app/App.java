package app;
import model.*;
import model.exceptions.IllegalFoodException;

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
        Predator foxAsHunter = (Predator)fox;

        try{
            foxAsHunter.seeFood(clover); // пытаемся покормить не своей едой (5)
        } catch (IllegalFoodException e) {
            e.printStackTrace();
        }
        try {
            foxAsHunter.seeFood(rabbit); // пытаемся покормить хищника мертвым животным (5)
        } catch (IllegalFoodException e) {
            e.printStackTrace();
        }
    }
}
