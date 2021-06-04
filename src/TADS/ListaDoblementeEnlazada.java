package TADS;

public class ListaDoblementeEnlazada implements Lista {
    private Nodo1 first;
    private Nodo1 last;
    private int size = 0;

    @Override
    public void add(Object value) {
        Nodo1 temp = new Nodo1(value);
        if(first == null){
            first = temp;
            last = first;
        }
        else{
            last.setSiguiente(temp);
            first.setAnterior(temp);
            temp.setSiguiente(first);
            temp.setAnterior(first);
            last = temp;
        }
        size += 1;
    }

    @Override
    public void remove(int position) {
        int i = 0;
        if(position == 0){
            Nodo1 temp = first.getSiguiente();
            first.setSiguiente(null);
            temp.setAnterior(null);
            first = temp;
        }
        else{
            Nodo1 temp = first;
            while(temp.getSiguiente() != null && i < position-1){
                temp = temp.getSiguiente();
                i ++;
            }
            Nodo1 elementoBorrado = temp.getSiguiente();
            Nodo1 NuevoElementoSiguiente = elementoBorrado.getSiguiente();
            temp.setSiguiente(NuevoElementoSiguiente);
            NuevoElementoSiguiente.setAnterior(temp);
            elementoBorrado.setAnterior(null);
            elementoBorrado.setSiguiente(null);
        }

        size -= 1;
    }

    @Override
    public Object get(int position) {
        int i = 0;
        Nodo1 temp = first;
        while(temp.getSiguiente() != null && i < position){
            temp = temp.getSiguiente();
            i ++;
        }
        return temp.getValue();
    }

    @Override
    public void print() {
        System.out.println("Puta");
    }

    public int size(){
        return size;
    }
}
