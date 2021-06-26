package Main;

import Exceptions.EmptyHeapException;
import Exceptions.IlegalIndexException;
import Modelo.*;
import TADS.*;
import Utilidades.Loader;

import java.nio.file.Path;
import java.nio.file.Paths;

import static Utilidades.Functions.multiContains;

public class MovieDataBase {

    Loader loader;
    boolean data_loaded = false;

    OpenHash<String, MovieCastMember> movie_cast_member_storage;
    HeapImp<MovieRating> movie_rating_storage;
    OpenHash<String, Movie> movie_storage;
    OpenHash<String, CastMember> cast_member_storage;

    public MovieDataBase(){
        Path path = Paths.get("."); // http://tutorials.jenkov.com/java-nio/path.html de aca aprendi a usar paths
        String path_absolute = path.toAbsolutePath().toString();

        String proyect_path = path_absolute.substring(0,path_absolute.length()-1);
        String file_path = proyect_path + "src\\Files\\";
        loader = new Loader(file_path);
    }

    public void load(){
        long start_time = System.currentTimeMillis();
        try {
            int debbug_text = 0;
            movie_cast_member_storage = loader.load_movie_cast_member(debbug_text);
            movie_storage = loader.load_movie_database(debbug_text);
            movie_rating_storage = loader.load_review_database(movie_storage, debbug_text);
            cast_member_storage = loader.load_castmember_database(debbug_text);
            data_loaded = true;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga:" + Long.toString(time_elapsed) + "ms");
        System.out.println();
    }

    public void Query(int Q){
        if(data_loaded) {
            switch (Q) {
                case 1:
                    Query1();
                    break;
                case 2:
                    Query2();
                    break;
                case 3:
                    Query3();
                    break;
                case 4:
                    Query4();
                    break;
                case 5:
                    Query5();
                    break;
            }
        }
        else {
            System.out.println("Por favor caerga los datos antes de hacer los querries");
        }


    }

    public void Query1(){

        long begin = System.currentTimeMillis();

        //Hay cerca de 90000 actores, definimos el hash con un poco más del doble de buckets,
        //Para asegurar que el load factor nunca supere el 0.5
        MyClosedHash<String, Integer> result = new MyClosedHash<>(180000);

        for(int i = 0; i < movie_cast_member_storage.getTableHashSize(); i++){

            OpenHashNode<String, MovieCastMember> temp = movie_cast_member_storage.getPosition(i);
            while(temp != null){

                if(temp.getValue().getCategory().contains("actor") ||
                        temp.getValue().getCategory().contains("actress")){

                    result.put(temp.getValue().getImbdNameId(), 1);

                }
                temp = temp.getNext();
            }

        }

        result.Bubblesort(5);

        for (int i = 0; i < 5; i++){

            ClosedHashNode<String, Integer> retorno = result.getPosition(result.getTableHashSize() - i - 1);
            // La key guardade es un string, para poder buscar el nombre del acotor en
            // cast_member_strage debemos buscar con un key que es integer:

            CastMember temp = cast_member_storage.get(retorno.getKey());
            String name = temp.getName();

            System.out.println("Nombre actor/actriz: " + name);
            System.out.println("Cantidad de apariciones: " + retorno.getReps());
            System.out.println(); //

        }

        long end = System.currentTimeMillis();
        System.out.println("Tiempo de ejecicion de la consulta: " + (end - begin) + "ms");
        System.out.println();


    }

    public void Query2() {
        /**
         * Tomando en cuenta los productores y directores que nacieron en
         * Italia, Estados Unidos, Francia y UK, hacer un top 5 de las causas de
         * muerte más frecuentes de dichos países.
         */

        int debbug_text = 0;

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
            System.out.println();
        }

        System.out.println("Tiempo de ejecucion de la consulta: " + time_elapsed + "ms");
        System.out.println();
    }

    public void Query3(){
        long begin = System.currentTimeMillis();

        int counter = 0; //El counter nos indicará el momento en el que obtenemos las 14 peliculas:
        //Los elementos que sacaremos los colocamos en elementosSacados, para luego insertarlos de nuevo:
        ListaEnlazada<MovieRating> elementosSacados = new ListaEnlazada();
        Lista<Movie> resultados = new ListaEnlazada<>(); //Aquí iran las 14 peliculas que queremos que nos
        //devuelva la consulta

        MovieRating temp;
        Movie pelicula;
        while(counter < 14){
            try {
                temp = movie_rating_storage.pop();
                elementosSacados.add(temp);

                pelicula = movie_storage.get(temp.getImdbTitle());

                if(pelicula.getYear() != null && pelicula.getYear() >= 1950  && pelicula.getYear() <= 1960)
                {
                    resultados.add(pelicula);
                    counter ++;
                }

            } catch (EmptyHeapException e) {
                e.printStackTrace();
            }

        } //Ahora, en resulta tenemos, ordenados de mayor a menor, las peliculas con mas weigthed average
        //Con año de estreno entre 1950 y 1960. en elementos sacados, estan todos los elementos que se sacaron del heap
        //Ahora, antes de seguir, volvemos a colocar en el heap los elementos en elementosSacados.

        int elementosAInsertar = elementosSacados.size();
        for (int i = 0; i < elementosAInsertar; i++){
            try {
                movie_rating_storage.insert(elementosSacados.get(i));
            } catch (IlegalIndexException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 14; i++){
            try{
                float sumaAltura = 0;
                pelicula = resultados.get(i);

                OpenHashNode<String, MovieCastMember> relation = movie_cast_member_storage.getNode(pelicula.getImbdTitle());
                //Buscamos todos los actores que actuaron en la pelicula:
                int cantidad_actores = 0;
                while(relation != null){
                    if(relation.getKey().equals(pelicula.getImbdTitle()) && (relation.getValue().getCategory().contains("actor")
                            || relation.getValue().getCategory().contains("actress"))){

                        CastMember actor = cast_member_storage.get(relation.getValue().getImbdNameId());

                        if(actor != null && actor.getHeight() != null) {
                            sumaAltura = sumaAltura + actor.getHeight();
                            cantidad_actores ++;
                        }

                    }
                    relation = relation.getNext();
                }

                if(sumaAltura != 0){
                    float promedio = sumaAltura/cantidad_actores;
                    System.out.println("Id pelicula: " + pelicula.getImbdTitle());
                    System.out.println("Nombre: " + pelicula.getTitle());
                    System.out.println("Altura promedio de actores: " + promedio);
                    System.out.println();
                }



            } catch (IlegalIndexException e){
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion de la consulta: " + (end - begin) + "ms");
        System.out.println();
    }

    public void Query4() {
        /**
         * Teniendo en cuenta los siguientes roles: actor y actress, se quiere
         * obtener cual es el año más habitual en el que nacen dichos individuos.
         * Es decir, dadas las películas realizadas, se quiere ver cuál es el año de
         * nacimiento mas frecuente para las actrices y cuál es el año más habitual
         * de nacimiento para los actores
         */

        int debbug_text = 0;

        long start_time = System.currentTimeMillis();

        int cantidad_de_cast_members = 297705;

        OpenHash<String, String> keys_de_castMember_que_vamos_a_filtrar = new OpenHash(cantidad_de_cast_members);

        // Cargamos el arreglo con las keys

        for(int i = 0; i < cast_member_storage.getTableHashSize(); i++){

            OpenHashNode<String, CastMember> temp_node = cast_member_storage.getPosition(i);

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

        int cantidad_de_anos = year_actual-year0; //no incluye 2021, aunque nacio aca


        int[][] count_anos = new int[cantidad_de_anos][2];
        int[] max_ano = new int[2];



        for(int i = 0; i < actors.size(); i++){
            String actor_key_String = null;
            try {
                actor_key_String = actors.get(i);
                CastMember actor = cast_member_storage.get(actor_key_String);

                Integer actor_birth_year = actor.getBirth_year();
                if(actor_birth_year != null) {
                    int year = actor_birth_year;
                    int index = year - year0;
                    count_anos[index][0]++;

                }
            } catch (IlegalIndexException e) {
                e.printStackTrace();
            }
        }



        for(int i = 0; i < actresses.size(); i++){


            String actress_key_String = null;
            try {
                actress_key_String = actresses.get(i);

                CastMember actress = cast_member_storage.get(actress_key_String);

                Integer actress_birth_year = actress.getBirth_year();
                if(actress_birth_year != null) {
                    int year = actress_birth_year;
                    int index = year - year0;
                    count_anos[index][1]++;

                }
            } catch (IlegalIndexException e) {
                e.printStackTrace();
            }


        }
        int sum = 0;

        for(int i = 0; i < cantidad_de_anos; i++){


            sum = sum + count_anos[i][0] + count_anos[i][1];
            if(count_anos[i][0] > count_anos[max_ano[0]][0]){

                max_ano[0] = i;
            }
            if(count_anos[i][1] > count_anos[max_ano[1]][1]){
                max_ano[1] = i;
            }
        }

        //System.out.println("Cantidad de actores/actrices: " + sum);
        int max_ano_actores = max_ano[0] + year0;
        int max_ano_actress = max_ano[1] + year0;
        System.out.println("Actores: ");
        System.out.println("    Año: " + max_ano_actores);
        System.out.println("    Cantidad: " + count_anos[max_ano[0]][0]);
        System.out.println();
        System.out.println("Actrices: ");
        System.out.println("    Año: " + max_ano_actress);
        System.out.println("    Cantidad: " + count_anos[max_ano[1]][1]);
        System.out.println();

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        System.out.println("Tiempo de ejecucion de la consulta: " + time_elapsed + "ms");
/*
        cantidadProfesion("actor");

        System.out.println("________________");
*/
        //Query4();

    }

    public void Query5(){
        long start_time = System.currentTimeMillis();
        MyClosedHash<String, Integer> result = new MyClosedHash(100000);
        for (int i = 0; i < movie_storage.getTableHashSize(); i++){
            OpenHashNode<String , Movie> peli = movie_storage.getPosition(i);
            while(peli != null){

                if(TieneActores(peli.getKey())){
                    ListaEnlazada<String> temp = peli.getValue().getGenre();

                    for (int j = 0; j < temp.size(); j++){

                        try {
                            result.put(temp.get(j), 1);

                        } catch (IlegalIndexException e) {
                            //e.printStackTrace();

                        }
                    }
                }
                peli = peli.getNext();
            }
        }



        // Ahora, en result, tenemos todos los generos en el closed Hahs. Hacemos bubblesort 10 veces y terminamos:
        result.Bubblesort(10);

        for (int i = 0; i < 10; i++){
            ClosedHashNode<String, Integer> temp = result.getPosition(result.getTableHashSize()-1-i);

            System.out.println("Genero pelicula: " + temp.getKey());
            System.out.println("Cantidad: " + temp.getReps());
            System.out.println();
        }

        long end_time = System.currentTimeMillis();
        System.out.println("tiempo de ejecución de la consulta: " + (end_time - start_time) + "ms");
        System.out.println();

    }

    public OpenHash<String,Integer> obtener_death_count(ArrayList<String> keys_pais_y_profesion, int death_hash_table_size){
        int cantidad_de_profesionales = keys_pais_y_profesion.size();

        OpenHash<String, Integer> cause_of_death_hash = new OpenHash(death_hash_table_size);//48268 elementos que cumplen cond

        for(int i = 0; i < cantidad_de_profesionales; i++) {
            String profesional_imdb_name = null;
            try {
                profesional_imdb_name = keys_pais_y_profesion.get(i);

                OpenHashNode<String, CastMember> temp_node = cast_member_storage.getNode(profesional_imdb_name);

                CastMember cast_member_posta = temp_node.getValue();


                CauseOfDeath temp_cause_of_death_de_cast_member_posta = cast_member_posta.getCauseOfDeath();

                if(temp_cause_of_death_de_cast_member_posta != null)   {

                    String temp_string_cause_of_death_de_cast_member_posta = temp_cause_of_death_de_cast_member_posta.getName();

                    OpenHashNode<String, Integer> nodo_temporal = cause_of_death_hash.getNode(temp_string_cause_of_death_de_cast_member_posta);;
                    if (nodo_temporal == null) {
                        cause_of_death_hash.put(temp_string_cause_of_death_de_cast_member_posta, 1);
                    } else {
                        while (!nodo_temporal.getKey().equals(temp_string_cause_of_death_de_cast_member_posta)) {
                            nodo_temporal = nodo_temporal.getNext();
                            if (nodo_temporal == null)
                                break;
                        }
                        if (nodo_temporal != null) {
                            int current_cout = nodo_temporal.getValue();

                            nodo_temporal.setValue(current_cout + 1);
                        }
                    }
                }


            } catch (IlegalIndexException e) {
                e.printStackTrace();
            }
        }
        return cause_of_death_hash;
    }

    public OpenHash<String,String> obtener_por_paises(String[] key_words_paises){

        OpenHash<String,String> keys_pais_pre = new OpenHash(297705);

        int N = cast_member_storage.getTableHashSize();
        for(int i = 0; i < N; i++){

            OpenHashNode<String, CastMember> temp_cell = cast_member_storage.getPosition(i);
            if(temp_cell != null) {
                String paisito = temp_cell.getValue().getPlace_of_birth();
                if(paisito != null)
                    if(multiContains(paisito,key_words_paises)) {
                        String s = temp_cell.getValue().getImbdNameId();
                        keys_pais_pre.put(s,s);
                    }

                temp_cell = temp_cell.getNext();

                while (temp_cell != null) {

                    paisito = temp_cell.getValue().getPlace_of_birth();
                    if(paisito != null) {
                        if (multiContains(paisito, key_words_paises)) {
                            String s = temp_cell.getValue().getImbdNameId();
                            keys_pais_pre.put(s, s);
                        }
                    }

                    temp_cell = temp_cell.getNext();
                }
            }

        }

        return keys_pais_pre;
    }

    public ArrayList<String> obtener_por_profesion_dado_hash(String[] Profesiones, OpenHash<String,String> keys_pais, int debbug_text){

        int HashSize = keys_pais.getTableHashSize()*2;

        ArrayList<String> keys_pais_y_profesion = new ArrayList<>();

        int relations_to_try = movie_cast_member_storage.getTableHashSize();
        OpenHash<String, String> elementosAnalizados = new OpenHash<>(HashSize);

        for(int index = 0; index < movie_cast_member_storage.getTableHashSize(); index++){

            if(debbug_text >= 3) System.out.println("Indice del hash: " + Integer.toString(index));


            OpenHashNode<String, MovieCastMember> temp_cell = movie_cast_member_storage.getPosition(index);


            while (temp_cell != null) {

                String ActorId = temp_cell.getValue().getImbdNameId();
                OpenHashNode<String, String> temp_node = keys_pais.getNode(ActorId);


                while (temp_node != null) {

                    if (temp_node.getValue().equals(ActorId)) {

                        String profesion = temp_cell.getValue().getCategory();

                        if (multiContains(profesion, Profesiones) &&
                        !elementosAnalizados.contains(ActorId)) {
                            elementosAnalizados.put(ActorId, ActorId);
                            keys_pais_y_profesion.add(ActorId);
                        }
                    }

                    temp_node = temp_node.getNext();


                }
                temp_cell = temp_cell.getNext();
            }

        }
        return keys_pais_y_profesion;
    }

    public boolean TieneActores(String movieId){
        OpenHashNode<String, MovieCastMember> temp = movie_cast_member_storage.getNode(movieId);
        while(temp != null){
            if(temp.getKey().equals(movieId)) {
                if (temp.getValue().getCategory().contains("actor") ||
                        temp.getValue().getCategory().contains("actress")) {

                    CastMember actor = cast_member_storage.get(temp.getValue().getImbdNameId());
                    if (actor.getChildren() >= 2) {
                        return true;
                    }
                }
            }
            temp = temp.getNext();
        }
        return false;

    }


}
