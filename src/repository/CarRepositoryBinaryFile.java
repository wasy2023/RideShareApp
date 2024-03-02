package repository;
import domain.Car;

import java.util.HashMap;
import java.io.*;

public class CarRepositoryBinaryFile extends FileRepository<Integer,Car>{

    public CarRepositoryBinaryFile(String filename) {super(filename);}
    @Override
    protected void writeToFile() {

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(Filename)))
        {
            for(Car car : Repo.values())
            {
                objectOutputStream.writeObject(car);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void readFromFile() {

       try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(getFilename())))
       {
            Repo =(HashMap<Integer,Car>) objectInputStream.readObject();
       }
       catch(IOException | ClassNotFoundException e)
       {
           e.printStackTrace();
       }
    }
}

