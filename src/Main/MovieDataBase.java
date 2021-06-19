package Main;

import Exceptions.EmptyHeapException;
import Exceptions.IlegalIndexException;
import Modelo.*;
import TADS.*;
import Utilidades.Loader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import static Utilidades.Functions.multiContains;

public class MovieDataBase {

    Path path = Paths.get("."); // http://tutorials.jenkov.com/java-nio/path.html de aca aprendi a usar paths
    String path_absolute = path.toAbsolutePath().toString();

    public String proyect_path = path_absolute.substring(0,path_absolute.length()-1);
    public String file_path = proyect_path + "src\\Files\\";

    Loader loader = new Loader(file_path);

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
            int debbug_text = 0;
            movie_cast_member_storage = loader.load_movie_cast_member(debbug_text);
            movie_rating_storage = loader.load_review_database(debbug_text);
            movie_storage = loader.load_movie_database(debbug_text);
            cast_member_storage = loader.load_castmember_database(debbug_text); //debbug_tex = 0
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

    public void Query(int Q) throws IlegalIndexException {
        if(data_loaded) {
            switch (Q) {
                case 1:
                    Query1();
                    break;
                case 2:
                    Querry2(0);
                    break;
                case 3:
                    Query3();
                    break;
                case 4:
                    Querry4(0);
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

            OpenHashNode<Integer, MovieCastMember> temp = movie_cast_member_storage.getPosition(i);
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
            int name_string_length = retorno.getKey().length();
            String imbd_title_id_string = retorno.getKey().substring(2,name_string_length);
            int imdb_title_id = Integer.parseInt(imbd_title_id_string);

            CastMember temp = cast_member_storage.get(imdb_title_id);
            String name = temp.getName();

            System.out.println("Nombre actor/actriz: " + name);
            System.out.println("Cantidad de apariciones: " + retorno.getReps());
            System.out.println(); //

        }

        long end = System.currentTimeMillis();
        System.out.println("Tiempo de ejecicion de la consulta: " + (end - begin) + "ms");
        System.out.println();


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
            System.out.println();
        }

        System.out.println("Tiempo de ejecucion de la consulta: " + time_elapsed + "ms");
        System.out.println();
    }

    public void Query3(){
        long begin = System.currentTimeMillis();

        int counter = 0; //El counter nos indicará el momento en el que obtenemos las 14 peliculas:
        //Los elementos que sacaremos los colocamos en elementosSacados, para luego insertarlos de nuevo:
        ListaEnlazada<MovieRating> elementosSacados = new ListaEnlazada<>();
        Lista<Movie> resultados = new ListaEnlazada<>(); //Aquí iran las 14 peliculas que queremos que nos
        //devuelva la consulta

        MovieRating temp;
        Movie pelicula;
        while(counter < 14){
            try {
                temp = movie_rating_storage.pop();
                elementosSacados.add(temp);
                int name_string_length = temp.getImdbTitle().length();
                String imbd_title_id_string = temp.getImdbTitle().substring(2,name_string_length);
                int imdb_title_id = Integer.parseInt(imbd_title_id_string);

                pelicula = movie_storage.get(imdb_title_id);
                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

                if(pelicula.getDatePublished() != null &&
                        pelicula.getDatePublished().compareTo(sdformat.parse("1950-01-01")) >= 0
                        && pelicula.getDatePublished().compareTo(sdformat.parse("1960-01-01")) < 0){
                    resultados.add(pelicula);
                    counter ++;
                }

            } catch (EmptyHeapException | ParseException e) {
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
                //Pasamos el id de pelicula a integer, para buscar en la relación esa pelicula:
                int name_string_length = pelicula.getImbdTitled().length();
                String imbd_title_id_string = pelicula.getImbdTitled().substring(2,name_string_length);
                int imdb_title_id = Integer.parseInt(imbd_title_id_string);

                OpenHashNode<Integer, MovieCastMember> relation = movie_cast_member_storage.getNode(imdb_title_id);
                //Buscamos todos los actores que actuaron en la pelicula:
                int canitdadAcotres = 0;
                while(relation != null){
                    if(relation.getKey().equals(imdb_title_id) && (relation.getValue().getCategory().contains("actor")
                            || relation.getValue().getCategory().contains("actress"))){
                        // Pasamos el id de actor a Integer:
                        name_string_length = relation.getValue().getImbdNameId().length();
                        String imdb_CastMember_id_string = relation.getValue().getImbdNameId().substring(2,name_string_length);
                        int imdb_CastMember_id = Integer.parseInt(imdb_CastMember_id_string);
                        CastMember actor = cast_member_storage.get(imdb_CastMember_id);

                        if(actor != null && actor.getHeight() != null) {
                            sumaAltura = sumaAltura + actor.getHeight();
                            canitdadAcotres ++;
                        }

                    }
                    relation = relation.getNext();
                }

                if(sumaAltura != 0){
                    float promedio = sumaAltura/canitdadAcotres;
                    System.out.println("Id pelicula: " + pelicula.getImbdTitled());
                    System.out.println("Nombre: " + pelicula.getOriginalTitle());
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

        OpenHash<String, String> keys_de_castMember_que_vamos_a_filtrar = new OpenHash<>(cantidad_de_cast_members);

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

        int cantidad_de_anos = year_actual-year0; //no incluye 2021, aunque nacio aca


        int[][] count_anos = new int[cantidad_de_anos][2];
        int[] max_ano = new int[2];



        for(int i = 0; i < actors.size(); i++){
            boolean entre = false;
            String actor_key_String = actors.get(i);
            int actor_key_int = Integer.parseInt(actor_key_String.substring(2,actor_key_String.length()));
            CastMember actor = cast_member_storage.get(actor_key_int);

            Date date_of_birth_of_actor = actor.getBirthDate();
            if(date_of_birth_of_actor != null) {
                int year = date_of_birth_of_actor.getYear() + 1900;
                int index = year - year0;
                count_anos[index][0]++;
                entre = true;

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

        System.out.println("Cantidad de actores/actrices: " + sum);
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

        cantidadProfesion("actor");

        System.out.println("________________");

        Query4();

    }


    public void Query5(){
        long start_time = System.currentTimeMillis();
        MyClosedHash<String, Integer> result = new MyClosedHash<>(5000000);
        for (int i = 0; i < movie_storage.getTableHashSize(); i++){
            OpenHashNode<Integer, Movie> peli = movie_storage.getPosition(i);
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

                temp_cell = temp_cell.getNext();

                while (temp_cell != null) {

                    paisito = temp_cell.getValue().getBirthCountry();
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


            OpenHashNode<Integer, MovieCastMember> temp_cell = movie_cast_member_storage.getPosition(index);


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



            /*
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



             */



        }

        System.out.println(keys_pais_y_profesion.size());
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

    public ListaEnlazada<Integer> peliculasDeUnActor(String idActor){
        ListaEnlazada<Integer> result = new ListaEnlazada<>();
        for (int i = 0; i < movie_cast_member_storage.getTableHashSize(); i++){
            OpenHashNode<Integer, MovieCastMember> temp = movie_cast_member_storage.getNode(i);
            while(temp != null){
                if(temp.getValue().getImbdNameId().equals(idActor)){
                    result.add(temp.getKey());
                }
                temp = temp.getNext();
            }
        }
        return result;
    }

    public boolean TieneActores(Integer movieId){
        OpenHashNode<Integer, MovieCastMember> temp = movie_cast_member_storage.getNode(movieId);
        while(temp != null){
            if(temp.getKey().equals(movieId)) {
                if (temp.getValue().getCategory().contains("actor") ||
                        temp.getValue().getCategory().contains("actress")) {
                    //Pasamos el Id en la relacion a integer, para buscar en castMember:
                    int name_string_length = temp.getValue().getImbdNameId().length();
                    String imdb_CastMember_id_string = temp.getValue().getImbdNameId().substring(2, name_string_length);
                    int imdb_CastMember_id = Integer.parseInt(imdb_CastMember_id_string);

                    CastMember actor = cast_member_storage.get(imdb_CastMember_id);
                    if (actor.getChildren() > 2) {
                        return true;
                    }
                }
            }
            temp = temp.getNext();
        }
        return false;

    }

    public void prueba1(){
        int counter = 0;
        int i = 0;
        while(counter <= 200){
            if(cast_member_storage.getPosition(i) != null) {
                System.out.println(cast_member_storage.getPosition(i).getValue().getName());
                counter ++;
            }
            i ++;

            if(i >= 1000000){
                counter = 400;
            }
        }
    }

    public void prueba2() throws IlegalIndexException {
        for (int i = 0; i < 1000; i++){
            if(movie_storage.getPosition(i) != null){
                ListaEnlazada<String> temp = movie_storage.getPosition(i).getValue().getGenre();
                temp.print();
            }
        }
    }

    public void cantidadDeGenero(String genero){
        int counter = 0;
        for(int i = 0; i < movie_storage.getTableHashSize(); i++){
            OpenHashNode<Integer, Movie> temp = movie_storage.getNode(i);
            while(temp != null){
                ListaEnlazada<String> genres = temp.getValue().getGenre();
                for(int j = 0; j < genres.size(); j++){
                    if(genres.contains(genero)){
                        counter ++;
                    }
                }
                temp = temp.getNext();
            }
        }

        System.out.println("genero: " + genero + " " + counter);
    }

    public MyClosedHash<String, CastMember> cantidadProfesion(String profesion){

        MyClosedHash<String, CastMember> result = new MyClosedHash<>(10000000);
        for(int i = 0; i < movie_cast_member_storage.getTableHashSize(); i++){
            OpenHashNode<Integer, MovieCastMember> temp = movie_cast_member_storage.getPosition(i);
            while(temp != null){
                if(temp.getValue().getCategory().contains(profesion)){
                    int name_string_length = temp.getValue().getImbdNameId().length();
                    String imbd_title_id_string = temp.getValue().getImbdNameId().substring(2,name_string_length);
                    int imdb_title_id = Integer.parseInt(imbd_title_id_string);

                    CastMember actor = cast_member_storage.get(imdb_title_id);

                    result.put(temp.getValue().getImbdNameId(), actor);

                }
                temp = temp.getNext();
            }


        }

        System.out.println("Cantidad de" + profesion + "= " + result.size());

        return result;
    }

    public void Query4(){

        long init = System.currentTimeMillis();
        MyClosedHash<Integer, Integer> nacimientoActores = new MyClosedHash<>(2000);
        MyClosedHash<Integer, Integer> nacimientoActrices = new MyClosedHash<>(2000);
        MyClosedHash<String, CastMember> actores = cantidadProfesion("actor");
        MyClosedHash<String, CastMember> actrices = cantidadProfesion("actress");

        int iteraciones = 0;
        if(actores.getTableHashSize() >= actrices.getTableHashSize()){
            iteraciones = actores.getTableHashSize();
        } else{
            iteraciones = actrices.getTableHashSize();
        }

        MyClosedHash<String, CastMember> actores_con_fecha = new MyClosedHash<>(1000000);
        MyClosedHash<String, CastMember> actrices_con_fecha = new MyClosedHash<>(1000000);
        for(int i = 0; i < iteraciones; i++){


            if(actores.getPosition(i) != null){
                if(actores.getPosition(i).getValue().getBirthDate() != null) {
                    actores_con_fecha.put(actores.getPosition(i).getKey(), actores.getPosition(i).getValue());
                    nacimientoActores.put(actores.getPosition(i).getValue().getBirthDate().getYear() + 1900, 1);
                }
            }

            if(actrices.getPosition(i) != null){
                if(actrices.getPosition(i).getValue().getBirthDate() != null) {
                    actrices_con_fecha.put(actrices.getPosition(i).getKey(), actrices.getPosition(i).getValue());
                    nacimientoActrices.put(actrices.getPosition(i).getValue().getBirthDate().getYear() + 1900, 1);
                }
            }
        }

        nacimientoActores.Bubblesort(1);
        nacimientoActrices.Bubblesort(1);


        System.out.println("actores: " + actores_con_fecha.size());
        System.out.println("actrices: " + actrices_con_fecha.size());




        Integer anoActores = nacimientoActores.getPosition(nacimientoActores.getTableHashSize() - 1).getKey();
        int cantidadActores = nacimientoActores.getPosition(nacimientoActores.getTableHashSize() - 1).getReps();
        Integer anoActrices = nacimientoActrices.getPosition(nacimientoActrices.getTableHashSize() - 1).getKey();
        int cantidadActrices = nacimientoActrices.getPosition(nacimientoActrices.getTableHashSize() - 1).getReps();
        System.out.println("Actores: ");
        System.out.println("    Año: " + anoActores);
        System.out.println("    Cantidad: " + cantidadActores);
        System.out.println();
        System.out.println("Actrices: ");
        System.out.println("    Año: " + anoActrices);
        System.out.println("    Cantidad: " + cantidadActrices);
        System.out.println();

        long end = System.currentTimeMillis();

        System.out.println("Time elapsed: " + (end - init));

    }

}
