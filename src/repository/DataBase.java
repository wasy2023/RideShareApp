package repository;

import domain.Identifiable;
import repository.MemoryRepo;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public abstract class DataBase<U,T extends Identifiable<U>> extends MemoryRepo<U,T> {
    protected String URL;

    public DataBase() {
        this.URL = "jdbc:sqlite:C:/Users/wasy/IdeaProjects/a4-VasiAlexandru/data/data.db";
    }

    public abstract void fetchDataFromDataBase();
    public abstract void putDataInDataBase(T item);
    public abstract void updateDataInDataBase();
    public abstract void removeDataFromDB(U id);
    @Override
    public void addItem(U id,T item)
    {
        super.addItem(id,item);
        putDataInDataBase(item);
    }
    @Override
    public void removeItem(U id)
    {
        super.removeItem(id);
        removeDataFromDB(id);

    }
}
