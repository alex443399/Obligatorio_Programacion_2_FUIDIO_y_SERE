package Modelo;

import TADS.ArrayList;

import java.util.List;

public class MovieRating {

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

/*
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
    */

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

    }

}
