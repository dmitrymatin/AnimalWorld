package app;
import model.*;

public class App {

    public static void main(String[] args) {
        Animal wolf = new Predator("Волк Вася", 60);
        Animal fox = new Predator("Лиса Алиса", 40);
        Animal rabbit = new Herbivore("Зайка Игорь", 10);
        Grass clover = new Grass("Трава Капуста", 0.25f);

        System.out.println(wolf.GetInfo());
        System.out.println(fox.GetInfo());
        System.out.println(rabbit.GetInfo());
        System.out.println(clover.GetInfo());

        System.out.println("Заяц умирает...");
        rabbit.kill();
        System.out.println(rabbit.GetInfo());
    }

}
