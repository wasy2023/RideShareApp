package repository;

import domain.Car;
import domain.Fuel_Type;

import java.util.*;

public class CarRepository extends MemoryRepo<Integer,Car>{


    private HashMap<Car,Integer> cars;
    private Integer number_of_cars;

    public void setNOC(int noc)
    {
        number_of_cars = noc;
    }
    public Integer getNOC()
    {
        return number_of_cars;
    }

    public CarRepository()
    {
        cars = new HashMap<>();
        number_of_cars = 0;

    }

    public void Update_car(int id, String new_model)
    {
       Car car = findItem(id);
       car.setCar_model(new_model);


    }

}
