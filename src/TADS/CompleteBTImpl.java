package TADS;

public class CompleteBTImpl<K, T> implements CompleteBinaryTree<K, T> {

    private Node<K, T> root;

    @Override
    public T find(K key) {
        if (root == null) {
            return null;
        }


        MyQueue<Node<K, T>> lista = new ListaEnlazada<>();
        Node<K, T> temp = root;
        lista.enqueue(root);
        while (!lista.isEmpty() && !temp.getKey().equals(key)){
            temp = lista.dequeue();
            if(temp.getLeftChild() != null && temp.getLeftChild().getKey() != null){
                lista.enqueue(temp.getLeftChild());
            }

            if(temp.getRightChild() != null && temp.getRightChild().getKey() != null){
                lista.enqueue(temp.getRightChild());
            }

        }

        if(temp.getKey().equals(key)){
            return temp.getData();
        }

        return null;

    }

    @Override
    public void insert(K key, T data) {
        Node<K, T> temp = new Node<>(key, data);
        boolean insertion = false;
        if(root == null){
            root = temp;
        }
        else {

            MyQueue<Node<K, T>> lista = new ListaEnlazada<>();
            lista.enqueue(root);
            while(!insertion){
                Node<K, T> nodo = lista.dequeue();
                if(nodo.getLeftChild() == null || nodo.getLeftChild().getKey() == null){
                    nodo.setLeftChild(temp);
                    insertion = true;
                }

                else if(nodo.getRightChild() == null || nodo.getRightChild().getKey() == null){
                    nodo.setRightChild(temp);
                    insertion = true;
                }

                else{
                    lista.enqueue(nodo.getLeftChild());
                    lista.enqueue(nodo.getRightChild());
                }
            }
        }



    }

    @Override
    public void delete(K key) {

        MyQueue<Node<K, T>> lista = new ListaEnlazada<>();
        Node<K, T> temp = root;
        lista.enqueue(root);
        System.out.println(lista.isEmpty());
        System.out.println(temp.getKey() == key);

        while (!lista.isEmpty() && !temp.getKey().equals(key)){

            temp = busqueda(lista);
        }


        if(temp.getKey() ==key){
            Node<K, T> last = findLast();
            if(temp.getLeftChild() != null && temp.getLeftChild().getKey() != null){

                temp.setKey(last.getKey());
                temp.setData(last.getData());
            }

            else if(temp.getRightChild() != null && temp.getRightChild().getKey() != null){
                temp.setKey(last.getKey());
                temp.setData(last.getData());
            }
            deleteLast();

        }

    }

    private Node<K, T> findLast(){
        MyQueue<Node<K, T>> lista = new ListaEnlazada<>();
        Node<K, T> temp = root;
        lista.enqueue(root);

        while(!lista.isEmpty()){
            temp = busqueda(lista);

        }

        return temp;
    }

    private void deleteLast(){
        MyQueue<Node<K, T>> lista = new ListaEnlazada<>();
        Node<K, T> temp = root;
        lista.enqueue(root);

        while(!lista.isEmpty()){
            temp = busqueda(lista);

        }

        temp.setKey(null);

    }


    private Node<K, T> busqueda(MyQueue<Node<K, T>> lista){
        Node<K, T> temp = lista.dequeue();
        if(temp.getLeftChild() != null && temp.getLeftChild().getKey() != null){
            lista.enqueue(temp.getLeftChild());
        }

        if(temp.getRightChild() != null && temp.getRightChild().getKey() != null){
            lista.enqueue(temp.getRightChild());
        }

        return temp;
    }


}
