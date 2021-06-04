package TADS;

import Exceptions.EmptyStackException;

public class TreeImpl<K, T> implements MyTree<K, T>{

    private Node<K, T> root;

    public TreeImpl(K rootKey, T rootData){
        root = new Node<>(rootKey, rootData);
    }


    @Override
    public T find(K key) throws EmptyStackException {
        if (root == null){
            throw new EmptyStackException("The tree is empty");
        }

        return findWhitRoot(key, root);
    }

    @Override
    public void insert(K key, T data, K parentKey) {
        root = insertWhitRoot(key, data, parentKey, root);
    }

    @Override
    public void delete(K key) {
        root = deleteWhitRoot(key, root);
    }

    @Override
    public Lista<K> inOrder() {
        Lista<K> temp = new ListaEnlazada<>();
        return inOrderWithRoot(root, temp);
    }

    @Override
    public Lista<K> preOrder() {
        Lista<K> temp = new ListaEnlazada<>();
        return preOrderWithRoot(root, temp);
    }

    @Override
    public Lista<K> postOrder() {
        Lista<K> temp = new ListaEnlazada<>();
        return postOrderWithRoot(root, temp);
    }

    @Override
    public int size() {
        return sizeWhitRoot(root);
    }

    @Override
    public int countLeaf() {
        return countLeafWhitRoot(root);

    }

    @Override
    public int countCompleteElements() {
        return countCompleteElementsWhitRoot(root);
    }

    @Override
    public Lista<K> porNivel() {
        MyQueue<Node<K, T>> temp = new ListaEnlazada<>();
        Lista<K> lista = new ListaEnlazada<>();
        temp.enqueue(root);


        while(!temp.isEmpty()){
            Node<K, T> nodo = temp.dequeue();

            lista.add(nodo.getKey());
            if(nodo.getLeftChild() != null){
                temp.enqueue(nodo.getLeftChild());
            }

            if(nodo.getRightChild() != null){
                temp.enqueue(nodo.getRightChild());
            }

        }

        return lista;
    }

    private T findWhitRoot(K key, Node<K , T> raiz){
        if(raiz == null){
            return null;
        }

        if(raiz.getKey().equals(key)){
            return raiz.getData();
        }

        T left = findWhitRoot(key, raiz.getLeftChild());
        T right = findWhitRoot(key, raiz.getRightChild());
        if(left != null){
            return left;
        }
        else if(right != null){
            return right;
        }

        return null;
    }

    private Node<K, T> insertWhitRoot(K key, T data, K parentKey, Node<K , T> raiz){
        if(raiz == null){
            return null;
        }

        Node<K, T> temp = new Node<>(key, data);
        if(raiz.getKey().equals(parentKey)){
            if(raiz.getLeftChild() == null){
                raiz.setLeftChild(temp);

            }

            else if(raiz.getRightChild() == null){
                raiz.setRightChild(temp);

            }
        }

        raiz.setLeftChild(insertWhitRoot(key, data, parentKey, raiz.getLeftChild()));
        raiz.setRightChild(insertWhitRoot(key, data, parentKey, raiz.getRightChild()));

        return raiz;
    }

    private Lista<K> inOrderWithRoot(Node<K, T> raiz, Lista<K> temp){
        if(raiz != null) {
            System.out.println("HOLA");
            inOrderWithRoot(raiz.getLeftChild(), temp);
            temp.add(raiz.getKey());
            inOrderWithRoot(raiz.getRightChild(), temp);
        }

        return temp;
    }

    private Lista<K> preOrderWithRoot(Node<K, T> raiz, Lista<K> temp){
        if(raiz != null){
            temp.add(raiz.getKey());
            preOrderWithRoot(raiz.getLeftChild(), temp);
            preOrderWithRoot(raiz.getRightChild(), temp);
        }

        return temp;
    }

    private Lista<K> postOrderWithRoot(Node<K, T> raiz, Lista<K> temp){
        if(raiz != null){
            postOrderWithRoot(raiz.getLeftChild(), temp);
            postOrderWithRoot(raiz.getRightChild(), temp);
            temp.add(raiz.getKey());
        }

        return temp;
    }

    private Node<K, T> deleteWhitRoot(K key, Node<K, T> raiz){
        if(raiz == null){
            return null;
        }
        raiz.setLeftChild(deleteWhitRoot(key, raiz.getLeftChild()));
        raiz.setRightChild(deleteWhitRoot(key, raiz.getRightChild()));

        if(raiz.getKey().equals(key)){
            if(raiz.getLeftChild() == null){
                return raiz.getRightChild();

            }

            else if(raiz.getRightChild() == null){
                return raiz.getLeftChild();
            }

            raiz.setKey(raiz.getRightChild().getKey());
            raiz.setData(raiz.getRightChild().getData());
            raiz.setRightChild(deleteWhitRoot(raiz.getKey(), raiz.getRightChild()));


        }

        return raiz;
    }

    private int sizeWhitRoot(Node<K, T> raiz){
        int count = 0;
        if(raiz != null){
            count ++;
            count = count + sizeWhitRoot(raiz.getLeftChild());
            count = count + sizeWhitRoot(raiz.getRightChild());
        }

        return count;
    }

    private int countLeafWhitRoot(Node<K, T> raiz){
        int count = 0;

        if(raiz != null) {

            if (raiz.getRightChild() == null && raiz.getLeftChild() == null) {
                count++;
            }

            count = count + countLeafWhitRoot(raiz.getLeftChild());
            count = count + countLeafWhitRoot(raiz.getRightChild());
        }

        return count;

    }

    private int countCompleteElementsWhitRoot(Node<K, T> raiz){
        int count = 0;
        if(raiz != null){
            if(raiz.getLeftChild() != null && raiz.getRightChild() != null){
                count ++;
            }

            count = count + countCompleteElementsWhitRoot(raiz.getLeftChild());
            count = count + countCompleteElementsWhitRoot(raiz.getRightChild());
        }

        return count;
    }


}
