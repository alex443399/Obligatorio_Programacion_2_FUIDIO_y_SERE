package TADS;

public class ClosedHashNode<T extends Comparable<T>>{

    private T value;
    private boolean empty;

    public ClosedHashNode(T value){

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
}
