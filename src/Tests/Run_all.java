package Tests;
import Tests.Car_tests;
public class Run_all {
    public Run_all(){}
    public static void main(String[] args)
    {
        Car_tests car = new Car_tests();
        CarRepo_tests carRepo = new CarRepo_tests();
        Reservation_tests rev = new Reservation_tests();
        ReservationRepo_tests revRepo = new ReservationRepo_tests();
        DataBaseTEST DB_tests =new DataBaseTEST();
        car.Run_Car();
        System.out.println("Car object tests run successfully");
        carRepo.Run();
        System.out.println("Car repo tests run successfully");
        rev.Run();
        System.out.println("Reservation object tests run successfully");
        revRepo.Run();
        System.out.println("Reservation repo tests run successfully!");
        DB_tests.TestDATABASECARS();
        System.out.println("All test run successfully!!!");
    }
}
