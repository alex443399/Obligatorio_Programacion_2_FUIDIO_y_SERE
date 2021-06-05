package Main;
import Modelo.Movie;
import TADS.ArrayList;
import TADS.MyBSTImpl;
import TADS.OpenHash;
import Utilidades.Functions;
import Utilidades.Loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {


    public static void main(String[] args) throws Exception{

        Loader loader = new Loader();

        OpenHash<Integer, Movie> movie_storage = loader.LoadMovies();


    }
}
