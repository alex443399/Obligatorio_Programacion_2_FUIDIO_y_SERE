package TADS;

public class MyClosedHash<K extends Comparable<K>, T> implements MyHash<K, T>{

    private int tableHashSize;

    private ClosedHashNode<K, T>[] tableHash;

    public MyClosedHash(){
        this.tableHashSize = 100;
        tableHash =  (ClosedHashNode<K, T>[]) new ClosedHashNode[tableHashSize];
    }

    public MyClosedHash(int tableHashSize, float loadFactor){
        this.tableHashSize = tableHashSize;
        tableHash = (ClosedHashNode<K, T>[]) new ClosedHashNode[tableHashSize];
    }

    public MyClosedHash(int tableHashSize){
        this.tableHashSize = tableHashSize;
        tableHash = (ClosedHashNode<K, T>[]) new ClosedHashNode[tableHashSize];
    }


    @Override
    public void put(K key, T value) {
        ClosedHashNode<K, T> element = new ClosedHashNode<>(key, value);
        int position = hashFunction(element);
        int count = 0;
        boolean estaIngresado = false;
        while(tableHash[position] != null && !tableHash[position].isEmpty()){
            count ++;
            if(tableHash[position].getKey().equals(key)){
                tableHash[position].setReps(tableHash[position].getReps() + 1);
                estaIngresado = true;
                //System.out.println("Encontre un repetido");
                break;

            }

            if((position + count*key.hashCode()) % (tableHashSize) >= 0) {
                position = (position + count*key.hashCode()) % (tableHashSize); // Cuadratic Proving
            } else{
                position = - (position + count*key.hashCode()) % (tableHashSize);
            }


        }

        if(!estaIngresado) {
            tableHash[position] = element;
        }



    }

    public void Bubblesort(int recorridas){

        for (int j = 0; j < recorridas; j++){

            for (int i = 0; i < tableHashSize - j - 1; i++) {
                if ((tableHash[i] != null && tableHash[i+1] == null) ||
                        (tableHash[i] != null && tableHash[i+1] != null
                                && tableHash[i].getReps() > tableHash[i+1].getReps())){

                    ClosedHashNode<K, T> pivot = new ClosedHashNode<>(tableHash[i].getKey(),
                            tableHash[i].getValue());

                    pivot.setReps(tableHash[i].getReps());

                    tableHash[i] = tableHash[i + 1];
                    tableHash[i + 1] = pivot;

                }
            }
        }

    }

    @Override
    public T get(K value) {
        return null;
    }

    public ClosedHashNode<K, T> getPosition(int position){
        return tableHash[position];
    }

    @Override
    public void delete(K value) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(K key, T value) {
        return false;
    }

    private int hashFunction(ClosedHashNode<K, T> element){
        return element.hashCode() % tableHashSize;
    }

    public int getTableHashSize() {
        return tableHashSize;
    }

}
