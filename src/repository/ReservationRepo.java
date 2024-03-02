package repository;
import domain.Car;
import domain.Fuel_Type;
import domain.Reservation;

import java.util.*;

public class ReservationRepo extends MemoryRepo<Integer,Reservation> {

    private HashMap<Reservation,Integer> Reservations;
    private Integer number_of_reservations;
    public ReservationRepo()
    {
        Reservations = new HashMap<>();
        number_of_reservations = 0;
    }
    public void setReservations(Integer new_Number)
    {
        number_of_reservations = new_Number;
    }
    public Integer getNumber_of_reservations()
    {
        return number_of_reservations;
    }
     public void updateReservation(int id,int new_duration, int new_number_of_people,Car new_car,Integer userID){
        boolean updated = false;
        for(Reservation rev : getAllItems())
        {
            if(rev.getId() == id)
            {

                rev.update_reservation(new_duration,new_number_of_people);
                System.out.println("Updated details:");
                System.out.println("New duration set to: " + new_duration);
                System.out.println("New capacity increased to: " + new_number_of_people);
                System.out.println("New car set to: " + new_car.get_model() + " id: " + new_car.getId());
                updated = true;
            }
        }
        if(!updated)
        {
            Reservation new_rev = new Reservation(new_duration,new_number_of_people,number_of_reservations,userID,new_car.getId());
            Reservations.put(new_rev,number_of_reservations);
            number_of_reservations++;
        }
    }

    public void cancel_reservation(int id){
        boolean canceled=false;
        Iterator<Reservation> iterator = getAllItems().iterator();
        System.out.println("pace 1");
        while(iterator.hasNext())
        {
            System.out.println("pace 2");
            Reservation rev = iterator.next();
            if(rev.getId() == id)
            {
                canceled = true;
                iterator.remove();
                number_of_reservations--;
                System.out.println("Successfully removed reservation for " + " with the revid:" + id);
                break;
            }
        }
        if(!canceled)
        {
            System.out.println("Reservation not found");
        }
    }
}
