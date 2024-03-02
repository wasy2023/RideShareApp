package repository;

import domain.Reservation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ReservationRepoBinaryFile extends FileRepository<Integer,Reservation> {
    public ReservationRepoBinaryFile(String filename)
    {
        super(filename);
    }
    @Override
    protected void readFromFile() {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(getFilename()))){
            for(Reservation rev : Repo.values())
                objectOutputStream.writeObject(rev);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void writeToFile() {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(getFilename()))){
            objectOutputStream.writeObject(Repo);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
