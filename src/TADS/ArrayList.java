package TADS;

import Exceptions.EmptyStackException;
import Exceptions.IlegalIndexException;


public class ArrayList<T> implements Lista<T>, MyStack<T> {

    private T[] lista;//(T[]) new Object[50];
    private int size = 0;
    private int initial_size;

    public ArrayList(){
        this.initial_size = 50;//initial_size;
        lista = (T[]) new Object[50];
    }
    public ArrayList(int initial_size){
        this.initial_size = initial_size;
        lista = (T[]) new Object[50];
    }

    @Override
    public void add(T value) {
        if(size < lista.length){
            lista[size] = value;
        } else{
            Object[] temp = new Object[lista.length*2];
            for(int i = 0; i < lista.length; i++){
                temp[i] = lista[i];
            }
            lista = (T[]) temp;
            lista[size] = value;
        }

        size ++;
    }

    @Override
    public void remove(int position) throws IlegalIndexException {
        if(position >= size){
            throw new IlegalIndexException("Invalid position");
        }

        lista[position] = null;
        int count = position;
        while (count < size-1){
            lista[count] = lista[count+1];
            count ++;
        }

        size --;
    }

    @Override
    public T get(int position) throws IlegalIndexException {
        if (position >= size){
            throw new IlegalIndexException("Invalid position");
        }

        return lista[position];
    }

    @Override
    public void print() {
        System.out.println("Puta");
    }

    public int size(){
        return size;
    }

    //Operaciones del Stack:


    @Override
    public void pop() throws EmptyStackException {
        if(size == 0){
            throw new EmptyStackException("Stack is empty!");
        }

        lista[size - 1] = null;
        size --;
    }

    @Override
    public T top() throws EmptyStackException {
        if(size == 0){
            throw new EmptyStackException("Stack is empty");
        }

        return lista[size - 1];
    }

    @Override
    public void push(Object element) {
        if(size < lista.length){
            lista[size] = (T) element;
        } else{
            T[] temp = (T[]) new Object[lista.length*2];
            for(int i = 0; i < lista.length; i++){
                temp[i] = lista[i];
            }
            lista = temp;
            lista[size] = (T) element;
        }

        size ++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void makeEmpty() {
        for(int i = 0; i < size; i ++){
            lista[i] = null;
        }
    }

    public String[] toStringArray(){
        String[] awns = new String[size];
        for(int i = 0; i < size; i++){
            awns[i] = lista[i].toString();
        }
        return awns;

    }
}
