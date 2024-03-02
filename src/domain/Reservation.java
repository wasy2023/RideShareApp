package domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Reservation implements Identifiable<Integer>, Serializable {

    Integer id;
    private int distance;
    private int numberOfPeople;
    private String  date_of_order;
    private Integer userid;
    private Integer CarID;
    private String TimeOfOrder;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", carId=" + CarID +
                ", date_of_order=" + date_of_order  +
                ", userid=" + userid +
                ", TimeOfOrder=" + TimeOfOrder  +
                '}';
    }

    public Integer getCarID() {
        return CarID;
    }

    public void setCarID(Integer carID) {
        CarID = carID;
    }

    public String getDate_of_order() {
        return date_of_order;
    }

    public void setDate_of_order(String date_of_order) {
        this.date_of_order = date_of_order;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTimeOfOrder() {
        return TimeOfOrder;
    }

    public Reservation(int distance, int numberOfPeople1, Integer id1, Integer userid, String reservation_date, String reservationTime, Integer carID )
    {
        this.userid = userid;
        this.CarID = carID;
        this.distance = distance;
        numberOfPeople = numberOfPeople1;
        id = id1;
        date_of_order = reservation_date;
        TimeOfOrder = reservationTime;

        //TODO: Implement ONE car has MANY reservations(in different dates) and ONE reservation has only ONE car
    }
    public Reservation(int distance, int numberOfPeople1, Integer id1, Integer userid, Integer carID )
    {
        this.CarID = carID;
        this.distance = distance;
        numberOfPeople = numberOfPeople1;
        id = id1;
        this.userid = userid;
        date_of_order =LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        TimeOfOrder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static Reservation parseOfString(String string){
        String[] parts = string.split(", ");
        int id = Integer.parseInt(parts[0].substring(parts[0].indexOf("=") + 1));
        int distance = Integer.parseInt(parts[1].substring(parts[1].indexOf("=") + 1));
        int numberOfPeople = Integer.parseInt(parts[2].substring(parts[2].indexOf("=") + 1));
        String dateOfOrder = parts[3].substring(parts[3].indexOf("=") + 1);
        int userid = Integer.parseInt(parts[4].substring(parts[4].indexOf("=") + 1));
        int carID = Integer.parseInt(parts[5].substring(parts[5].indexOf("=") + 1));
        String timeOfOrder = parts[6].substring(parts[6].indexOf("=") + 1, parts[6].length() - 1);

        return new Reservation(distance,numberOfPeople,id,userid,dateOfOrder,timeOfOrder,carID);

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
    public void setDistance(int new_distance )
    {
        distance = new_distance;
    }
    public int getDistance()
    {
        return distance;
    }
    public void setPeople(int nr)
    {
        numberOfPeople = nr;
    }
    public int getNumberOfPeople()
    {
        return numberOfPeople;
    }

    public void update_reservation(int new_distance, int new_number_of_people)
    {
        distance = new_distance;
        numberOfPeople = new_number_of_people;

    }
}
