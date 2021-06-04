package TADS;

public class OpenHashNode<K extends Comparable<K>, T>{

    private final K key;
    private final T value;
    private OpenHashNode<K, T> next;


    public OpenHashNode(K key, T value){
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public OpenHashNode<K, T> getNext() {
        return next;
    }

    public void setNext(OpenHashNode<K, T> next) {
        this.next = next;
    }


}
