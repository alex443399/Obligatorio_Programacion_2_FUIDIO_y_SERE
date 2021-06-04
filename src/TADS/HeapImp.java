package TADS;

import Exceptions.EmptyHeapException;

public class HeapImp<T extends Comparable<T>> implements MyHeap<T>{

    private T[] values;
    private int size;

    public HeapImp(){
        values = (T[]) new Comparable[20];
    }

    public HeapImp(int size){
        values = (T[]) new Comparable[size];
    }

    private int getFather(int position){
        return (position - 1)/2;
    }

    private int getLeftChild(int position){
        return 2*position + 1;
    }

    private int getRightChild(int position){
        return 2*position + 2;
    }

    @Override
    public void insert(T value) {
        int position = size;
        values[position] = value;

        while(position != 0 &&
                values[position].compareTo(values[getFather(position)]) > 0){
            values[position] = values[getFather(position)];
            values[getFather(position)] = value;
            position = getFather(position);
        }

        size ++;
    }

    @Override
    public T delete() throws EmptyHeapException{
        if(size == 0){
            throw new EmptyHeapException("The heap is empty");
        }

        T valueToReturn = values[0];

        if(size == 1){
            values[0] = null;
        } else{
            int position = 0;
            values[0] = values[size - 1];
            values[size - 1] = null;


            int temp = maxPosition(getLeftChild(position), getRightChild(position));

            while(position < size && values[temp] != null &&
                    values[temp].compareTo(values[position]) > 0){
                T pivot = values[position];
                values[position] = values[temp];
                values[temp] = pivot;
                position = temp;
                temp = maxPosition(getLeftChild(position), getRightChild(position));

            }


        }

        size --;
        return valueToReturn;

    }

    private int maxPosition(int position1, int position2){
        if (values[position1] == null){
            return position2;
        }

        if(values[position2] == null){
            return position1;
        }

        if(values[position1].compareTo(values[position2]) > 0){
            return position1;
        } else {
            return position2;
        }
    }

    @Override
    public T get() throws EmptyHeapException{

        if(size == 0){
            throw new EmptyHeapException("The heap is empty");
        }
        return values[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int level(int position){
        int level = 0;
        while(position != 0){
            level ++;
            position = getFather(position);
        }

        return level;
    }

    private void print(){


    }


}
