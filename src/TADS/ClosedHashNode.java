package TADS;

public class ClosedHashNode<K extends Comparable<K>, T>{

    private K key;
    private T value;
    private boolean empty;

    public ClosedHashNode(K key, T value){

        this.key = key;
        this.value = value;
    }


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.empty = false;
    }

    @Override
    public int hashCode(){
        return value.hashCode();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
}
