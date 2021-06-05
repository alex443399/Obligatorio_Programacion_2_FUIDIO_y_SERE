package TADS;

import Exceptions.EmptyQueueException;
import Exceptions.EmptyStackException;
import Exceptions.IlegalIndexException;


public class ListaEnlazada <T> implements Lista<T>, MyStack<T>, MyQueue<T>, MyDoubleQueue<T> {

    private Nodo<T> first;
    private Nodo<T> last;
    private int size;

    @Override
    public void add(T value) {
        Nodo<T> temp = new Nodo<T>(value);
        //Si el primero es nulo, agregamos el primer elemento
        if (first == null){
            first = temp;
            last = first;
        }
        else{
            last.setNextValue(temp);
            last = temp;
        }
        size += 1;
    }

    @Override
    public void remove(int position) throws IlegalIndexException {
        Nodo<T> temp = first;
        if(position >= size || position < 0){
            throw new IlegalIndexException("Invalid position");
        }
        if(position == 0){
            first = temp.getNextValue();
        } else{
            int contador = 0;
            while(contador < position - 1){
                temp = temp.getNextValue();
                contador ++;
            } //temp es el elemento una posicion antes del elemento a eleminar
            Nodo<T> elemento = temp.getNextValue();
            temp.setNextValue(elemento.getNextValue());
            elemento.setNextValue(null);

            if(position == size-1){
                last = temp;
            }
        }

        size -= 1;
    }

    @Override
    public T get(int position) throws IlegalIndexException {
        if(position >= size || position < 0){
            throw new IlegalIndexException("Invalid position");
        }
        Nodo<T> temp = first;
        int contador = 0;
        while(contador < position){
            temp = temp.getNextValue();
            contador ++;
        } //temp es el nodo en la posicion buscada

        return temp.getValue();
    }

    @Override
    public void print() {
        if(first != null) {
            Nodo<T> temp = first;
            System.out.print("[");
            while (temp.getNextValue() != null) {
                System.out.print(temp.getValue() + ", ");
                temp = temp.getNextValue();
            }

            System.out.print(temp.getValue() + "]");
            System.out.println();
        }
    }

    public int size(){
        return size;
    }

    public boolean estaEnLista(T elemento) {
        Nodo<T> temp = first;
        while (temp.getNextValue() != null) {
            temp = temp.getNextValue();
            if (temp.getValue() == elemento) {
                return true;
            }
        }
        return false;
    }

    public void addFirst(T value){
        Nodo<T> temp = new Nodo<T>(value);
        temp.setNextValue(first);
        first = temp;

        size ++;
    }


    //METODOS DE STACK:

    @Override
    public void pop() throws EmptyStackException {
        // Sale del stack por el principio
        Nodo<T> temp = first;

        if (first == null){
            throw new EmptyStackException("The stack is empty");
        }

        first = first.getNextValue();

        if(first == null){
            last = null;
        }

        size --;

    }

    @Override
    public T top() throws EmptyStackException {
        if (first == null){
            throw new EmptyStackException("The stack is empty");
        }
        return first.getValue();
    }

    @Override
    public void push(T element) {
        // Entra al stack por el principio:
        Nodo<T> temp = new Nodo<>(element);
        temp.setNextValue(first);
        first = temp;
        size ++;
    }


    @Override //Este método aplica tanto para Queue como para stacks:
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void makeEmpty() {
        first = null;
        last = null;
        size = 0;
    }

    //METODOS DEL QUEUE:

    @Override
    public void enqueue(T element) {
        // Entra al queue por el ultimo lugar:
        Nodo<T> temp = new Nodo<T>(element);
        if(first == null){
            first = temp;
        }
        else {

            last.setNextValue(temp);
        }
        last = temp;
        size ++;
    }

    @Override
    public T dequeue()  {
        // Sale del queue por el primer lugar:
        Nodo<T> temp = first;
        Nodo<T> primero = first.getNextValue();
        first.setNextValue(null);
        first = primero;
        size --;
        return temp.getValue();

    }


    //METODOS DE MyDoubleQueue:

    @Override
    public void enqueueLeft(T element) {
        // Entra por el primer lugar:
        addFirst(element);
    }

    @Override
    public T dequeueLeft() throws EmptyQueueException {
        // Sale por el primer lugar
        if(first == null){
            throw new EmptyQueueException("The queue is empty");
        }

        return dequeue();
    }

    @Override
    public void enqueueRight(T element) {
        // Entra al queue por el ultimo lugar:
        enqueue(element);
        size ++;
    }

    @Override
    public T dequeueRight() throws EmptyQueueException {
        // sale del queue por el ultimo lugar:
        if(isEmpty()){
            throw new EmptyQueueException("La cola esta vacía");
        }

        Nodo<T> temp = first;
        if(size == 1){
            first = null;
            return temp.getValue();
        }

        int count = 0;
        while(count < size - 2){
            temp = temp.getNextValue();
            count ++;
        } //Nos paramos un elemento anterior al ultimo:
        T data = last.getValue();
        temp.setNextValue(null);

        size --;

        return data;

    }

}

