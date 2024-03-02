package repository;
import java.io.*;
import java.util.List;

import domain.Car;
import domain.Identifiable;

public abstract class FileRepository<U,T extends Identifiable<U>>extends MemoryRepo<U,T> {
    protected String Filename;
    public FileRepository()
    {
        Filename = "";
        readFromFile();
    }
    public FileRepository( String filename)
    {
        Filename = filename;
        readFromFile();
    }

    public void setFilename(String input_filename)
    {
        Filename = input_filename;
    }
    public String getFilename()
    {
        return Filename;
    }
    protected abstract void readFromFile();

    protected abstract void writeToFile();

    @Override
    public void addItem(U id,T item)
    {
        super.addItem(id,item);
        writeToFile();
    }
    @Override
    public void removeItem(U item)
    {
        super.removeItem(item);
        writeToFile();
    }

    public void modify(U id,T object )
    {

    }

}
