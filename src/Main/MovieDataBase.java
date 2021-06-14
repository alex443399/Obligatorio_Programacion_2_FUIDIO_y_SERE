package Main;

import Exceptions.IlegalIndexException;
import Modelo.*;
import TADS.*;
import Utilidades.Loader;

import java.util.Date;
import java.util.Locale;

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

        long start_time = System.currentTimeMillis();

        if(debbug_text >= 2) System.out.println("Discriminacion por pais empieza");

        String[] key_words_paises = {"USA", "UK", "France", "Italy"};
        OpenHash<String, String> keys_pais = obtener_por_paises(key_words_paises);

        if(debbug_text >= 2) System.out.println("Discriminacion por pais acaba");

        //////////////////////

        if(debbug_text >= 2) System.out.println("filtracion por profesion empieza");

        String[] Profesiones = {"Director", "Producer","director", "producer"};
        ArrayList<String> keys_pais_y_profesion = obtener_por_profesion_dado_hash(Profesiones, keys_pais, debbug_text);

        if(debbug_text >= 2) System.out.println("filtracion por profesion acaba");

        /////////////////////

        if(debbug_text >= 2) System.out.println("Contar las causas de muerte empieza");

        int death_hash_table_size = 500;
        OpenHash<String, Integer> cause_of_death_hash = obtener_death_count(keys_pais_y_profesion, death_hash_table_size);

        if(debbug_text >= 2) System.out.println("Contar las causas de muerte acaba");

        /////////////////////

        if(debbug_text >= 2) System.out.println("Rankear las causas de muerte empieza");

        int N = 5; // la cantidad del top que queremos

        String[] top_names = new String[N]; // Mayor en 0
        int[] top_counts = new int[N]; // Mayor en 0

        for(int i = 0; i < death_hash_table_size; i++){
            OpenHashNode<String,Integer> temp_node = cause_of_death_hash.getPosition(i);
            while(temp_node != null){
                int temp_count = temp_node.getValue();
                if(temp_count >= top_counts[N-1]){

                    for(int j = 0; j < N; j++){

                        if(top_counts[j] < temp_count){

                            for(int k = N-1; k > j; k--){
                                top_names[k] = top_names[k-1];
                                top_counts[k] = top_counts[k-1];
                            }
                            top_names[j] = temp_node.getKey();
                            top_counts[j] = temp_count;
                            break;
                        }
                    }

                }
                temp_node = temp_node.getNext();

            }

        }

        if(debbug_text >= 2) System.out.println("Rankear las causas de muerte acaba");

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        for(int i = 0; i < N; i++){
            System.out.println("Causa de muerte: " + top_names[i]);
            System.out.println("Cantidad de personas: " + top_counts[i]);
        }

        System.out.println("Tiempo de ejecucion de la consulta: " + time_elapsed + "ms");

    }

    public void Querry4(int debbug_text) throws IlegalIndexException {
        /**
         * Teniendo en cuenta los siguientes roles: actor y actress, se quiere
         * obtener cual es el año más habitual en el que nacen dichos individuos.
         * Es decir, dadas las películas realizadas, se quiere ver cuál es el año de
         * nacimiento mas frecuente para las actrices y cuál es el año más habitual
         * de nacimiento para los actores
         */

        long start_time = System.currentTimeMillis();

        int cantidad_de_cast_members = 297705;

        OpenHash<String, String> keys_de_castMember_que_vamos_a_filtrar = new OpenHash(cantidad_de_cast_members);

        // Cargamos el arreglo con las keys

        for(int i = 0; i < cast_member_storage.getTableHashSize(); i++){

            OpenHashNode<Integer, CastMember> temp_node = cast_member_storage.getPosition(i);

            while(temp_node != null){

                String id_string = temp_node.getValue().getImbdNameId();

                keys_de_castMember_que_vamos_a_filtrar.put(id_string,id_string);

                temp_node = temp_node.getNext();
            }
        }

        String[] Actor_key_words = {"actor"};
        String[] Actress_key_words = {"actress"};

        ArrayList<String> actors = obtener_por_profesion_dado_hash(Actor_key_words, keys_de_castMember_que_vamos_a_filtrar, 0);
        ArrayList<String> actresses = obtener_por_profesion_dado_hash(Actress_key_words, keys_de_castMember_que_vamos_a_filtrar, 0);

        int year0 = 1800;
        int year_actual = 2021;

        int cantidad_de_anos = year_actual-year0; //2021


        int[][] count_anos = new int[cantidad_de_anos][2];
        int[] max_ano = new int[2];

        for(int i = 0; i < actors.size(); i++){

            String actor_key_String = actors.get(i);
            int actor_key_int = Integer.parseInt(actor_key_String.substring(2,actor_key_String.length()));
            CastMember actor = cast_member_storage.get(actor_key_int);

            Date date_of_birth_of_actor = actor.getBirthDate();
            if(date_of_birth_of_actor != null) {
                int year = date_of_birth_of_actor.getYear()+1900;
                int index = year - year0;
                count_anos[index][0]++;
            }

        }

        for(int i = 0; i < actresses.size(); i++){

            String actress_key_String = actresses.get(i);
            int actress_key_int = Integer.parseInt(actress_key_String.substring(2,actress_key_String.length()));
            CastMember actress = cast_member_storage.get(actress_key_int);

            Date date_of_birth_of_actress = actress.getBirthDate();
            if(date_of_birth_of_actress != null) {
                int year = date_of_birth_of_actress.getYear()+1900;
                int index = year - year0;
                count_anos[index][1]++;
            }
        }

        for(int i = 0; i < cantidad_de_anos; i++){

            if(count_anos[i][0] > count_anos[max_ano[0]][0]){
                max_ano[0] = i;
            }
            if(count_anos[i][1] > count_anos[max_ano[1]][1]){
                max_ano[1] = i;
            }
        }

        int max_ano_actores = max_ano[0] + year0;
        int max_ano_actress = max_ano[1] + year0;
        System.out.println("Actores: ");
        System.out.println("    Año: " + max_ano_actores);
        System.out.println("    Cantidad: " + count_anos[max_ano[0]][0]);
        System.out.println("Actrices: ");
        System.out.println("    Año: " + max_ano_actress);
        System.out.println("    Cantidad: " + count_anos[max_ano[1]][1]);

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        System.out.println("Tiempo de ejecucion de la consulta: " + time_elapsed + "ms");
    }

    public OpenHash<String,Integer> obtener_death_count(ArrayList<String> keys_pais_y_profesion, int death_hash_table_size) throws IlegalIndexException {
        int cantidad_de_profesionales = keys_pais_y_profesion.size();

        OpenHash<String, Integer> cause_of_death_hash = new OpenHash(death_hash_table_size);//48268 elementos que cumplen cond

        for(int i = 0; i < cantidad_de_profesionales; i++){
            //Conseguir profesional - Begin
            String profesional_imdb_name = keys_pais_y_profesion.get(i);
            int profesional_key = Integer.parseInt(profesional_imdb_name.substring(2,profesional_imdb_name.length()));

            OpenHashNode<Integer,CastMember> temp_node = cast_member_storage.getNode(profesional_key);

            while(!temp_node.getValue().getImbdNameId().equals(profesional_imdb_name)){
                temp_node = temp_node.getNext();
            }

            CastMember cast_member_posta = temp_node.getValue();
            //Conseguir profesional - End

            ArrayList<CauseOfDeath> causas_de_muerte_de_un_cast_member = cast_member_posta.getCauseOfDeath();

            for(int j = 0; j < causas_de_muerte_de_un_cast_member.size(); j++){

                String temp_cause_of_death_de_cast_member_posta = causas_de_muerte_de_un_cast_member.get(j).getName().toLowerCase();

                OpenHashNode<String, Integer> nodo_temporal = cause_of_death_hash.getNode(temp_cause_of_death_de_cast_member_posta);

                if(nodo_temporal == null){
                    cause_of_death_hash.put(temp_cause_of_death_de_cast_member_posta,1);
                }
                else{
                    while (!nodo_temporal.getKey().equals(temp_cause_of_death_de_cast_member_posta)) {
                        nodo_temporal = nodo_temporal.getNext();
                        if(nodo_temporal == null)
                            break;
                    }
                    if(nodo_temporal != null) {
                        int current_cout = nodo_temporal.getValue();

                        nodo_temporal.setValue(current_cout + 1);
                    }
                }
            }

        }
        return cause_of_death_hash;
    }

    public OpenHash<String,String> obtener_por_paises(String[] key_words_paises){

        OpenHash<String,String> keys_pais_pre = new OpenHash(297705);

        int N = cast_member_storage.getTableHashSize();
        for(int i = 0; i < N; i++){

            OpenHashNode<Integer, CastMember> temp_cell = cast_member_storage.getPosition(i);
            if(temp_cell != null) {
                String paisito = temp_cell.getValue().getBirthCountry();
                if(paisito != null)
                    if(multiContains(paisito,key_words_paises)) {
                        String s = temp_cell.getValue().getImbdNameId();
                        keys_pais_pre.put(s,s);
                    }

                while (temp_cell.getNext() != null) {
                    temp_cell = temp_cell.getNext();
                    paisito = temp_cell.getValue().getBirthCountry();
                    if(paisito != null)
                        if(multiContains(paisito,key_words_paises)) {
                            String s = temp_cell.getValue().getImbdNameId();
                            keys_pais_pre.put(s,s);
                        }
                }
            }

        }
        return keys_pais_pre;
    }

    public ArrayList<String> obtener_por_profesion_dado_hash(String[] Profesiones, OpenHash<String,String> keys_pais, int debbug_text){

        ArrayList<String> keys_pais_y_profesion = new ArrayList();

        int relations_to_try = movie_cast_member_storage.getTableHashSize();

        for(int index = 0; index < relations_to_try; index++){

            if(debbug_text >= 3) System.out.println("Indice del hash: " + Integer.toString(index));


            OpenHashNode<Integer, MovieCastMember> temp_cell = movie_cast_member_storage.getPosition(index);

            if(temp_cell != null) {
                MovieCastMember temp_cm = temp_cell.getValue();
                if (temp_cm != null) {
                    String name_actor = temp_cm.getImbdNameId();

                    //version estupida del hash contains
                    OpenHashNode<String, String> temp_node = keys_pais.getNode(name_actor);
                    while(temp_node != null){// name_actor si vive en los paises


                        if(temp_node.getValue().equals(name_actor)){
                            String profesion = temp_cm.getCategory();
                            if(multiContains(profesion,Profesiones))
                                keys_pais_y_profesion.add(temp_cm.getImbdNameId());
                        }

                        temp_node = temp_node.getNext();
                    }

                }

                while (temp_cell.getNext() != null) {
                    temp_cell = temp_cell.getNext();
                    temp_cm = temp_cell.getValue();


                    if (temp_cm != null) {
                        String name_actor = temp_cm.getImbdNameId();

                        //version estupida del hash contains
                        OpenHashNode<String, String> temp_node = keys_pais.getNode(name_actor);
                        while(temp_node != null){// name_actor si vive en los paises



                            if(temp_node.getValue().equals(name_actor)){
                                String profesion = temp_cm.getCategory();
                                if(multiContains(profesion,Profesiones)){
                                    keys_pais_y_profesion.add(temp_cm.getImbdNameId());
                                }
                            }

                            temp_node = temp_node.getNext();
                        }
                    }
                }
            }

        }
        return keys_pais_y_profesion;
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
