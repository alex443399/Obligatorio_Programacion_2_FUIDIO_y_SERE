package TADS;

import Exceptions.EmptyHeapException;

public class HeapImp<T extends Comparable<T>> implements MyHeap<T>{

    private T[] values;
    private int amount_of_elements_stored;
    private int storage_size;

    public HeapImp(){
        values = (T[]) new Comparable[20];
    }

    public HeapImp(int amount_of_data){
        double n = Math.floor(Math.log(amount_of_data)/Math.log(2))+1d; //Cantidad de niveles en el arbol
        int l = (int) Math.pow(2d,n)-1;
        storage_size = l;
        values = (T[]) new Comparable[storage_size];
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
        int position = amount_of_elements_stored;
        values[position] = value;


        boolean condition_insert_loop = position > 0;
        if(condition_insert_loop)
            condition_insert_loop = (values[position].compareTo(values[getFather(position)]) > 0);

        while(condition_insert_loop){// ERROR DE CONDICION OUT OF BOUNDS INMINENTEEE si no separamos la condicion en dos partes

            T temp = values[position];
            values[position] = values[getFather(position)];
            values[getFather(position)] = temp;

            position = getFather(position);

            condition_insert_loop = position > 0;
            if(condition_insert_loop)
                condition_insert_loop = (values[position].compareTo(values[getFather(position)]) > 0);
        }

        amount_of_elements_stored++;
    }

    @Override
    public T delete() throws EmptyHeapException{
        if(amount_of_elements_stored == 0){
            throw new EmptyHeapException("The heap is empty");
        }

        T valueToReturn = values[0];

        if(amount_of_elements_stored == 1){
            values[0] = null;
        } else{
            int position = 0;
            values[0] = values[amount_of_elements_stored - 1];
            values[amount_of_elements_stored - 1] = null;


            Integer max_child_position = maxPosition(getLeftChild(position), getRightChild(position));

            while(position < amount_of_elements_stored && values[max_child_position] != null &&
                    values[max_child_position].compareTo(values[position]) > 0){ //change

                T pivot = values[position];
                values[position] = values[max_child_position];
                values[max_child_position] = pivot;

                position = max_child_position;
                max_child_position = maxPosition(getLeftChild(position), getRightChild(position));
                if(max_child_position == null)
                    break;

            }


        }

        amount_of_elements_stored--;
        return valueToReturn;

    }

    private Integer maxPosition(int position1, int position2){

        if(position1 >= storage_size || position2 >= storage_size)
            return null;

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

        if(amount_of_elements_stored == 0){
            throw new EmptyHeapException("The heap is empty");
        }
        return values[0];
    }

    @Override
    public int size() {
        return amount_of_elements_stored;
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

    @Override
    public T pop() throws EmptyHeapException {
        T output = get();
        delete();
        return output;
    }

    @Override
    public boolean isEmpty() {
        return amount_of_elements_stored<=0;
    }

}
