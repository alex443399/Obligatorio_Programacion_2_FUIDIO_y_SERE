package TADS;

public interface CompleteBinaryTree<K, T>{

    T find(K key);
    void insert(K key, T data);
    void delete(K key);
}
