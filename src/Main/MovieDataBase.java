package Main;

import Exceptions.IlegalIndexException;
import Modelo.CastMember;
import Modelo.Movie;
import Modelo.MovieCastMember;
import Modelo.MovieRating;
import TADS.HeapImp;
import TADS.ListaEnlazada;
import TADS.OpenHash;
import TADS.OpenHashNode;
import Utilidades.Loader;

public class MovieDataBase {

    public String proyect_path = "C:\\Users\\alex4\\IdeaProjects\\Obligatorio Programacion 2 v1\\src\\Files\\";
    // Alex -> "C:\\Users\\alex4\\IdeaProjects\\Obligatorio Programacion 2 v1\\src\\Files\\";
    // Fede -> "C:\\Users\\Federico Fuidio\\IdeaProjects\\Obligatorio_Programacion_2_FUIDIO_y_SERE\\src\\Files\\dataset1\\"

    Loader loader = new Loader(proyect_path);

    boolean data_loaded = false;

    OpenHash<Integer, MovieCastMember> movie_cast_member_storage;
    HeapImp<MovieRating> movie_rating_storage;
    OpenHash<Integer, Movie> movie_storage;
    OpenHash<Integer, CastMember> cast_member_storage;

    public MovieDataBase(){

    }

    public void load(){
        long start_time = System.currentTimeMillis();
        try {
            //movie_cast_member_storage = loader.load_movie_cast_member(0);
            //movie_rating_storage = loader.load_review_database(0);
            //movie_storage = loader.load_movie_database(0);
            cast_member_storage = loader.load_castmember_database(0);
            data_loaded = true;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga:" + Long.toString(time_elapsed) + "ms");
    }

    public void Querry2(){
        /**
         * Tomando en cuenta los productores y directores que nacieron en
         * Italia, Estados Unidos, Francia y UK, hacer un top 5 de las causas de
         * muerte más frecuentes de dichos países.
         */

        // Primero vamos a testear q countries existen
        listCountries();
    }

    public void listCountries(){
        int N = cast_member_storage.getTableHashSize();

        ListaEnlazada<String> paises = new ListaEnlazada();

        for(int i = 0; i < N; i++){
            OpenHashNode<Integer, CastMember> temp_cell = cast_member_storage.getPosition(i);
            if(temp_cell != null) {
                String paisito = temp_cell.getValue().getBirthCountry();
                if (!paises.estaEnLista(paisito))
                    paises.add(paisito);

                while (temp_cell.getNext() != null) {
                    temp_cell = temp_cell.getNext();
                    paisito = temp_cell.getValue().getBirthCountry();
                    if (!paises.estaEnLista(paisito))
                        paises.add(paisito);
                }
            }

        }

        for (int i = 0; i < paises.size(); i++) {
            try {
                System.out.println(paises.get(i));
            }
            catch(IlegalIndexException e){
                e.printStackTrace();
            }
        }
        System.out.println(paises.size());

    }

}
