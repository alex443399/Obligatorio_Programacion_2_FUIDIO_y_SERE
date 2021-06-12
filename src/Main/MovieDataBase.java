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

import static Utilidades.Functions.multiContains;

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
            movie_cast_member_storage = loader.load_movie_cast_member(0);
            movie_rating_storage = loader.load_review_database(0);
            movie_storage = loader.load_movie_database(0);
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

    public void Querry2(int debbug_text) throws IlegalIndexException {
        /**
         * Tomando en cuenta los productores y directores que nacieron en
         * Italia, Estados Unidos, Francia y UK, hacer un top 5 de las causas de
         * muerte más frecuentes de dichos países.
         */

        // Primero vamos a testear q countries existen
        // listCountries(); // Le pregunte a Jimena que cuenta como que pais, hasta que me conteste voy a usar como key "USA", "UK", "Italy", "France"

        if(debbug_text >= 2) System.out.println("Discriminacion por pais empieza");

        String[] key_words_paises = {"USA", "UK", "France", "Italy"};
        ListaEnlazada<String> keys_pais = obtener_por_paises(key_words_paises);

        if(debbug_text >= 2) System.out.println("Discriminacion por pais acaba");



        if(debbug_text >= 2) System.out.println("filtracion por profesion empieza");

        String[] Profesiones = {"Director", "Producer"};
        ListaEnlazada<String> keys_pais_y_profesion = new ListaEnlazada();

        int relations_to_try = movie_cast_member_storage.getTableHashSize();

        for(int index = 0; index < relations_to_try; index++){

            if(debbug_text >= 3) System.out.println("Indice del hash: " + Integer.toString(index));
            OpenHashNode<Integer, MovieCastMember> temp_cell = movie_cast_member_storage.getPosition(index);

            if(temp_cell != null) {
                MovieCastMember temp_cm = temp_cell.getValue();
                if (temp_cm != null) {

                    if (keys_pais.estaEnLista(temp_cm.getImbdNameId())) {   // ACA ESTA LA LENTEJA
/*
                        String profesion = temp_cm.getCategory();
                        if(profesion.equals("Productor"))
                            keys_pais_y_profesion.add(temp_cm.getImbdNameId());*/
                    }
                }
/*
                while (temp_cell.getNext() != null) {
                    temp_cell = temp_cell.getNext();
                    temp_cm = temp_cell.getValue();


                    if(index == 2461){
                        System.out.println(temp_cm.getImbdNameId());
                        System.out.println(temp_cell.getNext());
                    }

                    if (temp_cm != null) {
                        if (keys_pais.estaEnLista(temp_cm.getImbdNameId())) {
                            //aca esta revisando, compara profesion y blah blah
                            String profesion = temp_cm.getCategory();
                            if(profesion.equals("Productor"))
                                keys_pais_y_profesion.add(temp_cm.getImbdNameId());
                        }
                    }
                }*/
            }

        }

        if(debbug_text >= 2) System.out.println("Discriminacion por pais acaba");

        for(int i = 0; i < keys_pais_y_profesion.size(); i++)
            System.out.println(keys_pais_y_profesion.get(i));

    }

    public ListaEnlazada<String> obtener_por_paises(String[] key_words_paises){

        ListaEnlazada<String> keys_pais = new ListaEnlazada();

        int N = cast_member_storage.getTableHashSize();
        for(int i = 0; i < N; i++){

            OpenHashNode<Integer, CastMember> temp_cell = cast_member_storage.getPosition(i);
            if(temp_cell != null) {
                String paisito = temp_cell.getValue().getBirthCountry();
                if(paisito != null)
                    if(multiContains(paisito,key_words_paises))
                        keys_pais.add(temp_cell.getValue().getImbdNameId());

                while (temp_cell.getNext() != null) {
                    temp_cell = temp_cell.getNext();
                    paisito = temp_cell.getValue().getBirthCountry();
                    if(paisito != null)
                        if(multiContains(paisito,key_words_paises))
                           keys_pais.add(temp_cell.getValue().getImbdNameId());
                }
            }

        }
        return keys_pais;
    }

    public void listCountries(){
        int N = cast_member_storage.getTableHashSize();

        ListaEnlazada<String> paises = new ListaEnlazada();

        for(int i = 0; i < N; i++){
            OpenHashNode<Integer, CastMember> temp_cell = cast_member_storage.getPosition(i);
            if(temp_cell != null) {
                String paisito = temp_cell.getValue().getBirthCountry();
                //if(paisito.equals("Oklahoma"))
                //    System.out.println(temp_cell.getKey());
                if (!paises.estaEnLista(paisito))
                    paises.add(paisito);

                while (temp_cell.getNext() != null) {
                    temp_cell = temp_cell.getNext();
                    paisito = temp_cell.getValue().getBirthCountry();

                    //if(paisito.equals("Oklahoma"))
                    //    System.out.println(temp_cell.getKey());

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
