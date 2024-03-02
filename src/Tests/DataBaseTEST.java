package Tests;

import domain.Car;
import domain.Fuel_Type;
import domain.Reservation;
import repository.DataBase;
import repository.DataBaseCAR;
import repository.DataBaseReservation;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseTEST {
    private static DataBaseCAR dataBase=new DataBaseCAR();
    private  static DataBaseReservation dataBaseReservation = new DataBaseReservation();

    public DataBaseTEST() {
    }

    public void TestDATABASECARS()
    {
        Reservation rev = new Reservation(2,2,1,3,1);
        System.out.println("Checking elements from database");
        Car car = new Car(1,"Mazda", Fuel_Type.DIESEL);
        dataBase.fetchDataFromDataBase();
        assertEquals(dataBase.findItem(6).get_model(),car.get_model());
        System.out.println("    Test 1/1 passed");

        dataBaseReservation.addItem(rev.getId(), rev);
        assertEquals(dataBaseReservation.findItem(1),rev);
        System.out.println("    Test 2/1 passed");






    }
    public void Test_ADD(){
        Car car2 = new Car(2,"Volvo", Fuel_Type.HYBRID);

        dataBase.addItem(car2.getId(), car2);
        dataBase.updateDataInDataBase();
        assertEquals(dataBase.findItem(2).get_model(),car2.get_model());
        System.out.println("    Test 1/2 passed");
    }

}
