package Modelo;

public class CauseOfDeath{

    private String name;

    public CauseOfDeath(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof CauseOfDeath))
            return false;
        CauseOfDeath c = (CauseOfDeath) o;
        return c.toString().equals(this.toString());
    }

    public String getName() {
        return name;
    }
}
