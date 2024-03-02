package domain;

import java.io.Serializable;

public interface  Identifiable <T>{

    public T getId();
    public void setId(T id);
}
