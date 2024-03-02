package GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import domain.Car;
import domain.Reservation;
import domain.User;
import filters.Filters;
import repository.IRepository;
import service.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {

    public service service ;
    private Filters filters = new Filters();
    public Controller(service service){
        this.service = service;
    }
    @FXML
    private TextField HourOfTheReservation;

    @FXML
    private Button SubmitReservDate;

    @FXML
    private TextField carid2;

    @FXML
    private TextField dateOfReservation;

    @FXML
    private TextField distance2;

    @FXML
    private TextField persons2;

    @FXML
    private Button ReserveCarForDate;
    @FXML
    private Label AvailableCars = new Label();

    @FXML
    private Label ReservToday = new Label();

    @FXML
    private Button ReserveCar;

    @FXML
    private Label AllCarsLabel = new Label();
    @FXML
    private MenuItem ShowCars;

    @FXML
    private AnchorPane menu;

    @FXML
    private MenuItem showreservations;
    @FXML
    private Button buttonSignIn;
    @FXML
    private Button buttonSignUp;

    @FXML
    private Button Back;

    @FXML
    private Button Submit;

    @FXML
    private TextField password1;

    @FXML
    private TextField password2;

    @FXML
    private TextField username;

    @FXML
    private TextField Password=new TextField();

    @FXML
    private TextField Username=new TextField();
    @FXML
    private TextField CarId;

    @FXML
    private Button SumbitReserv;

    @FXML
    private TextField distance;

    @FXML
    private TextField noOfPersons;

    @FXML
    private MenuItem CancelReservButton;
    @FXML
    private  TextField reservationID = new TextField();
    @FXML
    private Label AllReservs = new Label();
    @FXML
    private MenuItem ByModelButton;
    @FXML
    private TextField inputModel=new TextField();

    @FXML
    private Button showModelButton;
    @FXML
    private Label BymodelLabel = new Label();
    @FXML
    private Button ManagerLogin ;
    @FXML
    void LoginManager(){
        try {
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/ManagerLoginPage.fxml"));
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showforModel(ActionEvent event) {

    }

    @FXML
    void RemoveReserv(){
        String reservID = reservationID.getText();
        service.remove_reservation(Integer.parseInt(reservID));
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/HomePage.fxml"));
            Stage stage = (Stage) SumbitReserv.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void CancelReservationSceneSwap() {
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/CancelReservPage.fxml"));
            Stage stage = (Stage) CancelReservButton.getParentPopup().getOwnerWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Reservation> revs = filters.show_upcoming_reservations(service.getRez_repo(),service.getUser().getId());
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {

            StringBuilder labelText1 = new StringBuilder();
            for(Reservation rev : revs){
                labelText1.append(rev.toString()+"\n");
            }
            if(!revs.isEmpty())
                ReservToday.setText(labelText1.toString());
            else
                ReservToday.setText("There are no reservations for you today!");
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }





    @FXML
    void GoToHome() {
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/HomePage.fxml"));
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void CheckValues(ActionEvent event) {
        if(service.searchUser(username.getText())) {
            service.addUser(username.getText(), password1.getText());
            try {
                Controller controller = new Controller(service);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/HomePage.fxml"));
                Stage stage = (Stage) Submit.getScene().getWindow();
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username already taken!");
            alert.setContentText("There exist already an user with that username!!");
            alert.show();
        }
    }
    @FXML
    void CheckValuesforSignIn(ActionEvent event) {
        String username = Username.getText();
        String password = Password.getText();
        if(service.searchUser(username,password)) {
            try {
                Controller controller = new Controller(service);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/HomePage.fxml"));
                Stage stage = (Stage) Submit.getScene().getWindow();
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect credentials!");
            alert.setContentText("Wrong credentials, do you have an account?");
            alert.show();
        }
    }

    @FXML
    void GoBack(ActionEvent event) {
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/CarsGUI.fxml"));
            Stage stage = (Stage) Back.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void ShowCars(){ try{
        Controller controller = new Controller(service);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/ShowingCars.fxml"));
        Stage stage = (Stage) ShowCars.getParentPopup().getOwnerWindow();
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }catch (Exception e){
        e.printStackTrace();
    }
    }
    @FXML
    void ReservOnDate(){
        String carid = carid2.getText();
        String Distance = distance2.getText();
        String NumberOfPeople = persons2.getText();
        String date = dateOfReservation.getText();
        String hour = HourOfTheReservation.getText();
        service.reserve_car(Integer.parseInt(carid),Integer.parseInt(Distance),Integer.parseInt(NumberOfPeople),date, hour);
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/HomePage.fxml"));
            Stage stage = (Stage) SubmitReservDate.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void ReserveForADate(){
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/ReserveOnDate.fxml"));
            Stage stage = (Stage) ReserveCarForDate.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void ReserveCar() {
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/ReserveNowPage.fxml"));
            Stage stage = (Stage) ReserveCar.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void createReservNow() {
        String carid = CarId.getText();
        String Distance = distance.getText();
        String NumberOfPeople = noOfPersons.getText();
        service.reserve_car(Integer.parseInt(carid),Integer.parseInt(Distance),Integer.parseInt(NumberOfPeople), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/HomePage.fxml"));
            Stage stage = (Stage) SumbitReserv.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void Showusersreservations() {

    try{
        Controller controller = new Controller(service);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/ShowingAllReservationsPage.fxml"));
        Stage stage = (Stage) showreservations.getParentPopup().getOwnerWindow();
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }catch (Exception e){
        e.printStackTrace();
    }

    }


    @FXML
    void ByModelFunction(ActionEvent event) {
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/ShowByModel.fxml"));
            Stage stage = (Stage) ByModelButton.getParentPopup().getOwnerWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void initalizeModels(){
        List<Car> cars = service.filterCarBy(1,inputModel.getText());
        StringBuilder builder = new StringBuilder();
        for(Car car  : cars){
            builder.append(car.toString()).append("\n");
        }
        BymodelLabel.setText(builder.toString());
    }
    public void initialize() {
        if(service.getUser()!=null) {
            service.refresh();
            List<Car> cars2 = service.getRepo().getAllItems().stream().toList();
            StringBuilder labelText3 = new StringBuilder();
            for(Car car : cars2){
                labelText3.append(car).append("\n");
            }
            if(!cars2.isEmpty())
                AllCarsLabel.setText(labelText3.toString());
            else
                AllCarsLabel.setText("There are no cars!");

            List<Reservation> revs1 = service.showOnlyUsersReservations();
            StringBuilder labelText2 = new StringBuilder();
            for (Reservation rev : revs1) {
                labelText2.append(rev.toString()).append(" ").append("\n");
            }
            if (!revs1.isEmpty())
                AllReservs.setText(labelText2.toString());
            else
                AllReservs.setText("You do not have reservations !!");



            Timeline timeline = getTimeline();
            timeline.play();
            service.refresh();
        }
    }

    private Timeline getTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {


            service.refresh();
            StringBuilder labelText = new StringBuilder();
            StringBuilder labelText1 = new StringBuilder();
            List<Car> cars = service.filterCarBy(2,"");
            List<Reservation> revs = service.filterRevsBy(1);
            for (Reservation rev : revs) {
                labelText1.append(rev.toString()).append("\n");
            }
            for (Car car : cars) {
                labelText.append(car.get_model()).append(" with id: ").append(car.getId()).append("\n");
            }
            if (!revs.isEmpty())
                ReservToday.setText(labelText1.toString());
            else
                ReservToday.setText("There are no reservations for you today!");
            if (!cars.isEmpty())
                AvailableCars.setText(labelText.toString());
            else
                AvailableCars.setText("There are no free cars!");
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    @FXML
    private void buttonpressed() {


        try {
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/SignInPage.fxml"));
            Stage stage = (Stage) buttonSignIn.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void SignUpPressed() {
        try{
            Controller controller = new Controller(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/GUI/SignUpPage.fxml"));
            Stage stage = (Stage) buttonSignUp.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
