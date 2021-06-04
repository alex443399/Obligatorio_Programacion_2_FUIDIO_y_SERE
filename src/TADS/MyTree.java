package TADS;

import Exceptions.EmptyStackException;

public interface MyTree<K, T> {

    T find(K key) throws EmptyStackException;
    void insert(K key, T data, K parentKey);
    void delete(K key);
    Lista<K> inOrder();
    Lista<K> preOrder();
    Lista<K> postOrder();
    int size();
    int countLeaf();
    int countCompleteElements();
    Lista<K> porNivel();

}
