package TADS;

public class MyClosedHash<K, T> implements MyHash<K, T>{

    private int tableHashSize;

    private ClosedHashNode<K, T>[] tableHash;

    public MyClosedHash(){
        this.tableHashSize = 100;
        tableHash =  (ClosedHashNode<K, T>[]) new ClosedHashNode[tableHashSize];
    }

    public MyClosedHash(int tableHashSize, float loadFactor){
        this.tableHashSize = tableHashSize;
        tableHash = (ClosedHashNode<K, T>[]) new ClosedHashNode[tableHashSize];
    }


    @Override
    public void put(K key, T value) {
        ClosedHashNode element = new ClosedHashNode(key, value);
        int position = hashFunction(element);
        int count = 0;
        while(position < tableHashSize && tableHash[position] != null && !tableHash[position].isEmpty()){
            count ++;
            position = tableHashSize % (position + count*count);

        }

        tableHash[position] = element;

    }

    @Override
    public T get(K value) {
        return null;
    }

    @Override
    public void delete(K value) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(K key, T value) {
        return false;
    }

    private int hashFunction(ClosedHashNode<K, T> element){
        return tableHashSize % element.hashCode();
    }
}
