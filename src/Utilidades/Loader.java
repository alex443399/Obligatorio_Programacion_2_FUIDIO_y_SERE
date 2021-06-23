package Utilidades;


import Modelo.*;
import TADS.HeapImp;
import TADS.MyBSTImpl;
import TADS.OpenHash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Loader {

    public static final Float hash_load_factor = 0.8f;

    public final String proyect_path;

    private OpenHash<String, Movie> movieHash;
    private OpenHash<String, CastMember> castMemberHash;
    private OpenHash<String, MovieCastMember> movieCastMemberHash;
    private HeapImp<MovieRating> movieRatingHash;



    public Loader(String proyect_path) {
        this.proyect_path = proyect_path;
    }

    public static final String movies_file_name = "IMDb movies.csv";
    public static final String cast_member_file_name = "IMDb names.csv";
    public static final String movie_cast_member_file_name = "IMDb title_principals.csv";
    public static final String rating_file_name = "IMDb ratings.csv";

    public OpenHash<String, Movie> load_movie_database(int debbug_text) throws Exception{
        long start_time = System.currentTimeMillis();

        int number_of_columns = 22, number_of_rows = 85854;

        int hash_size = (int) (number_of_rows/hash_load_factor);

        OpenHash<String, Movie> movie_storage = new OpenHash(hash_size);

        if(debbug_text>0) System.out.println("Loading Movies Begining...");

        String path = proyect_path+movies_file_name;

        String delimiter = ",";

        BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);

        String header_line = br.readLine();

        String[] headers = header_line.split(",");

        String line = "";
        int i = 0;
        int error_counter = 0;
        int inserted_counter = 0;

        while((line = br.readLine()) != null){// && i < 100
            i++;

            String[] registro = Functions.StringArrayFromCsvLine(line,',','"',number_of_columns);

            int name_string_length = registro[0].length();
            String imbd_title_id_string = registro[0].substring(2,name_string_length);
            int imdb_title_id = Integer.parseInt(imbd_title_id_string);


            if(debbug_text>2) System.out.println("Begining to load: tt" + imbd_title_id_string + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
            try {
                Movie movie_to_load = new Movie(registro[0], registro[1], registro[2], registro[3], registro[4], registro[5], registro[6], registro[7], registro[8], registro[9], registro[10], registro[11], registro[12], registro[13], registro[14], registro[15], registro[16], registro[17], registro[18], registro[19], registro[20], registro[21]);

                movie_storage.put(
                        registro[0],
                        movie_to_load
                );
                inserted_counter++;
            }
            catch (Exception e){
                if(debbug_text>2) System.out.println("Error cargando durante la iteracion: " + Integer.toString(i) + " con titulo imdb: " + registro[0]);
                error_counter++;
                if(debbug_text==-1) e.printStackTrace();
            }

            if(debbug_text>2) System.out.println("Loaded tt " + Integer.toString(imdb_title_id) + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
        }

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        if(debbug_text>0) System.out.println("Loading Movies Ended");
        if(debbug_text>0) System.out.println("Duration: " + Long.toString(time_elapsed) + "ms");
        if(debbug_text>1) System.out.println("Errores: " + error_counter);
        if(debbug_text>1) System.out.println("Inserciones: " + inserted_counter);

        movieHash = movie_storage;
        return movie_storage;
    }

    public OpenHash<String, CastMember> load_castmember_database(int debbug_text) throws Exception{//debbug_text 0 -> nada, 1-> Lo minimo letra, 2 -> letra + error y counter, 3 -> cada cargado

        long start_time = System.currentTimeMillis();

        int number_of_columns = 17, number_of_rows = 297705;

        int hash_size = (int) (number_of_rows/hash_load_factor);

        OpenHash<String, CastMember> cast_member_storage = new OpenHash(hash_size);
        if(debbug_text>0) System.out.println("Loading Cast Members Begining...");

        String path = proyect_path+cast_member_file_name;

        String delimiter = ",";

        BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);

        String header_line = br.readLine();

        String[] headers = header_line.split(",");

        String line = "";
        int i = 0;
        int error_counter = 0;
        int inserted_counter = 0;
        String nextLine = br.readLine();
        boolean condition_nextline;

        while(nextLine != null){// && i < 295292+1

            //Reconstruir la linea por multiline data

            line = nextLine;

            nextLine = br.readLine();

            condition_nextline = (nextLine != null);
            if(condition_nextline) condition_nextline = (!(nextLine.charAt(0) == 'n' && nextLine.charAt(1) == 'm'));

            while(condition_nextline){
                line = line + nextLine;
                nextLine = br.readLine();

                condition_nextline = (nextLine != null);
                if(condition_nextline) condition_nextline = (!(nextLine.charAt(0) == 'n' && nextLine.charAt(1) == 'm'));

            }

            //System.out.println(line);

            i++;

            String[] registro = Functions.StringArrayFromCsvLine(line,',','"',number_of_columns);

            int name_string_length = registro[0].length();
            String imdb_CastMember_id_string = registro[0].substring(2,name_string_length);
            int imdb_CastMember_id = Integer.parseInt(imdb_CastMember_id_string);


            if(debbug_text>2) System.out.println("Begining to load: " + registro[0] + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
            try {
                CastMember castMember_to_load = new CastMember(registro[0], registro[1], registro[2], registro[3], registro[4], registro[5], registro[6], registro[7], registro[8], registro[9], registro[10], registro[11], registro[12], registro[13], registro[14], registro[15], registro[16]);

                cast_member_storage.put(
                        registro[0],
                        castMember_to_load
                );
                inserted_counter++;
            }
            catch (Exception e){
                if(debbug_text>2) System.out.println("Error cargando durante la iteracion: " + Integer.toString(i) + " con nombre imdb: " + registro[0]);
                error_counter++;
                if(debbug_text==-1) e.printStackTrace();
            }

            if(debbug_text>2) System.out.println("Loaded " + registro[0] + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
        }

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        if(debbug_text>0) System.out.println("Loading Cast Members Ended");
        if(debbug_text>0) System.out.println("Duration: " + Long.toString(time_elapsed) + "ms");
        if(debbug_text>1) System.out.println("Errores: " + error_counter);
        if(debbug_text>1) System.out.println("Inserciones: " + inserted_counter);


        castMemberHash = cast_member_storage;
        return cast_member_storage;
    }

    public HeapImp<MovieRating> load_review_database(int debbug_text) throws Exception{

        long start_time = System.currentTimeMillis();

        int number_of_columns = 49, number_of_rows = 85855;

        HeapImp<MovieRating> rating_storage = new HeapImp(number_of_rows); //Heap es arbol, el tamano exacto y necesario del heap lo determina el cosntructor de la clase.

        if(debbug_text>0) System.out.println("Loading Ratings Begining...");

        String path = proyect_path+rating_file_name;

        String delimiter = ",";

        BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);

        String header_line = br.readLine();

        String[] headers = header_line.split(",");

        String line = "";
        int i = 0;
        int error_counter = 0;
        int inserted_counter = 0;

        while((line = br.readLine()) != null){// && i < 2
            i++;

            String[] registro = Functions.StringArrayFromCsvLine(line,',','"',number_of_columns);

            int name_string_length = registro[0].length();
            String imbd_title_id_string = registro[0].substring(2,name_string_length);
            int imdb_title_id = Integer.parseInt(imbd_title_id_string);


            if(debbug_text>2) System.out.println("Begining to load: tt" + imbd_title_id_string + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));

            try {

                Movie peliculaAsociada = movieHash.get(registro[0]);
                MovieRating rating_to_load = new MovieRating
                        (peliculaAsociada, registro[0], registro[1], registro[2], registro[3], registro[4], registro[5], registro[6], registro[7], registro[8], registro[9],
                                registro[10], registro[11], registro[12], registro[13], registro[14], registro[15], registro[16], registro[17], registro[18], registro[19],
                                registro[20], registro[21], registro[22], registro[23], registro[24], registro[25], registro[26], registro[27], registro[28], registro[29],
                                registro[30], registro[31], registro[32], registro[33], registro[34], registro[35], registro[36], registro[37], registro[38], registro[39],
                                registro[40], registro[41], registro[42], registro[43], registro[44], registro[45], registro[46], registro[47], registro[48]
                        );

                rating_storage.insert(
                        rating_to_load
                );
                if(debbug_text>2) System.out.println("Loaded tt" + Integer.toString(imdb_title_id) + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
                inserted_counter++;
            }
            catch (Exception e){
                if(debbug_text>2) System.out.println("Error cargando durante la iteracion: " + Integer.toString(i) + " con titulo imdb: " + registro[0]);
                error_counter++;
                if(debbug_text>3) e.printStackTrace();
                if(debbug_text==-1) e.printStackTrace();
            }

            }

        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        if(debbug_text>0) System.out.println("Loading Reviews Ended");
        if(debbug_text>0) System.out.println("Duration: " + Long.toString(time_elapsed) + "ms");
        if(debbug_text>1) System.out.println("Errores: " + error_counter);
        if(debbug_text>1) System.out.println("Inserciones: " + inserted_counter);

        movieRatingHash = rating_storage;
        return rating_storage;
    }

    public OpenHash<String, MovieCastMember> load_movie_cast_member(int debbug_text) throws Exception{

        long start_time = System.currentTimeMillis();

        int number_of_columns = 6, number_of_rows = 85855;

        int number_of_pk = 85855;

        int hash_size = (int) (number_of_pk/hash_load_factor);

        OpenHash<String, MovieCastMember> movie_cast_member_storage = new OpenHash(hash_size);

        if(debbug_text>0) System.out.println("Loading Movie Cast Members Begining...");

        String path = proyect_path+movie_cast_member_file_name;

        String delimiter = ",";

        BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);

        String header_line = br.readLine();

        String[] headers = header_line.split(",");

        String line = "";
        int i = 0;
        int error_counter = 0;
        int inserted_counter = 0;

        while((line = br.readLine()) != null){// && i < 1000
            i++;

            String[] registro = Functions.StringArrayFromCsvLine(line,',','"',number_of_columns);

            int movie_id_string_length = registro[0].length();
            String movie_id_number_string = registro[0].substring(2,movie_id_string_length);
            int movie_id_number_int = Integer.parseInt(movie_id_number_string);


            if(debbug_text>2) System.out.println("Begining to load: tt" + movie_id_number_string + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
            try {
                MovieCastMember movie_cast_member_to_load = new MovieCastMember(
                        registro[0], registro[1], registro[2], registro[3], registro[4], registro[5]
                );

                movie_cast_member_storage.put(
                        registro[0],
                        movie_cast_member_to_load
                );
                inserted_counter++;
            }
            catch (Exception e){
                if(debbug_text>2) System.out.println("Error cargando durante la iteracion: " + Integer.toString(i) + " con titulo imdb: " + registro[0]);
                error_counter++;
                if(debbug_text>3) e.printStackTrace();
                if(debbug_text==-1) e.printStackTrace();
            }

            if(debbug_text>2) System.out.println("Loaded tt " + Integer.toString(movie_id_number_int) + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
        }
        long end_time = System.currentTimeMillis();
        long time_elapsed = end_time-start_time;

        if(debbug_text>0) System.out.println("Loading Movie Cast Members Ended");
        if(debbug_text>0) System.out.println("Duration: " + Long.toString(time_elapsed) + "ms");

        if(debbug_text>1) System.out.println("Errores: " + error_counter);
        if(debbug_text>1) System.out.println("Inserciones: " + inserted_counter);

        movieCastMemberHash = movie_cast_member_storage;
        return movie_cast_member_storage;
    }


}
