package Modelo;

import Exceptions.InvalidDateFormatException;

import static Utilidades.Functions.*;

public class CastMember {

    private String imbdNameId;
    private String name;
    private String birthName;
    private Integer height;
    private String bio;
    private Integer birth_year;
    private String place_of_birth;
    private Integer death_year;
    private String place_of_death;
    private String spousesString;
    private Integer spouses;
    private Integer divorces;
    private Integer spousesWithChildren;
    private Integer children;
    CauseOfDeath causeOfDeath;


    public CastMember(String imbdNameId, String name, String birthName, String s_height, String bio, String birthDetails, String date_of_birth, String place_of_birth, String deathDetails, String date_of_death, String place_of_death, String reason_of_death, String spousesString, String s_spouses, String s_divorces, String s_spousesWithChildren, String s_children) throws InvalidDateFormatException {
        this.imbdNameId = imbdNameId;
        this.name = name;
        this.birthName = birthName;
        this.height = parseIntNullEnabled(s_height);
        this.bio = bio;



            this.place_of_birth =place_of_birth;
            this.place_of_death =place_of_death;

            this.birth_year = get_stupid_birth_year(date_of_birth);
            this.death_year = get_stupid_birth_year(date_of_death);


        this.spousesString = spousesString;
        this.spouses = parseIntNullEnabled(s_spouses);
        this.divorces = parseIntNullEnabled(s_divorces);
        this.spousesWithChildren = parseIntNullEnabled(s_spousesWithChildren);
        this.children = parseIntNullEnabled(s_children);
        try{
            if(reason_of_death.equals(" "))
                this.causeOfDeath = null;
            this.causeOfDeath = new CauseOfDeath(reason_of_death);
        }
        catch (Exception e){

        }
    }

    public String getImbdNameId() {
        return imbdNameId;
    }

    public String getName() {
        return name;
    }

    public String getBirthName() {
        return birthName;
    }

    public Integer getHeight() {
        return height;
    }

    public String getBio() {
        return bio;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public String getPlace_of_death() {
        return place_of_death;
    }

    public String getSpousesString() {
        return spousesString;
    }

    public Integer getSpouses() {
        return spouses;
    }

    public Integer getDivorces() {
        return divorces;
    }

    public Integer getSpousesWithChildren() {
        return spousesWithChildren;
    }

    public Integer getChildren() {
        return children;
    }

    public CauseOfDeath getCauseOfDeath() {
        return causeOfDeath;
    }
}