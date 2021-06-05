package Main;
import Modelo.Movie;
import TADS.ArrayList;
import TADS.MyBSTImpl;
import TADS.OpenHash;
import TADS.OpenHashNode;
import Utilidades.Functions;
import Utilidades.Loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {


    public static void main(String[] args) throws Exception{

        Loader loader = new Loader();

        OpenHash<Integer, Movie> movie_storage = loader.LoadMovies();

        System.out.println(movie_storage.get(5500148).getTitled());

    }
}
