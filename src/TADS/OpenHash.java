package TADS;

public class OpenHash<K extends Comparable<K>, T, Node> implements MyHash<K, T>{
    //Nodo = OpenHashNode<K,T>
    private int tableHashSize;
    private Node[] HashTable;//ArrayList<OpenHashNode<K,T>> hashTable = new ArrayList<OpenHashNode<K,T>>(tableHashSize);
    private int size;

    public OpenHash(int tableHashSize){
        this.tableHashSize = tableHashSize;
        HashTable = (Node[]) new Object[tableHashSize];
    }


    @Override
    public void put(K key, T value) {

        int position = hashFunction(key);
        if(HashTable[position] == null){

            Node element = (Node) new OpenHashNode<K, T>(key, value);
            HashTable[position] = element;
        } else{
            OpenHashNode<K,T> temp = (OpenHashNode<K,T>) HashTable[position];
            while(temp.getNext() != null){
                temp = temp.getNext();
            }
            OpenHashNode<K, T> element = new OpenHashNode<K, T>(key, value);
            temp.setNext(element);

        }
        size ++;
    }

    @Override
    public T get(K key) {
        int position = hashFunction(key);
        OpenHashNode<K, T> temp = (OpenHashNode<K, T>) HashTable[position];
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

        return key.hashCode() % tableHashSize;
    }


}
