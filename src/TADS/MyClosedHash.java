package TADS;

public class MyClosedHash<T extends Comparable<T>> implements MyHash<T>{

    private int tableHashSize;

    private ClosedHashNode<T>[] tableHash;

    public MyClosedHash(){
        this.tableHashSize = 100;
        tableHash =  (ClosedHashNode<T>[]) new ClosedHashNode[tableHashSize];
    }

    public MyClosedHash(int tableHashSize, float loadFactor){
        this.tableHashSize = tableHashSize;
        tableHash = (ClosedHashNode<T>[]) new ClosedHashNode[tableHashSize];
    }


    @Override
    public void put(T value) {
        ClosedHashNode<T> element = new ClosedHashNode<T>(value);
        int position = hashFunction(element);
        int count = 0;
        while(position < tableHashSize && tableHash[position] != null && !tableHash[position].isEmpty()){
            count ++;
            position = tableHashSize % (position + count*count);

        }

        tableHash[position] = element;

    }

    @Override
    public T get(T value) {
        return null;
    }

    @Override
    public void delete(T value) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains() {
        return false;
    }

    private int hashFunction(ClosedHashNode<T> element){
        return tableHashSize % element.hashCode();
    }
}
