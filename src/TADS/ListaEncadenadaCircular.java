package TADS;

public class ListaEncadenadaCircular implements Lista {
    private Nodo1 first;
    private int size = 0;

    @Override
    public void add(Object value) {
        if(first == null){
            first = new Nodo1(value);
            first.setAnterior(first);
            first.setSiguiente(first);
        }
        else{
            int contador = 0;
            Nodo1 temp = first;
            while(contador < size-1){
                temp = temp.getSiguiente();
                contador++;
            }
            Nodo1 agregar = new Nodo1(value);
            temp.setSiguiente(agregar);
            agregar.setAnterior(temp);
            agregar.setSiguiente(first);
            first.setAnterior(agregar);
        }
        size += 1;
    }

    @Override
    public void remove(int position) {
        int contador = 0;
        Nodo1 temp = first;
        if(position >= 0) {
            while (contador < position) {
                temp = temp.getSiguiente();
                contador++;
            }
        }else {
            while (contador < -position) {
                temp = temp.getAnterior();
                contador++;
            }
        }
        //temp = nodo que se debe borrar
        //Lamamos temp1 al nodo anterior a temp, y temp2 al siguiente
        //Para borrar temp, solo debemos conectar temp1 con temp2
        Nodo1 temp1 = temp.getAnterior();
        Nodo1 temp2 = temp.getSiguiente();
        temp1.setSiguiente(temp2);
        temp2.setAnterior(temp1);
        temp.setSiguiente(null);
        temp.setAnterior(null);
        //Si se quiere borrar el primer elemento, entonces temp2 pasa a ser el primer elemento:
        if(position % size == 0){
            first = temp2;
        }

        size -= 1;
    }

    @Override
    public Object get(int position) {
        int contador = 0;
        Nodo1 temp = first;
        if(position >= 0) {
            while (contador < position) {
                temp = temp.getSiguiente();
                contador++;
            }
        }else {
            while (contador < -position) {
                temp = temp.getAnterior();
                contador++;
            }
        }

        return temp.getValue();
    }

    @Override
    public void print() {

    }

    public int size(){
        return size;
    }

    public void imprimir(){
        System.out.print("[");
        for(int i = 0; i < size-1; i++){
            System.out.print(get(i) + ", ");
        }
        System.out.print(get(size-1) + "]");
        System.out.println();
    }

}
