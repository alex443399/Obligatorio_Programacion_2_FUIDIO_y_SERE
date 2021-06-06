package Modelo;

public class Rating {

    private Float NumberVotes;
    private Float AverageRating;

    public Rating(Float NumberVotes, Float AverageRating){
        this.NumberVotes = NumberVotes;
        this.AverageRating = AverageRating;
    }

    public Float getNumberVotes() {
        return NumberVotes;
    }

    public Float getAverageRating() {
        return AverageRating;
    }
}
