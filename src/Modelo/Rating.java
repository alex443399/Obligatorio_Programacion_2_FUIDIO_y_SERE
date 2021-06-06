package Modelo;

public class Rating {

    private Integer NumberVotes;
    private Float AverageRating;

    public Rating(Integer NumberVotes, Float AverageRating){
        this.NumberVotes = NumberVotes;
        this.AverageRating = AverageRating;
    }

    public Integer getNumberVotes() {
        return NumberVotes;
    }

    public Float getAverageRating() {
        return AverageRating;
    }
}
