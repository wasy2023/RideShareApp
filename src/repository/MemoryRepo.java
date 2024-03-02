package repository;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import domain.Fuel_Type;
import domain.Identifiable;

import java.util.ArrayList;

//public class MemoryRepo <T extends Identifiable, U> implements IRepository<T,U> {
public class MemoryRepo<U,T extends Identifiable> implements IRepository<U,T> {
    protected HashMap<U,T> Repo;
    public Fuel_Type type(String word)
    {
        if(word =="GAS")
        {
            return Fuel_Type.GAS;
        } else if (word == "HYBRID") {
            return Fuel_Type.HYBRID;

        }
        else return Fuel_Type.DIESEL;
    }
    public String reversed_type(Fuel_Type word)
    {
        if(word == Fuel_Type.GAS)
        {
            return "GAS";
        }
        else
            if(word == Fuel_Type.HYBRID)
            {
                return "HYBRID";
            }
            else return "DIESEL";
    }
    public MemoryRepo() {
        Repo = new HashMap<>();
    }

    @Override
    public void addItem(U key, T obj) {
        Repo.put(key,obj);
    }

    @Override
    public void removeItem(U key) {
        Repo.remove(key);
    }

    @Override
    public Collection<T> getAllItems() {
        return Repo.values();
    }

    @Override
    public int getNumberOfItems()
    {
       return Repo.size();
    }

    @Override
    public T findItem(U key) {
        return Repo.get(key);
    }
}