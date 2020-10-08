package app;
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
        Predator foxAsHunter = (Predator)fox;
        foxAsHunter.seeFood(rabbit);
    }
}
