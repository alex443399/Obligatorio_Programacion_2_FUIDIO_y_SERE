package TADS;

public interface MyBinarySearchTree<K extends Comparable<K>, T>{

    T get(K key);
    void insert(K key, T data);
    void delete(K key);
    int size();
    Lista<K> inOrder();
    Lista<K> preOrder();
    Lista<K> postOrder();

}
