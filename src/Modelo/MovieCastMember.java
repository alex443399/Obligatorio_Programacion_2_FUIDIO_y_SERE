package Modelo;

import java.util.List;

public class MovieCastMember {

    private String imbdNameId;
    private String imbdTitled;
    private int ordering;
    private String category;
    private String job;
    private String[] characters;

    public MovieCastMember(String imbdNameId, String imbdTitled, String ordering,
                           String category, String job, String characters){
        this.imbdNameId = imbdNameId;
        this.imbdTitled = imbdTitled;
        this.ordering = Integer.parseInt(ordering);
        this.category = category;
        this.job = job;
        this.characters = characters.split(",");
    }

    public String getImbdNameId() {
        return imbdNameId;
    }

    public String getImbdTitled() {
        return imbdTitled;
    }

    public int getOrdering() {
        return ordering;
    }

    public String getCategory() {
        return category;
    }

    public String getJob() {
        return job;
    }

    public String[] getCharacters() {
        return characters;
    }
}
