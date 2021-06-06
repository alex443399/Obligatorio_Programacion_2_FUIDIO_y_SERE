package Modelo;

import TADS.ArrayList;

import java.util.List;

public class MovieRating implements Comparable<MovieRating> {

    private String imdbTitle;

    private Float weightedAverage;
    private Integer totalVotes;
    private Float meanVotes;
    private Float medianVote;
    private ArrayList<Integer> votesRating;

    private Rating allGenders;
    private Rating males;
    private Rating females;
    private Rating top1000;
    private Rating us;
    private Rating non_us;


    private Rating allGenders_0age;
    private Rating allGenders_18age;
    private Rating allGenders_30age;
    private Rating allGenders_45age;
    private Rating malesAllAge;
    private Rating males_0age;
    private Rating males_18age;
    private Rating males_30age;
    private Rating males_45age;
    private Rating femalesAllAge;
    private Rating females_0age;
    private Rating females_18age;
    private Rating females_30age;
    private Rating females_45age;
    private Rating top_1000Voters;
    private Rating usVoters;
    private Rating nonUsVoters;


    public MovieRating(String imdbTitle, String weighted_average_vote, String total_votes, String mean_votes, String median_votes,
                       String votes_10, String votes_9, String votes_8, String votes_7, String votes_6, String votes_5, String votes_4, String votes_3, String votes_2, String votes_1,
                       String allgenders_0age_avg_vote, String allgenders_0age_votes, String allgenders_18age_avg_vote, String allgenders_18age_votes, String allgenders_30age_avg_vote, String allgenders_30age_votes, String allgenders_45age_avg_vote, String allgenders_45age_votes,
                       String males_allages_avg_vote, String males_allages_votes,
                       String males_0age_avg_vote, String males_0age_votes, String males_18age_avg_vote, String males_18age_votes, String males_30age_avg_vote, String males_30age_votes, String males_45age_avg_vote, String males_45age_votes,
                       String females_allages_avg_vote, String females_allages_votes,
                       String females_0age_avg_vote, String females_0age_votes, String females_18age_avg_vote, String females_18age_votes, String females_30age_avg_vote, String females_30age_votes, String females_45age_avg_vote, String females_45age_votes,
                       String top1000_voters_rating, String top1000_voters_vote,
                       String us_voters_ratings, String us_voters_votes,
                       String non_us_voters_ratings, String non_us_voters_votes
    ){
        this.imdbTitle = imdbTitle;
        this.weightedAverage = Float.parseFloat(weighted_average_vote);
        this.totalVotes = Integer.parseInt(total_votes);
        this.meanVotes = Float.parseFloat(mean_votes);
        this.votesRating = new ArrayList<>();
        votesRating.add(Integer.parseInt(votes_10));
        votesRating.add(Integer.parseInt(votes_9));
        votesRating.add(Integer.parseInt(votes_8));
        votesRating.add(Integer.parseInt(votes_7));
        votesRating.add(Integer.parseInt(votes_6));
        votesRating.add(Integer.parseInt(votes_5));
        votesRating.add(Integer.parseInt(votes_4));
        votesRating.add(Integer.parseInt(votes_3));
        votesRating.add(Integer.parseInt(votes_3));
        votesRating.add(Integer.parseInt(votes_2));
        votesRating.add(Integer.parseInt(votes_1));
        this.allGenders_0age = new Rating(Integer.parseInt(allgenders_0age_votes),
                Float.parseFloat(allgenders_0age_avg_vote));
        this.allGenders_18age = new Rating(Integer.parseInt(allgenders_18age_votes),
                Float.parseFloat(allgenders_18age_avg_vote));
        this.allGenders_30age = new Rating(Integer.parseInt(allgenders_30age_votes),
                Float.parseFloat(allgenders_30age_avg_vote));
        this.allGenders_45age = new Rating(Integer.parseInt(allgenders_45age_votes),
                Float.parseFloat(allgenders_18age_avg_vote));
        this.malesAllAge = new Rating(Integer.parseInt(males_allages_votes),
                Float.parseFloat(males_allages_avg_vote));
        this.males_0age = new Rating(Integer.parseInt(males_0age_votes),
                Float.parseFloat(males_0age_avg_vote));
        this.males_18age = new Rating(Integer.parseInt(males_18age_votes),
                Float.parseFloat(males_18age_avg_vote));
        this.males_30age = new Rating(Integer.parseInt(males_30age_votes),
                Float.parseFloat(males_30age_avg_vote));
        this.males_45age = new Rating(Integer.parseInt(males_45age_votes),
                Float.parseFloat(males_45age_avg_vote));
        this.femalesAllAge = new Rating(Integer.parseInt(females_allages_votes),
                Float.parseFloat(females_allages_avg_vote));
        this.females_0age = new Rating(Integer.parseInt(females_0age_votes),
                Float.parseFloat(females_0age_avg_vote));
        this.females_18age = new Rating(Integer.parseInt(females_18age_votes),
                Float.parseFloat(females_18age_avg_vote));
        this.females_30age = new Rating(Integer.parseInt(females_30age_votes),
                Float.parseFloat(females_30age_avg_vote));
        this.females_45age = new Rating(Integer.parseInt(females_45age_votes),
                Float.parseFloat(females_45age_avg_vote));
        this.top_1000Voters = new Rating(Integer.parseInt(top1000_voters_rating),
                Float.parseFloat(top1000_voters_vote));
        this.usVoters = new Rating(Integer.parseInt(us_voters_ratings),
                Float.parseFloat(us_voters_votes));
        this.nonUsVoters = new Rating(Integer.parseInt(non_us_voters_ratings),
                Float.parseFloat(non_us_voters_votes));

    }

    @Override
    public int compareTo(MovieRating m) {
        if(weightedAverage > m.weightedAverage)
            return 1;
        else if (weightedAverage < m.weightedAverage)
            return -1;
        else
            return 0;
    }

    public String getImdbTitle() {
        return imdbTitle;
    }

    public Float getWeightedAverage() {
        return weightedAverage;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public Float getMeanVotes() {
        return meanVotes;
    }

    public Float getMedianVote() {
        return medianVote;
    }

    public ArrayList<Integer> getVotesRating() {
        return votesRating;
    }

    public Rating getAllGenders() {
        return allGenders;
    }

    public Rating getMales() {
        return males;
    }

    public Rating getFemales() {
        return females;
    }

    public Rating getTop1000() {
        return top1000;
    }

    public Rating getUs() {
        return us;
    }

    public Rating getNon_us() {
        return non_us;
    }
}
