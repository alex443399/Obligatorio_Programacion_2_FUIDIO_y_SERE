package TADS;

import Exceptions.IlegalIndexException;

public interface Lista <T> {
    void add(T value);
    void remove (int position) throws IlegalIndexException;
    T get(int position) throws IlegalIndexException;
    void print();

}
