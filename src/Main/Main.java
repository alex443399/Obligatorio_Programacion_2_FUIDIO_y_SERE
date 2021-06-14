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

        db.load(); // Hay muchas fechas que no se cargan, incluyendo "July 22 in Chicago, Illinois, USA", "c. 1928 in Israel", "October 9"

        db.Querry4(0);

    }
}
