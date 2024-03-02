package Tests;
import domain.Car;
import domain.Fuel_Type;
import repository.CarRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class CarRepo_tests {
    private CarRepository rep = new CarRepository();
    public void create_test()
    {
        Car car = new Car(1,"Prius",Fuel_Type.HYBRID);
        rep.addItem(car.getId(),car);
        assertEquals(rep.findItem(1).getId(),1);
        assertEquals(rep.findItem(1).get_model(),"Prius");
        assertEquals(rep.findItem(1).getFuel_type(), Fuel_Type.HYBRID);
    }
    public void update_test()
    {
        rep.Update_car(1,"Mazda");
        assertEquals(rep.findItem(1).get_model(),"Mazda");
    }
    public void remove_test()
    {
        rep.removeItem(7);
        assertEquals(rep.findItem(7),null);
    }
    public void Run()
    {
        create_test();
        System.out.println("    -> create tests passed");
        update_test();
        System.out.println("    -> update tests passed");
        remove_test();
        System.out.println("    -> remove tests passed");
    }
    public CarRepo_tests()
    {}


}
