package TADS;

public interface MyHash<K, T>{

    void put(K key, T value);
    T get(K key);
    void delete(K key);
    int size();
    boolean contains(K key, T value);

}
