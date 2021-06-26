package TADS;

import Exceptions.EmptyStackException;
import Exceptions.IlegalIndexException;


public class ArrayList<T> implements Lista<T>{

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

    }

    public int size(){
        return size;
    }

    //Operaciones del Stack:

    public String[] toStringArray(){
        String[] awns = new String[size];
        for(int i = 0; i < size; i++){
            awns[i] = lista[i].toString();
        }
        return awns;

    }
}
