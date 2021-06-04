package TADS;

import Exceptions.EmptyStackException;

public class Tree <K, T> implements MyTree<K, T>{

    private Node<K, T> root;


    @Override
    public T find(K key) throws EmptyStackException {
        Node<K, T> temp = root;
        MyStack <Node<K, T>> nodos = new ListaEnlazada<Node<K, T>>();
        nodos.push(temp);
        while(!nodos.isEmpty() && temp.getKey() != key){
            // Mientras tenga un hijo izquierdo, lo recorro:
            if(temp.getLeftChild() != null){
                temp = temp.getLeftChild();
                nodos.push(temp);
            } // Si no tiene un hijo izquierdo, chequeamos que tenga un hijo derecho:
            else if(temp.getRightChild() != null){
                temp = temp.getRightChild();
                nodos.push(temp);
            }
            // Si no tiene ni un hijo izquierdo ni un hijo derecho (es una hoja),
            // Sacamos al nodo del stack, nos ubicamos en su padre, y vamos (si es que existe)
            // Al hijo derecho de su padre, luego sacamos al padre del stack
            else{
                nodos.pop();
                temp = nodos.top();
                nodos.pop();
                if(temp.getRightChild() != null){
                    temp = temp.getRightChild();
                    nodos.push(temp);
                }
            }
        }

        if(temp.getKey() == key) {
            return temp.getData();
        }
        return null;
    }

    @Override
    public Lista<K> inOrder() {
        return null;
    }

    @Override
    public Lista<K> preOrder() {
        return null;
    }

    @Override
    public Lista<K> postOrder() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int countLeaf() {
        return 0;
    }

    @Override
    public int countCompleteElements() {
        return 0;
    }

    @Override
    public Lista<K> porNivel() {
        return null;
    }

    private void preorder(Node<K, T> raiz, Lista<T> lista){
        lista.add(raiz.getData());

        if(raiz.getLeftChild() != null){
            preorder(raiz.getLeftChild(), lista);
        }

        if(raiz.getRightChild() != null){
            preorder(raiz.getRightChild(), lista);
        }
    }

    @Override
    public void insert(Object key, Object data, Object parentKey) {

    }

    @Override
    public void delete(Object key) {

    }
}
