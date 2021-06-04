package TADS;

public class MyBSTImpl<K extends Comparable<K>, T> implements MyBinarySearchTree<K, T>{

    private NodeBST<K, T> root;
    int size;

    @Override
    public T get(K key) {
        return getWithRoot(key, root);
    }

    @Override
    public void insert(K key, T data) {
        root = insertWithRoot(key, data, root);
        size ++;
    }

    @Override
    public void delete(K key) {
        root = deleteWithRoot(key, root);
        size --;
    }

    @Override
    public int size() {
        return size;
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

    private T getWithRoot(K key, NodeBST<K, T> raiz){
        //System.out.println(raiz.getData());
        if(raiz.getKey().compareTo(key) == 0){
            return raiz.getData();
        }
        else if(raiz.getKey().compareTo(key) < 0){  //Key de la raiz es menor que key buscada
            if(raiz.getRightChild() != null) {
                return getWithRoot(key, raiz.getRightChild());
            }
        }
        else if(raiz.getKey().compareTo(key) > 0){
            if(raiz.getLeftChild() != null) {
                return getWithRoot(key, raiz.getLeftChild());
            }
        }


        return null;
    }

    private NodeBST<K, T> insertWithRoot(K key, T data, NodeBST<K, T> raiz){


        if(raiz == null){
            return new NodeBST<>(key, data);
        }

        if(raiz.getKey().compareTo(key) > 0){
            raiz.setLeftChild(insertWithRoot(key, data, raiz.getLeftChild()));
        }

        else if(raiz.getKey().compareTo(key) < 0){
            raiz.setRightChild(insertWithRoot(key, data, raiz.getRightChild()));
        }

        return raiz;

    }


    private NodeBST<K, T> findMin(NodeBST<K, T> raiz){

        NodeBST<K, T> temp = raiz.getLeftChild();

        while(temp.getLeftChild() != null){
            temp = temp.getLeftChild();
        }

        return temp;

    }

    private NodeBST<K, T> deleteWithRoot(K key, NodeBST<K, T> raiz){

        if(raiz == null){
            return null;
        }

        if(raiz.getKey().compareTo(key) > 0){
            raiz.setLeftChild(deleteWithRoot(key, raiz.getLeftChild()));
        }

        else if(raiz.getKey().compareTo(key) < 0){
            raiz.setRightChild(deleteWithRoot(key, raiz.getRightChild()));
        }

        else{
            if(raiz.getLeftChild() == null){
                return raiz.getRightChild();
            }

            else if(raiz.getRightChild() == null){
                return raiz.getLeftChild();
            }

            NodeBST<K, T> temp = findMin(raiz.getRightChild());

            raiz.setKey(temp.getKey());
            raiz.setData(temp.getData());

            deleteWithRoot(temp.getKey(), raiz.getRightChild());
        }

        return raiz;

    }

    private Lista<K> inOrderWithRoot(NodeBST<K, T> raiz, Lista<K> temp){
        if(raiz != null) {
            inOrderWithRoot(raiz.getLeftChild(), temp);
            temp.add(raiz.getKey());
            inOrderWithRoot(raiz.getRightChild(), temp);
        }

        return temp;
    }

    private Lista<K> preOrderWithRoot(NodeBST<K, T> raiz, Lista<K> temp){
        if(raiz != null){
            temp.add(raiz.getKey());
            preOrderWithRoot(raiz.getLeftChild(), temp);
            preOrderWithRoot(raiz.getRightChild(), temp);
        }

        return temp;
    }

    private Lista<K> postOrderWithRoot(NodeBST<K, T> raiz, Lista<K> temp){
        if(raiz != null){
            postOrderWithRoot(raiz.getLeftChild(), temp);
            postOrderWithRoot(raiz.getRightChild(), temp);
            temp.add(raiz.getKey());
        }

        return temp;
    }


}
