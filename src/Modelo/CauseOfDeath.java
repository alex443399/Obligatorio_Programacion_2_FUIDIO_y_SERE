package Modelo;

public class CauseOfDeath implements Comparable<CauseOfDeath> {

    private String name;

    public CauseOfDeath(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }

    @Override
    public int compareTo(CauseOfDeath o) {
        return 0;
    }
}
