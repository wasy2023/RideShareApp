package filters;

import domain.Car;
import domain.Fuel_Type;
import domain.Reservation;
import repository.IRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Filters {
    public void sortBy(int type,IRepository<Integer,Car> repo)
    {
        List<Car> cars = new ArrayList<>();
        if(type==1)
        {
            cars = repo.getAllItems().stream().sorted(Comparator.comparing(Car::get_model)).collect(Collectors.toList());

        } else if (type == 2) {
            cars = repo.getAllItems().stream().sorted(Comparator.comparing(Car::getFuel_type)).collect(Collectors.toList());
        }
        for (Car car : cars)
        {
            System.out.println(car.toString());
        }
    }
    public void filterByFuelType(int type, IRepository<Integer,Car> repo)
    {
        List<Car> filtered_cars=new ArrayList<>();
        if(type==1){
            filtered_cars=repo.getAllItems().stream().filter(car -> car.getFuel_type()== Fuel_Type.DIESEL).collect(Collectors.toList());
        } else if (type == 2) {
            filtered_cars=repo.getAllItems().stream().filter(car -> car.getFuel_type()==Fuel_Type.GAS).collect(Collectors.toList());
        } else{
            filtered_cars=repo.getAllItems().stream().filter(car -> car.getFuel_type()==Fuel_Type.HYBRID).collect(Collectors.toList());
        }
        for(Car car : filtered_cars)
        {
            System.out.println(car.toString());
        }
    }
    public List<Car> filterByAvability(IRepository<Integer,Car> repo)
    {
        List<Car> filteredCars=new ArrayList<>();
        filteredCars = repo.getAllItems().stream().filter(Car::get_status).collect(Collectors.toList());
        for(Car car : filteredCars)
        {
            System.out.println(car.toString());
        }
        return filteredCars;
    }
    public List<Car> filterByModel(String model, IRepository<Integer,Car> repo)
    {
        List<Car> filteredCars = repo.getAllItems().stream().filter(car -> car.get_model()==model).collect(Collectors.toList());
        for(Car car : filteredCars)
        {
            System.out.println(car.toString());
        }
        return filteredCars;
    }
    public void filterReservationByDate(String boundryDate, int var, IRepository<Integer,Reservation> repo)
    {
        List<Reservation> revs;
        if(var == 1)
        {
             revs = repo.getAllItems().stream().filter(reservation -> compare_dates(boundryDate,reservation.getDate_of_order())=="<"||compare_dates(boundryDate,reservation.getDate_of_order())=="=").collect(Collectors.toList());

        }
        else if(var == 2){
            revs = repo.getAllItems().stream().filter(reservation -> compare_dates(boundryDate, reservation.getDate_of_order())==">"||compare_dates(boundryDate, reservation.getDate_of_order())=="=").collect(Collectors.toList());

        }
        else {
            revs = repo.getAllItems().stream().filter(reservation -> compare_dates(boundryDate,reservation.getDate_of_order())=="=").collect(Collectors.toList());

        }
        for (Reservation rev : revs)
        {
            System.out.println(rev.toString());
        }
    }
    public List<Reservation> show_upcoming_reservations(IRepository<Integer,Reservation> repo,Integer userId)
    {
        String current_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<Reservation> upcoming = repo.getAllItems().stream().filter(reservation -> (Objects.equals(compare_dates(current_date, reservation.getDate_of_order()), "<") || Objects.equals(compare_dates(current_date, reservation.getDate_of_order()),"="))&&reservation.getUserid() == userId).collect(Collectors.toList());
        upcoming = upcoming.stream().sorted(Comparator.comparing(Reservation::getDate_of_order)).collect(Collectors.toList());
        for(Reservation rev : upcoming)
        {
            System.out.println(rev.toString());
        }
        return upcoming;
    }
    public void showTodaysReservations(IRepository<Integer,Reservation> repo){
        String current_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<Reservation> todaysreservations = repo.getAllItems().stream().filter(reservation -> Objects.equals(current_date,reservation.getDate_of_order())).collect(Collectors.toList());
        if(!todaysreservations.isEmpty()) {
            System.out.println("Today's reservations are:");
            for (Reservation rev : todaysreservations) {
                System.out.println(rev.toString());
            }
        }
        else {
            System.out.println("Today there weren't found any reservations!");
        }
    }

    public String compare_dates(String date1_, String date2_ )
    {
        String[] date1=date1_.split("-");
        String[] date2 = date2_.split("-");
        int day2 = Integer.parseInt(date2[0]);
        int month2 = Integer.parseInt(date2[1]);
        int year2 = Integer.parseInt(date2[2]);
        int day1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int year1 = Integer.parseInt(date1[2]);
        if(year1<year2){
            return "<";
        } else if (year1>year2) {
            return ">";
        }
        if(month1<month2)
            return "<";
        else if (month1>month2) {
            return ">";
        }
        if(day1>day2)
            return ">";
        else if (day1 < day2) {
            return "<";
        }
        return "=";
    }

}
