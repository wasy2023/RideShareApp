package Validators;

import domain.Reservation;

public class Reservation_Validator {
    public Reservation_Validator()
    {}
    public static void validate(Reservation rev)
    {
        if(rev == null)
            throw new IllegalArgumentException("Reservation is null!!");
        if(rev.getId()<=0)
            throw new IllegalArgumentException("Id must be higher than 0!!");
        if(rev.getNumberOfPeople()<=0)
            throw new IllegalArgumentException("There must be at least one person!");
        if(rev.getDistance()<=0)
            throw new IllegalArgumentException("The distance must be at least 1!");
    }
}
