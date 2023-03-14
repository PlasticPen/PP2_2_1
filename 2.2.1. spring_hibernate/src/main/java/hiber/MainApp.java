package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        //Creating and adding users without car to a Database
        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        //Adding the car to User and add User to a Database
        userService.add(addUserWithCar(new User("User11", "Lastname11", "user11@mail.ru"),
                new Car("BMW", 3)));
        userService.add(addUserWithCar(new User("User22", "Lastname22", "user22@mail.ru"),
                new Car("AUDI", 99)));
        userService.add(addUserWithCar(new User("User33", "Lastname33", "user33@mail.ru"),
                new Car("MERCEDES", 600)));
        userService.add(addUserWithCar(new User("User44", "Lastname44", "user44@mail.ru"),
                new Car("LADA", 2107)));

        //Print all users' information
        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Car: model " + user.getCar().getModel() + " , series " + user.getCar().getSeries());
            } else {
                System.out.println("Car: no car");
            }
            System.out.println();
        }

        //Get User by model and series of the car
        try {
            System.out.println(userService.getUserByCar("BMW", 3).getFirstName());
        } catch (NullPointerException npe) {
            System.out.println("No user with such car found");
        }

        context.close();
    }

    public static User addUserWithCar(User user, Car car) {
        car.setUser(user);
        user.setCar(car);
        return user;
    }
}
