package TADS;

import Exceptions.IlegalIndexException;


public class ListaEnlazada <T> implements Lista<T>{

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
        if(position >= size){
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
        if(first == null)
            return false;
        Nodo<T> temp = first;
        while (temp.getNextValue() != null) {
            temp = temp.getNextValue();
            T value = temp.getValue();
            if(value != null){
                if (value.equals(elemento)) {
                    return true;
                }
            }
            else
                return false;
        }
        return false;
    }

    public boolean contains(T value){
        boolean contain = false;
        Nodo<T> temp = first;
        while(temp != null){
            if(temp.getValue().equals(value)){
                contain = true;
                return contain;
            }
            temp = temp.getNextValue();
        }

        return contain;
    }

}

