package Modelo;

import Exceptions.InvalidDateFormatException;
import TADS.ArrayList;
import TADS.ListaEnlazada;

import java.util.Date;

import static Utilidades.Functions.*;

public class CastMember {

    private String imbdNameId;
    private String name;
    private String birthName;
    private Integer height;
    private String bio;
    private Date birthDate;
    private String birthState;
    private String birthCountry;
    private String birthCity;
    private Date deathDate;
    private String deathState;
    private String deathCountry;
    private String deathCity;
    private String spousesString;
    private Integer spouses;
    private Integer divorces;
    private Integer spousesWithChildren;
    private Integer children;
    ArrayList<CauseOfDeath> causeOfDeath;

    public CastMember(String imbdNameId, String name, String birthName, Integer height, String bio, Date birthDate, String birthState, String birthCountry, String birthCity, Date deathDate, String deathState, String deathCountry, String deathCity, String spousesString, int spouses, int divorces, int spousesWithChildren, int children) {
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

    public CastMember(String imbdNameId, String name, String birthName, String s_height, String bio, String birthDetails, String date_of_birth, String place_of_birth, String deathDetails, String date_of_death, String place_of_death, String reason_of_death, String spousesString, String s_spouses, String s_divorces, String s_spousesWithChildren, String s_children) throws InvalidDateFormatException {
        this.imbdNameId = imbdNameId;
        this.name = name;
        this.birthName = birthName;
        this.height = parseIntNullEnabled(s_height);
        this.bio = bio;
        this.birthDate = DateFromRegisterString(date_of_birth);
        String[] place_birth = ParametersFromPlaceString(place_of_birth);

        if(place_birth != null) {
            this.birthCity = place_birth[0];
            this.birthState = place_birth[1];
            this.birthCountry = place_birth[2];
        }

        this.deathDate = DateFromRegisterString(date_of_death);
        String[] place_death = ParametersFromPlaceString(place_of_death);

        if(place_death != null) {
            this.deathCity = place_death[0];
            this.deathState = place_death[1];
            this.deathCountry = place_death[2];
        }

        this.spousesString = spousesString;
        this.spouses = parseIntNullEnabled(s_spouses);
        this.divorces = parseIntNullEnabled(s_divorces);
        this.spousesWithChildren = parseIntNullEnabled(s_spousesWithChildren);
        this.children = parseIntNullEnabled(s_children);
        this.causeOfDeath = CausasDeMuerte(reason_of_death);
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

    public Date getBirthDate() {
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

    public Date getDeathDate() {
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

    public ArrayList<CauseOfDeath> getCauseOfDeath() {
        return causeOfDeath;
    }
}