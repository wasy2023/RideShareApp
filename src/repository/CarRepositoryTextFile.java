package repository;

import domain.Car;
import domain.Fuel_Type;
import domain.Identifiable;

import java.io.*;
import java.util.List;

public class CarRepositoryTextFile extends FileRepository<Integer,Car> {
    public CarRepositoryTextFile(String filename) {super(filename);}

    @Override
    protected void readFromFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(Filename)))
        {
            String line = null;
            while((line = reader.readLine())!=null)
            {
                Car car = Car.parseOfString(line);
                Repo.put(car.getId(), car);
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try(FileWriter writer = new FileWriter(Filename,false))
        {
            int size = Repo.size();
            int i=0;
            for(Car car : Repo.values())
            {
                if(i!=size-1)
                writer.write(car.toString() + "\n");
                else
                    writer.write(car.toString());
                i++;
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
