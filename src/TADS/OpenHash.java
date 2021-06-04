package TADS;

public class OpenHash<K extends Comparable<K>, T> implements MyHash<K, T>{

    private int tableHashSize;
    private OpenHashNode<K, T>[] HashTable;
    private int size;

    public OpenHash(int tableHashSize){
        this.tableHashSize = tableHashSize;
        HashTable = (OpenHashNode<K, T>[]) new Object[tableHashSize];

    }


    @Override
    public void put(K key, T value) {

        OpenHashNode<K, T> element = new OpenHashNode<>(key, value);
        int position = hashFunction(key);
        if(HashTable[position] == null){
            HashTable[position] = element;
        } else{
            OpenHashNode<K, T> temp = HashTable[position];
            while(temp.getNext() != null){
                temp = temp.getNext();
            }

            temp.setNext(element);

        }

        size ++;

    }

    @Override
    public T get(K key) {
        int position = hashFunction(key);
        OpenHashNode<K, T> temp = HashTable[position];
        while(temp != null && !temp.getKey().equals(key)){
            temp = temp.getNext();
        }

        if(temp != null){
            return temp.getValue();
        }

        return null;

    }

    @Override
    public void delete(K key) {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains() {
        return false;
    }

    private int hashFunction(K key){

        return tableHashSize % key.hashCode();
    }


}
