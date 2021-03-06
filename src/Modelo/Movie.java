package Modelo;

import TADS.ListaEnlazada;

import java.util.Date;

import static Utilidades.Functions.*;

public class Movie {

    private String imbdTitle;
    private String title;
    private String originalTitle;
    private Integer year;
    private Date datePublished;
    private ListaEnlazada<String> genre;
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
    private String worldWideGrossIncome;
    private Float metaScore;
    private Float reviewsFromUsers;
    private Float reviewsFromCritics;

    public Movie(String imbdTitle, String title, String originalTitle, int year, Date datePublished, ListaEnlazada<String> genre, int duration, ListaEnlazada country, String language, ListaEnlazada director, ListaEnlazada writer, String productorCompany, ListaEnlazada actors, String description, float avgVote, int votes, String budget, String usaGrossIncome, String worldWideGrossIncome, Float metaScore, Float reviewsFromUsers, Float reviewsFromCritics) {
        this.imbdTitle = imbdTitle;
        this.title = title;
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
        this.worldWideGrossIncome = worldWideGrossIncome;
        this.metaScore = metaScore;
        this.reviewsFromUsers = reviewsFromUsers;
        this.reviewsFromCritics = reviewsFromCritics;
    }

    public Movie(String imbdTitle, String title, String originalTitle, String s_year, String s_datePublished, String s_genre, String s_duration, String s_country, String language, String s_director, String s_writer, String productorCompany, String s_actors, String description, String s_avgVote, String s_votes, String budget, String usaGrossIncome, String worldWideGrossIncome, String s_metaScore, String s_reviewsFromUsers, String s_reviewsFromCritics){
        this.imbdTitle = imbdTitle;
        this.title = title;
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
        this.worldWideGrossIncome = worldWideGrossIncome;
        this.metaScore = parseFloatNullEnabled(s_metaScore);
        this.reviewsFromUsers = parseFloatNullEnabled(s_reviewsFromUsers);
        this.reviewsFromCritics = parseFloatNullEnabled(s_reviewsFromCritics);
    }

    public String getImbdTitle() {
        return imbdTitle;
    }

    public String getTitle() {
        return title;
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

    public String getWorldWideGrossIncome() {
        return worldWideGrossIncome;
    }

    public Float getMetaScore() {
        return metaScore;
    }

    public Float getReviewsFromUsers() {
        return reviewsFromUsers;
    }

    public Float getReviewsFromCritics() {
        return reviewsFromCritics;
    }
}
