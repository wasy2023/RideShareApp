package Tests;
import domain.Car;
import domain.Fuel_Type;
import domain.Reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Reservation_tests {
    private Reservation rev = new Reservation(45,2,1,null,null);
    public void test_create()
    {
        assertEquals(rev.getId(),1);
        assertEquals(rev.getDistance(),45);
        assertEquals(rev.getNumberOfPeople(),2);
    }
    public void test_update()
    {
        rev.update_reservation(30,3);
        assertEquals(rev.getNumberOfPeople(),3);
        assertEquals(rev.getDistance(),30);
    }
    public void test_setters()
    {
        rev.setDistance(45);
        rev.setPeople(1);
        rev.setId(2);
        assertEquals(rev.getDistance(),45);
        assertEquals(rev.getId(),2);
        assertEquals(rev.getNumberOfPeople(),1);
    }
    public void Run()
    {
        System.out.println("Running Reservation tests");
        test_create();
        System.out.println("    ->create test passed");
        test_setters();
        System.out.println("    ->setters test passed");
        test_update();
        System.out.println("    ->update test passed");
    }
    public Reservation_tests()
    {}


}
