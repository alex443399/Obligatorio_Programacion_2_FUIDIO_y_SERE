package Main;
import Modelo.CastMember;
import Modelo.Movie;
import Modelo.MovieCastMember;
import Modelo.MovieRating;
import TADS.HeapImp;
import TADS.OpenHash;
import TADS.OpenHashNode;
import Utilidades.Loader;


public class Main {


    public static void main(String[] args) throws Exception{

        Loader loader = new Loader();

        OpenHash<Integer, MovieCastMember> movie_cast_member_storage = loader.load_movie_cast_member(2);
/*
        OpenHashNode<Integer, MovieCastMember> nodo = movie_cast_member_storage.getNode(3014);// 3014 colision
        System.out.println(nodo.getValue().getImbdTitled());

        while(nodo.getNext() != null){
            nodo = nodo.getNext();
            System.out.println(nodo.getValue().getImbdTitled());

        }
*/

        HeapImp<MovieRating> movie_rating_storage = loader.load_review_database(2); //4: Error->print
/*
        System.out.println("-----");
        Float wa;
        while(!movie_rating_storage.isEmpty()){
            wa = movie_rating_storage.pop().getWeightedAverage();
            System.out.println(wa);
        }
*/
        OpenHash<Integer, Movie> movie_storage = loader.load_movie_database(2);
        OpenHash<Integer, CastMember> cast_member_storage = loader.load_castmember_database(2);
        //System.out.println(movie_storage.get(5500148).getTitled());
        //System.out.println(cast_member_storage.get(1).getName());
        //System.out.println(cast_member_storage.get(2510127).getName());
    }
}
