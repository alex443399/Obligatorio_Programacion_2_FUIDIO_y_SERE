package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;

import static Utilidades.Functions.parseIntNullEnabled;

public class CastMember {

    private String imbdNameId;
    private String name;
    private String birthName;
    private Integer height;
    private String bio;
    private LocalDate birthDate;
    private String birthState;
    private String birthCountry;
    private String birthCity;
    private LocalDate deathDate;
    private String deathState;
    private String deathCountry;
    private String deathCity;
    private String spousesString;
    private Integer spouses;
    private Integer divorces;
    private Integer spousesWithChildren;
    private Integer children;

    public CastMember(String imbdNameId, String name, String birthName, int height, String bio, LocalDate birthDate, String birthState, String birthCountry, String birthCity, LocalDate deathDate, String deathState, String deathCountry, String deathCity, String spousesString, int spouses, int divorces, int spousesWithChildren, int children) {
        this.imbdNameId = imbdNameId;
        this.name = name;
        this.birthName = birthName;
        this.height = height;
        this.bio = bio;
        this.birthDate = birthDate;
        this.birthState = birthState;
        this.birthCountry = birthCountry;
        this.birthCity = birthCity;
        this.deathDate = deathDate;
        this.deathState = deathState;
        this.deathCountry = deathCountry;
        this.deathCity = deathCity;
        this.spousesString = spousesString;
        this.spouses = spouses;
        this.divorces = divorces;
        this.spousesWithChildren = spousesWithChildren;
        this.children = children;
    }

    public CastMember(String imbdNameId, String name, String birthName, String s_height, String bio, String birthDetails, String date_of_birth, String place_of_birth, String deathDetails, String date_of_death, String place_of_death, String reason_of_death, String spousesString, String s_spouses, String s_divorces, String s_spousesWithChildren, String s_children) {
        this.imbdNameId = imbdNameId;
        this.name = name;
        this.birthName = birthName;
        this.height = parseIntNullEnabled(s_height);
        this.bio = bio;
        /*
        try{
            this.birthDate = LocalDate.parse(s_birthDate);
        }
        catch (DateTimeParseException e){
            if(s_birthDate.length() == 4)
                this.birthDate = null;
            else
                this.birthDate = LocalDate.parse(s_birthDate);
        }

        this.birthState = birthState;
        this.birthCountry = birthCountry;
        this.birthCity = birthCity;
        */
        /*
        try {
            this.deathDate = LocalDate.parse(s_deathDate);
        }
        catch (DateTimeParseException e){
            if(s_deathDate.length() == 4)
                this.deathDate = null;
            else
                this.deathDate = LocalDate.parse(s_deathDate);
        }
        this.deathState = deathState;
        this.deathCountry = deathCountry;
        this.deathCity = deathCity;
        */
        this.spousesString = spousesString;
        this.spouses = parseIntNullEnabled(s_spouses);
        this.divorces = parseIntNullEnabled(s_divorces);
        this.spousesWithChildren = parseIntNullEnabled(s_spousesWithChildren);
        this.children = parseIntNullEnabled(s_children);
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

    public int getHeight() {
        return height;
    }

    public String getBio() {
        return bio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthState() {
        return birthState;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public String getDeathState() {
        return deathState;
    }

    public String getDeathCountry() {
        return deathCountry;
    }

    public String getDeathCity() {
        return deathCity;
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
}