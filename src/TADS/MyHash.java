package TADS;

public interface MyHash<T extends Comparable<T>>{

    void put(T value);
    T get(T value);
    void delete(T value);
    int size();
    boolean contains();
}
