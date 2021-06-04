package TADS;

public class MyClosedHash<K, T> implements MyHash<K, T>{

    private final int DEFAULT_INITIAL_TABLE_HASH_SIZE = 10;

    private ClosedHashNode[] tableHash;

    public MyClosedHash(){
        tableHash = new ClosedHashNode[DEFAULT_INITIAL_TABLE_HASH_SIZE];
    }

    public MyClosedHash(int tableHashSize, float loadFactor){
        tableHash = new ClosedHashNode[tableHashSize];
    }


    @Override
    public void put(K key, T value) {

    }

    @Override
    public T get(K key) {
        return null;
    }

    @Override
    public void delete(K key) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains() {
        return false;
    }
}
