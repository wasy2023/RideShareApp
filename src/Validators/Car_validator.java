package Validators;
import domain.Car;
public class Car_validator {
    public static void validate(Car car){
        if(car == null)
            throw new IllegalArgumentException("Car is null!");
        if(car.getId()<=0)
            throw new IllegalArgumentException("Id must be higher than 0!");
        if(car.get_model() == null)
            throw new IllegalArgumentException("Model can't be empty!");
    }
}
