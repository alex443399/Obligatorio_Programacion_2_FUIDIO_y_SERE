package Modelo;

import Exceptions.InvalidDateFormatException;
import TADS.ListaEnlazada;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import static Utilidades.Functions.*;

public class Movie {

    private String imbdTitled;
    private String titled;
    private String originalTitle;
    private Integer year;
    private Date datePublished;
    private ListaEnlazada<String > genre;
    private Integer duration;
    private ListaEnlazada<String> country;
    private String language;
    private ListaEnlazada<String> director;
    private ListaEnlazada<String> writer;
    private String productorCompany;
    private ListaEnlazada<String> actors;
    private String description;
    private Float avgVote;
    private Integer votes;
    private String budget;
    private String usaGrossIncome;
    private String worldWideGorssIncome;
    private Integer metaScore;
    private Integer reviewsFromUsers;
    private Integer reviewsFromCritics;

    public Movie(String imbdTitled, String titled, String originalTitle, int year, Date datePublished, ListaEnlazada<String> genre, int duration, ListaEnlazada country, String language, ListaEnlazada director, ListaEnlazada writer, String productorCompany, ListaEnlazada actors, String description, float avgVote, int votes, String budget, String usaGrossIncome, String worldWideGorssIncome, int metaScore, int reviewsFromUsers, int reviewsFromCritics) {
        this.imbdTitled = imbdTitled;
        this.titled = titled;
        this.originalTitle = originalTitle;
        this.year = year;
        this.datePublished = datePublished;
        this.genre = genre;
        this.duration = duration;
        this.country = country;
        this.language = language;
        this.director = director;
        this.writer = writer;
        this.productorCompany = productorCompany;
        this.actors = actors;
        this.description = description;
        this.avgVote = avgVote;
        this.votes = votes;
        this.budget = budget;
        this.usaGrossIncome = usaGrossIncome;
        this.worldWideGorssIncome = worldWideGorssIncome;
        this.metaScore = metaScore;
        this.reviewsFromUsers = reviewsFromUsers;
        this.reviewsFromCritics = reviewsFromCritics;
    }

    public Movie(String imbdTitled, String titled, String originalTitle, String s_year, String s_datePublished, String s_genre, String s_duration, String s_country, String language, String s_director, String s_writer, String productorCompany, String s_actors, String description, String s_avgVote, String s_votes, String budget, String usaGrossIncome, String worldWideGorssIncome, String s_metaScore, String s_reviewsFromUsers, String s_reviewsFromCritics) throws InvalidDateFormatException {
        this.imbdTitled = imbdTitled;
        this.titled = titled;
        this.originalTitle = originalTitle;
        this.year = parseYear(s_year); // Int

        this.datePublished = DateFromRegisterString(s_datePublished);

        this.genre = ListaEnCelda(s_genre); // List<String>
        this.duration = parseIntNullEnabled(s_duration); // int
        this.country = ListaEnCelda(s_country); // List<String>
        this.language = language;
        this.director = ListaEnCelda(s_director); // List<String>
        this.writer = ListaEnCelda(s_writer);
        this.productorCompany = productorCompany;
        this.actors = ListaEnCelda(s_actors); // List<String>
        this.description = description;
        this.avgVote = Float.parseFloat(s_avgVote);
        this.votes = parseIntNullEnabled(s_votes);
        this.budget = budget;
        this.usaGrossIncome = usaGrossIncome;
        this.worldWideGorssIncome = worldWideGorssIncome;
        this.metaScore = parseIntNullEnabled(s_metaScore);
        this.reviewsFromUsers = parseIntNullEnabled(s_reviewsFromUsers);
        this.reviewsFromCritics = parseIntNullEnabled(s_reviewsFromCritics);
    }

    public String getImbdTitled() {
        return imbdTitled;
    }

    public String getTitled() {
        return titled;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Integer getYear() {
        return year;
    }

    public Date getDatePublished() { return datePublished; }

    public ListaEnlazada<String> getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public ListaEnlazada getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public ListaEnlazada getDirector() {
        return director;
    }

    public ListaEnlazada getWriter() {
        return writer;
    }

    public String getProductorCompany() {
        return productorCompany;
    }

    public ListaEnlazada getActors() {
        return actors;
    }

    public String getDescription() {
        return description;
    }

    public Float getAvgVote() {
        return avgVote;
    }

    public Integer getVotes() {
        return votes;
    }

    public String getBudget() {
        return budget;
    }

    public String getUsaGrossIncome() {
        return usaGrossIncome;
    }

    public String getWorldWideGorssIncome() {
        return worldWideGorssIncome;
    }

    public Integer getMetaScore() {
        return metaScore;
    }

    public Integer getReviewsFromUsers() {
        return reviewsFromUsers;
    }

    public Integer getReviewsFromCritics() {
        return reviewsFromCritics;
    }
}
