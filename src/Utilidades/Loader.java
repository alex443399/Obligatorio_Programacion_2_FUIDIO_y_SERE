package Utilidades;


import Modelo.Movie;
import TADS.MyBSTImpl;
import TADS.OpenHash;

import java.io.BufferedReader;
import java.io.FileReader;

public class Loader {


    public OpenHash<Integer, Movie> LoadMovies() throws Exception{

        int number_of_columns = 22, number_of_rows = 85854;

        OpenHash<Integer, Movie> movie_storage = new OpenHash<Integer, Movie>(number_of_rows);//(85854);

        System.out.println("Loading Begining...");

        String path = "C:\\Users\\alex4\\IdeaProjects\\Obligatorio Programacion 2 v1\\src\\Files\\IMDb movies.csv";

        String delimiter = ",";

        BufferedReader br = new BufferedReader(new FileReader(path));

        String header_line = br.readLine();

        String[] headers = header_line.split(",");

        String line = "";
        int i = 0;
        while((line = br.readLine()) != null && i < 1000){//
            i++;
            //System.out.println(line);
            String[] registro = Functions.StringArrayFromCsvLine(line,',','"',number_of_columns);
/*
            for(int j = 0; j < registro.length; j++)
                System.out.println(registro[j]);
*/
            int name_string_length = registro[0].length();
            String imbd_title_id_string = registro[0].substring(2,name_string_length);
            int imdb_title_id = Integer.parseInt(imbd_title_id_string);


            System.out.println("Begining to load: tt" + imbd_title_id_string + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));

            Movie movie_to_load = new Movie(registro[0],registro[1],registro[2],registro[3],registro[4],registro[5],registro[6],registro[7],registro[8],registro[9],registro[10],registro[11],registro[12],registro[13],registro[14],registro[15],registro[16],registro[17],registro[18],registro[19],registro[20],registro[21]);


            movie_storage.put(
                    imdb_title_id,
                    movie_to_load
            );

            System.out.println("Loaded tt " + Integer.toString(imdb_title_id) + ", fraction: " + Integer.toString(i) + "/" + Integer.toString(number_of_rows));
        }
        System.out.println("Loading Ended");
        return movie_storage;
    }


}
