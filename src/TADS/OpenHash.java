package TADS;

public class OpenHash<K extends Comparable<K>, T> implements MyHash<K, T>{// Hacer lo q estoy haciendo o UN ARRAYLIST DE LINKEDLIST???

    private int tableHashSize;
    private OpenHashNode<K,T>[] HashTable;//ArrayList<OpenHashNode<K,T>> hashTable = new ArrayList<OpenHashNode<K,T>>(tableHashSize);
    private int size;

    public OpenHash(int tableHashSize){
        this.tableHashSize = tableHashSize;
        HashTable = (OpenHashNode<K,T>[]) new OpenHashNode  [tableHashSize];
    }


    @Override
    public void put(K key, T value) {

        OpenHashNode<K, T> element = new OpenHashNode(key, value);
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

    public OpenHashNode<K,T> getNode(K key) {
        int position = hashFunction(key);
        OpenHashNode<K, T> temp = HashTable[position];
        return temp;

    }

    public OpenHashNode<K, T> getPosition(int position){
        return HashTable[position];

    }

    @Override
    public void delete(K key) {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(K key, T value) {
        return false;
    }

    public int hashFunction(K key){
        int value = (key.hashCode()) % tableHashSize;
        if(value < 0)
            value+=tableHashSize;
        return value;
    }

    public int getTableHashSize() {
        return tableHashSize;
    }
}
