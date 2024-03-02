package repository;

import domain.Reservation;
import domain.Car;

import java.io.*;

public class ReservationRepoTextFile extends FileRepository<Integer,Reservation> {
    public ReservationRepoTextFile(String filename) { super(filename);}
    @Override
    protected void readFromFile() {
    try(BufferedReader reader = new BufferedReader(new FileReader(Filename)))
    {
        //Input format for the file is : car_id,car_model,fuel_type,duration,number_of_people,rev_id
        String line=null;
        while((line=reader.readLine())!=null)
        {
            Reservation rev = Reservation.parseOfString(line);
            this.addItem(rev.getId(),rev);
        }
    }
    catch(IOException e){
        throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        int i = 0;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Filename)))
        {
            for(Reservation rev: getAllItems())
                if(i!= getAllItems().size()-1)
                    writer.write(rev.toString() + "\n");
            else
                    writer.write(rev.toString() );
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
