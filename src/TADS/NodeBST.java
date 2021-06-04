package TADS;

public class NodeBST<K extends Comparable<K>, T>{

    private K key;
    private T data;
    private NodeBST<K, T> LeftChild;
    private NodeBST<K, T> RightChild;

    public NodeBST(K key, T data){
        this.key = key;
        this.data = data;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setData(T data) {
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public NodeBST<K, T> getLeftChild() {
        return LeftChild;
    }

    public void setLeftChild(NodeBST<K, T> leftChild) {
        LeftChild = leftChild;
    }

    public NodeBST<K, T> getRightChild() {
        return RightChild;
    }

    public void setRightChild(NodeBST<K, T> rightChild) {
        RightChild = rightChild;
    }
}
