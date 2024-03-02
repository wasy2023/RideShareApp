package Tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import domain.Car;
import domain.Fuel_Type;
import domain.Reservation;
import repository.ReservationRepo;

public class ReservationRepo_tests {
    private ReservationRepo repo = new ReservationRepo();
    public void add_test()
    {
        Reservation rev = new Reservation(43,2,1,null,null);
        repo.addItem(rev.getId(), rev);
        assertEquals(repo.getNumberOfItems(),1);

    }
    public void update_test()
    {
        Car car = new Car(2,"Polo",Fuel_Type.GAS);
        repo.updateReservation(1,25,6,car,null);
        assertEquals(repo.findItem(1).getNumberOfPeople(),6);
        assertEquals(repo.findItem(1).getDistance(),25);
    }
    public void remove_test()
    {
        repo.removeItem(1);
        assertEquals(repo.getNumberOfItems(),0);
    }

    public void Run()
    {
        add_test();
        System.out.println("    -> adding test passed");

    }
    public ReservationRepo_tests()
    {}

}
