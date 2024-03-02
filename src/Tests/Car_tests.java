package Tests;

import domain.Car;
import domain.Fuel_Type;

import static org.junit.jupiter.api.Assertions.*;

public class Car_tests {
    private  Car car = new Car(1,"Mazda", Fuel_Type.HYBRID);
    public Car_tests(){}
    public void test_createANDread()
    {

        assertEquals(car.getId(), 1);
        assertEquals(car.get_model(),"Mazda");
        assertEquals(car.getFuel_type(),Fuel_Type.HYBRID);
        assertTrue(car.get_status());
    }
    public void test_update()
    {
        car.setId(2);
        assertEquals(car.getId(),2);
        car.set_status(false);
        assertFalse(car.get_status());
        car.setCar_model("Prius");
        assertEquals(car.get_model(),"Prius");
        car.setFuel_type(Fuel_Type.GAS);
        assertEquals(car.getFuel_type(),Fuel_Type.GAS);
    }
    public void Run_Car()
    {
        test_createANDread();
        System.out.println("    -> create and read test passed");
        test_update();
        System.out.println("    -> update test passed");
    }
}
