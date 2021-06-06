package Utilidades;


import Modelo.CastMember;
import Modelo.Movie;
import Modelo.MovieRating;
import Modelo.Rating;
import TADS.MyBSTImpl;
import TADS.OpenHash;

import java.io.BufferedReader;
import java.io.FileReader;

public class Loader {


    public OpenHash<Integer, Movie> load_movie_database(int debbug_text) throws Exception{

        int number_of_columns = 22, number_of_rows = 85854;

        OpenHash<Integer, Movie> movie_storage = new OpenHash<Integer, Movie>(number_of_rows);

        if(debbug_text>0) System.out.println("Loading Movies Begining...");

        String path = "C:\\Users\\alex4\\IdeaProjects\\Obligatorio Programacion 2 v1\\src\\Files\\IMDb movies.csv";

        String delimiter = ",";

        BufferedReader br = new BufferedReader(new FileReader(path));

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
                        imdb_title_id,
                        movie_to_load
                );
                inserted_counter++;
            }
            catch (Exception e){
                if(debbug_text>2) System.out.println("Error cargando durante la iteracion: " + Integer.toString(i) + " con titulo imdb: " + registro[0]);
                error_counter++;
            }

            if(debbug_text>2) System.out.println("Loaded tt " + Integer.toString(imdb_title_id) + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
        }
        if(debbug_text>0) System.out.println("Loading Movies Ended");
        //if(debbug_text>0) print time
        if(debbug_text>1) System.out.println("Errores: " + error_counter);
        if(debbug_text>1) System.out.println("Inserciones: " + inserted_counter);
        return movie_storage;
    }

    public OpenHash<Integer, CastMember> load_castmember_database(int debbug_text) throws Exception{//debbug_text 0 -> nada, 1-> Lo minimo letra, 2 -> letra + error y counter, 3 -> cada cargado

        int number_of_columns = 17, number_of_rows = 297705;

        OpenHash<Integer, CastMember> cast_member_storage = new OpenHash<Integer, CastMember>(number_of_rows);
        if(debbug_text>0) System.out.println("Loading Cast Members Begining...");

        String path = "C:\\Users\\alex4\\IdeaProjects\\Obligatorio Programacion 2 v1\\src\\Files\\IMDb names.csv";

        String delimiter = ",";

        BufferedReader br = new BufferedReader(new FileReader(path));

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
            if(condition_nextline) condition_nextline = condition_nextline && (!(nextLine.charAt(0) == 'n' && nextLine.charAt(1) == 'm'));

            while(condition_nextline){
                line = line + nextLine;
                nextLine = br.readLine();

                condition_nextline = (nextLine != null);
                if(condition_nextline) condition_nextline = condition_nextline && (!(nextLine.charAt(0) == 'n' && nextLine.charAt(1) == 'm'));

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
                        imdb_CastMember_id,
                        castMember_to_load
                );
                inserted_counter++;
            }
            catch (Exception e){
                if(debbug_text>2) System.out.println("Error cargando durante la iteracion: " + Integer.toString(i) + " con nombre imdb: " + registro[0]);
                error_counter++;
                e.printStackTrace();
            }

            if(debbug_text>2) System.out.println("Loaded " + registro[0] + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
        }
        if(debbug_text>0) System.out.println("Loading CastMembers Ended");
        //if(debbug_text>0) print time
        if(debbug_text>1) System.out.println("Errores: " + error_counter);
        if(debbug_text>1) System.out.println("Inserciones: " + inserted_counter);

        return cast_member_storage;
    }

    public OpenHash<Integer, MovieRating> load_review_database(int debbug_text) throws Exception{

        int number_of_columns = 49, number_of_rows = 85854;

        OpenHash<Integer, MovieRating> rating_storage = new OpenHash<Integer, MovieRating>(number_of_rows);

        if(debbug_text>0) System.out.println("Loading Ratings Begining...");

        String path = "C:\\Users\\alex4\\IdeaProjects\\Obligatorio Programacion 2 v1\\src\\Files\\IMDb ratings.csv";

        String delimiter = ",";

        BufferedReader br = new BufferedReader(new FileReader(path));

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
                MovieRating rating_to_load = new MovieRating
                        (registro[0], registro[1], registro[2], registro[3], registro[4], registro[5], registro[6], registro[7], registro[8], registro[9],
                                registro[10], registro[11], registro[12], registro[13], registro[14], registro[15], registro[16], registro[17], registro[18], registro[19],
                                registro[20], registro[21], registro[22], registro[23], registro[24], registro[25], registro[26], registro[27], registro[28], registro[29],
                                registro[30], registro[31], registro[32], registro[33], registro[34], registro[35], registro[36], registro[37], registro[38], registro[39],
                                registro[40], registro[41], registro[42], registro[43], registro[44], registro[45], registro[46], registro[47], registro[48]
                        );

                rating_storage.put(
                        imdb_title_id,
                        rating_to_load
                );
                inserted_counter++;
            }
            catch (Exception e){
                if(debbug_text>2) System.out.println("Error cargando durante la iteracion: " + Integer.toString(i) + " con titulo imdb: " + registro[0]);
                error_counter++;
            }

            if(debbug_text>2) System.out.println("Loaded tt" + Integer.toString(imdb_title_id) + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
        }
        if(debbug_text>0) System.out.println("Loading Ratings Ended");
        //if(debbug_text>0) print time
        if(debbug_text>1) System.out.println("Errores: " + error_counter);
        if(debbug_text>1) System.out.println("Inserciones: " + inserted_counter);
        return rating_storage;
    }


}
