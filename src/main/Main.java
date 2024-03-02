package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import GUI.Controller;
import domain.Car;
import domain.Reservation;
import repository.DataBaseCAR;
import repository.DataBaseReservation;
import repository.IRepository;
import repository.MemoryRepo;
import service.service;

public class Main extends Application {

    public static void main(String[] args){
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        IRepository<Integer, Car> cars = new DataBaseCAR();
        IRepository<Integer, Reservation> revs = new DataBaseReservation();
        Controller controller = new Controller(new service(cars,revs));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CarsGUI.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();


    }
}
