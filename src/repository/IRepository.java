package repository;

import domain.Identifiable;

import java.util.Collection;
public interface IRepository<U,T extends Identifiable> {
public void addItem(U id, T obj);
public void removeItem(U id);
public T findItem(U id);
public Collection<T> getAllItems();
public int getNumberOfItems();
}
