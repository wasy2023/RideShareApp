package domain;
import java.io.Serializable;
import java.util.Objects;
import domain.Fuel_Type;

public class Car implements Identifiable<Integer>, Serializable {

    Integer id;
    private String car_model;
    private boolean active_status;
    private Fuel_Type fuel_type;
    public Car (int id1, String model,Fuel_Type fuel,boolean status)
    {
        id = id1;
        car_model = model;
        fuel_type = fuel;
        active_status = status;
    }
    public Car (int id1, String model,Fuel_Type fuel)
    {
        id = id1;
        car_model = model;
        fuel_type = fuel;
        active_status = true;
    }
    public Car()
    {
        id = 0;
        car_model = "";
        active_status = true;
        fuel_type = Fuel_Type.GAS;
    }
    public Fuel_Type getFuel_type()
    {
        return fuel_type;
    }
    public void setFuel_type(Fuel_Type fuel)
    {
        fuel_type = fuel;
    }
    public boolean get_status()
    {
        return active_status;
    }
    public String get_model()
    {
        return car_model;
    }
    public void set_status(boolean status)
    {
        active_status = status;
    }
    public void setCar_model(String car_model1)
    {
        car_model = car_model1;
    }
    public void update(String model){
        this.car_model = model;
    }
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", car_model=" + car_model  +
                ", active_status=" + active_status +
                ", fuel_type=" + fuel_type  +
                '}';
    }
    public static Car parseOfString(String string){
        String[] parts = string.split(", ");
        int id = Integer.parseInt(parts[0].substring(parts[0].indexOf("=") + 1));
        String carModel = parts[1].substring(parts[1].indexOf("=") + 1);
        boolean activeStatus = Boolean.parseBoolean(parts[2].substring(parts[2].indexOf("=") + 1));
        String fuelType = parts[3].substring(parts[3].indexOf("=") + 1, parts[3].length() - 1);

        return new Car(id,carModel,Fuel_Type.valueOf(fuelType),activeStatus);

    }

    @Override
    public Integer getId()
    {
        return id;
    }
    @Override
    public void setId(Integer id1)
    {
        id = id1;
    }


}
