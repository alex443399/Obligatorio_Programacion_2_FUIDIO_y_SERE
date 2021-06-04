package TADS;

public class Nodo1 {
    private Object value;
    private Nodo1 anterior;
    private Nodo1 siguiente;

    public Nodo1(Object value){
        this.value = value;
        this.anterior = null;
        this.siguiente = null;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Nodo1 getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo1 anterior) {
        this.anterior = anterior;
    }

    public Nodo1 getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo1 siguiente) {
        this.siguiente = siguiente;
    }
}
