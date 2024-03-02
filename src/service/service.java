package service;
import domain.Car;
import domain.Reservation;
import domain.User;
import filters.Filters;
import repository.*;
import domain.Fuel_Type;
import Validators.Car_validator;
import Validators.Reservation_Validator;
import settings.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class service {
    private IRepository<Integer,Car> repo ;
    private IRepository<Integer,Reservation> rez_repo;
    private User user;
    private MemoryRepo memoryRepo;
    private Filters filters = new Filters();

    public IRepository<Integer, Car> getRepo() {
        return repo;
    }

    public IRepository<Integer, Reservation> getRez_repo() {
        return rez_repo;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user1){
        user=user1;
    }

    public service(IRepository<Integer,Car> cars, IRepository<Integer,Reservation> revs){
        repo = cars;
        rez_repo = revs;
    }

    public boolean dateCompare(String[] localDate,String[] reservDate){
        if(!Integer.valueOf(localDate[2]).equals(Integer.valueOf(reservDate[2]))){
            return false;
        } else if (Integer.parseInt(localDate[1])!=Integer.parseInt(reservDate[1])) {
            return false;
        } else if ( Integer.parseInt(localDate[0]) != Integer.parseInt(reservDate[0])) {
            return false;
        }
        return true;
    }
    public void refresh(){

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String []datev = date.split("-");
        for(Reservation rev : rez_repo.getAllItems()){
            //TODO: complete the next if
            if(repo.findItem(rev.getCarID())!=null){
                repo.findItem(rev.getCarID()).set_status(dateCompare(datev,rev.getDate_of_order().split("-")));
                if(repo.findItem(rev.getCarID()).get_status() && repo.findItem(rev.getCarID())!=null){
                    repo.removeItem(rev.getCarID());
                }
            }
        }
    }
    public List<Car> filterCarBy(int type,String model){
        if(type == 1){
            return filters.filterByModel(model,repo);
        }
        if(type == 2){
            return filters.filterByAvability(repo);
        }
        return null;
    }
    public List<Reservation> filterRevsBy(int type){

        if(type==1)
            return filters.show_upcoming_reservations(rez_repo,user.getId());
        return null;
    }
    public void filterBY(int type)
    {
        Scanner scanner = new Scanner(System.in);
        if(type==1) {
            System.out.println("Input the fuel type you want:");
            System.out.println("1-Hybrid 2-Gas 3-Diesel");
            int fuel = scanner.nextInt();
            filters.filterByFuelType(fuel,repo);
        } else if (type == 2) {
            filters.filterByAvability(repo);
        } else if (type == 3) {
            System.out.println("What kind of model are you searching for?");
            String model = scanner.nextLine();
            filters.filterByModel(model,repo);
        } else if (type == 4) {
            System.out.println("What date are you searching for?");
            String date = scanner.nextLine();
            int loop = 1;
            while(loop==1) {
                System.out.println("What reservations do you search for?");
                System.out.println("Newer than the given date = 1");
                System.out.println("Older than the given date = 2");
                System.out.println("On the exact date = 3");
                System.out.println("Exit this filter -> 0");
                int var = scanner.nextInt();
                if(var == 0)
                    loop=0;
                else
                    filters.filterReservationByDate(date, var, rez_repo);
            }
        } else if (type == 5){
            System.out.println("Upcoming reservations are:");
            filters.show_upcoming_reservations(rez_repo,user.getId());
        } else if (type == 6) {
            filters.showTodaysReservations(rez_repo);
        }
    }
    public service()
    {
        repo = new DataBaseCAR();
        rez_repo = new DataBaseReservation();
        refresh();
    }
    public void printUserDetails()
    {
        System.out.println("Username: " + user.getName());
        System.out.println("Id: " + user.getId());
    }
    public int numberOfUsers()
    {
        int result=0;
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/wasy/IdeaProjects/a4-VasiAlexandru/data/data.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");){
            result++;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    public void addUser(String username, String password)
    {
        DataBaseUsers dataBase = new DataBaseUsers();
        User user1 = new User(numberOfUsers()+1,username,password);
        dataBase.addItem(user1.getId(),user1);
        user = new User(user1.getId(),username,password);

    }
    public boolean searchUser(String username, String password)
    {
         try(Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/wasy/IdeaProjects/a4-VasiAlexandru/data/data.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");){
             while(resultSet.next()){
                if(Objects.equals(resultSet.getString("username"), username)) {
                     if (Objects.equals(password, resultSet.getString("password"))) {
                         user = new User(resultSet.getInt("userID"), resultSet.getString("username"), resultSet.getString("password"));
                            return true;
                        } else {
                            System.out.println("The password is not correct!");
                          return false;
                }
            }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean searchUser(String username)
    {
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/wasy/IdeaProjects/a4-VasiAlexandru/data/data.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");){
            while(resultSet.next())
                if(Objects.equals(resultSet.getString("username"), username))
                {
                    System.out.println("The password is not correct!");
                    return false;
                }

            } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public List<Reservation> showOnlyUsersReservations(){
        //List<Reservation> revs = rez_repo.getAllItems().stream().filter(reservation -> reservation.getUserid()==user.getId()).collect(Collectors.toList());
        List<Reservation> revs = new ArrayList<>();
        for(Reservation rev : rez_repo.getAllItems()){
            if(rev.getUserid() == user.getId())
                revs.add(rev);
        }
        return revs;
    }
    public void loadExamples()
    {
        repo = new CarRepository();
        rez_repo = new ReservationRepo();
        Car car = new Car(6,"Mazda", Fuel_Type.GAS);
        Car car1 = new Car(1,"Prius", Fuel_Type.HYBRID);
        Car car2 = new Car(2,"Aris",Fuel_Type.GAS);
        Car car3 = new Car(3,"Polo",Fuel_Type.DIESEL);
        Car car4 = new Car(4, "Prius",Fuel_Type.HYBRID);
        Car car5 = new Car(5,"Octavia",Fuel_Type.GAS);
        Car car6 = new Car(7,"Octavia",Fuel_Type.GAS);
        Car car7 = new Car(5,"Volvo",Fuel_Type.GAS);
        Reservation rev1 = new Reservation(14,2,1,3,"17-12-2023","14:30",1);
        Reservation rev2 = new Reservation(25,2,2,3,"24-12-2023","16:30",7);
        Reservation rev3 = new Reservation(8,3,3,3,"17-08-2023","18:30",5);
        rez_repo.addItem( rev1.getId(),rev1);
        rez_repo.addItem(rev2.getId(),rev2);
        rez_repo.addItem( rev3.getId(),rev3);
        repo.addItem(car.getId(),car);
        repo.addItem(car1.getId(),car1);
        repo.addItem( car2.getId(),car2);
        repo.addItem( car3.getId(),car3);
        repo.addItem(car4.getId(),car4);
        repo.addItem( car5.getId(),car5);
        repo.addItem(car6.getId(),car6);
        repo.addItem( car7.getId(),car7);
        user.setId(3);
        user.setName("Marian Mihnea");
    }

    public void reserve_car( int id, int distance, int numberOfPeople, String date, String hour)
    {
        //TODO: Implement this function and all asociated ones in order to save the hour and check also the hour
        Car car = repo.findItem(id);
       List<Reservation> carsReservations = rez_repo.getAllItems().stream().filter(reservation -> Objects.equals(reservation.getCarID(), car.getId()) && LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).equals(reservation.getDate_of_order())).collect(Collectors.toList());
       if(carsReservations.isEmpty()){
           if(Objects.equals(date, "now"))
               rez_repo.addItem(next_valid_idreservs(),new Reservation(distance, numberOfPeople,rez_repo.getAllItems().size()+1,user.getId(),LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),id));
           else
               rez_repo.addItem(next_valid_idreservs(),new Reservation(distance, numberOfPeople,rez_repo.getAllItems().size()+1,user.getId(),date,hour,id));
           System.out.println("Reserved " + rez_repo.findItem(rez_repo.getAllItems().size()));
       }
       else {
           System.out.println("Can not reserve that specific car!");
       }
       refresh();
    }
    public void Update_car(int id, String new_model)
    {
        repo.findItem(id).update(new_model);

    }
    public boolean unique_id (int id)
    {
        for(Car car : repo.getAllItems())
        {
            if(car.getId() == id)
                return false;
        }
        return true;
    }
    public int next_valid_idreservs()
    {
        return rez_repo.getAllItems().size()+1;

    }
    public int next_valid_id()
    {
        int i=0;
        boolean good = false;
        while(!good) {
            good = true;
            i++;
            for (Car car : repo.getAllItems()) {

                if (car.getId() == i)
                    good = false;
            }

        }
        return i;

    }
    public Fuel_Type gasoline(int type)
    {
        if(type == 1)
            return Fuel_Type.HYBRID;
        else
            if(type == 2)
                return Fuel_Type.GAS;
            else
                return Fuel_Type.DIESEL;
    }
    public void add_car(int id, String model,int fuel)
    {
        Car car = new Car(id,model,gasoline(fuel));
        if(repo.findItem(id)!=null){
            System.out.println("ID that you requested is already used!");
            System.out.println("ID set to : " + repo.getAllItems().size());
            car.setId(repo.getAllItems().size());
        }
        repo.addItem(car.getId(),car);
    }
    public void remove_car(int id)
    {

        repo.removeItem(id);
        refresh();
    }
    public List<Car> print_available_cars()
    {
        List<Car> cars = new ArrayList<>();
        cars = repo.getAllItems().stream().filter(Car::get_status).collect(Collectors.toList());

        return cars;
    }
    public boolean check_status(int id,String date)
    {
        //TODO: the returned value of reservation.getID() is null
        List<Reservation> reservations = rez_repo.getAllItems().stream().filter(reservation -> reservation.getCarID()==id && Objects.equals(reservation.getDate_of_order(), date)).collect(Collectors.toList());
        return reservations.isEmpty();
    }
    public void print_all_cars()
    {
       List<Car> reserved = repo.getAllItems().stream().filter(car -> !check_status(car.getId(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))).collect(Collectors.toList());
       List<Car> available = repo.getAllItems().stream().filter(car -> check_status(car.getId(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))).collect(Collectors.toList());
       System.out.println("Available cars: \n");
       for (Car car : available)
       {
           System.out.println(car);
       }
        System.out.println("\n Reserved cars: \n");
       for(Car car : reserved)
       {
           System.out.println(car);
       }
    }

    public void print_all_reserved()
    {
        if(!rez_repo.getAllItems().isEmpty()) {
            System.out.println("All reserved cars:");
            for(Reservation rev : rez_repo.getAllItems())
            {
                System.out.println(rev.toString());
            }
        }
        else
        {
            System.out.println("There are no reservations !");
        }
    }
    public void remove_reservation(int id)
    {
        Reservation rev = rez_repo.findItem(id);
        if(rev!=null){
            Car car = repo.findItem(rev.getCarID());
            car.set_status(true);
            System.out.println("Successfully removed :" + car.get_model());
            rez_repo.removeItem(rev.getId());
        }
        else
            System.out.println("Could not find the reservation that you are looking for!");
    }
    public Car findByReservation(int rezervationID)
    {
       Reservation rev = rez_repo.findItem(rezervationID);
       for(Car car : repo.getAllItems())
       {
           if(Objects.equals(car.getId(), rev.getCarID()))
           {
               return car;
           }
       }
        return null;
    }
    public void update_reservation(int id,int newDistance, int new_number_of_people,int new_carid)
    {
        Reservation rev = rez_repo.findItem(id);
        if(rev!=null){
            System.out.println("Reservation found! :\n " + rev);
            Car car = repo.findItem(rev.getCarID());
            car.set_status(true);
            rev.setDistance(newDistance);
            rev.setPeople(new_number_of_people);
            rev.setCarID(new_carid);
            car = repo.findItem(new_carid);
            car.set_status(false);
            System.out.println("\nReservation updated to:");
            System.out.println(rev);
        }
        else
            System.out.println("Could not find the reservation");
    }




    // Managing the saving of information part ->


    public void saveToDB()
    {
        settings settings = new settings();
        settings.updateProperty("CarRepository","database");
        settings.updateProperty("CarFilePath","jdbc:sqlite:C:/Users/wasy/IdeaProjects/a4-VasiAlexandru/data/data.db");
        settings.updateProperty("ReservationRepository","database");
        settings.updateProperty("ReservationFilePath","jdbc:sqlite:C:/Users/wasy/IdeaProjects/a4-VasiAlexandru/data/data.db");
    }

    public void saveToTxt()
    {
        settings settings = new settings();
        settings.updateProperty("CarRepository","text");
        settings.updateProperty("CarFilePath","cars.txt");
        settings.updateProperty("ReservationRepository","text");
        settings.updateProperty("ReservationFilePath","reservations.txt");
    }
    public void saveToBin()
    {
        settings settings = new settings();
        settings.updateProperty("CarRepository","binary");
        settings.updateProperty("CarFilePath","cars.bin");
        settings.updateProperty("ReservationRepository","binary");
        settings.updateProperty("ReservationFilePath","reservations.bin");

    }
    public void saveToDataBase()
    {
        //TODO: Make saving to database possible
        //TODO: Implement the memory source from the beginning
        saveToDB();
        //settings settings = new settings();
        DataBaseCAR dataBaseCAR = new DataBaseCAR();
        DataBaseReservation dataBaseReservation = new DataBaseReservation();

        dataBaseCAR.fetchDataFromDataBase();
        dataBaseReservation.fetchDataFromDataBase();

        for (Car car : repo.getAllItems()){
            if(dataBaseCAR.findItem(car.getId())==null)
                dataBaseCAR.addItem(car.getId(),car);
        }
        for (Reservation rev : rez_repo.getAllItems())
        {
            if(dataBaseReservation.findItem(rev.getId())==null)
                dataBaseReservation.addItem(rev.getId(),rev);
        }

    }
    public void retriveFromDB(){
        DataBaseCAR dataBaseCAR = new DataBaseCAR();
        DataBaseReservation dataBaseReservation = new DataBaseReservation();
        dataBaseCAR.fetchDataFromDataBase();
        dataBaseReservation.fetchDataFromDataBase();

        for (Car car : dataBaseCAR.getAllItems()){
            if(repo.findItem(car.getId())==null){
                repo.addItem(car.getId(),car);
            }
        }
        for (Reservation rev : dataBaseReservation.getAllItems()){
            if(rez_repo.findItem(rev.getId())==null){
                rez_repo.addItem(rev.getId(),rev);
            }
        }
    }
    public void saveToFile(int fileType)
    {
        FileRepository<Integer,Car> cars;
        FileRepository<Integer,Reservation> reservations;
        settings settings = new settings();
        if(fileType == 1) {
            saveToTxt();
            cars = new CarRepositoryTextFile(settings.propertiesTest("CarFilePath"));
            reservations = new ReservationRepoTextFile(settings.propertiesTest("ReservationFilePath"));

        }
        else {
            saveToBin();
            cars = new CarRepositoryBinaryFile(settings.propertiesTest("CarFilePath"));
            reservations = new ReservationRepoBinaryFile(settings.propertiesTest("ReservationFilePath"));
        }
        for(Car car : repo.getAllItems())
        {
            cars.addItem(car.getId(),car);
        }
        for(Reservation rev : rez_repo.getAllItems())
        {
            reservations.addItem(rev.getId(),rev);
        }

    }

}
