package Modelo;

import TADS.ListaEnlazada;

import java.util.List;

import static Utilidades.Functions.ListaEnCelda;


public class MovieCastMember {

    private String imbdNameId;
    private String imbdTitled;
    private Integer ordering;
    private String category;
    private String job;
    private ListaEnlazada<String> characters;

    public MovieCastMember(String imbdTitled, String ordering, String imbdNameId,
                           String category, String job, String characters){
        this.imbdNameId = imbdNameId;
        this.imbdTitled = imbdTitled;
        this.ordering = Integer.parseInt(ordering);
        this.category = category;
        this.job = job;
        this.characters = ListaEnCelda(",");
    }

    public String getImbdNameId() {
        return imbdNameId;
    }

    public String getImbdTitled() {
        return imbdTitled;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public String getCategory() {
        return category;
    }

    public String getJob() {
        return job;
    }

    public ListaEnlazada<String> getCharacters() {
        return characters;
    }
}
