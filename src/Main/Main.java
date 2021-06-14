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

        MovieDataBase db = new MovieDataBase();

        db.load();

        db.Querry2(0);

    }
}
