package Main;
import TADS.ArrayList;
import Utilidades.Functions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws Exception{
        String path = "C:\\Users\\alex4\\IdeaProjects\\Obligatorio Programacion 2 v1\\src\\Files\\IMDb movies.csv";

        String line = "";
        String delimiter = ",";

        int N = 10;
        BufferedReader br = new BufferedReader(new FileReader(path));

        String header_line = br.readLine();

        String[] headers = header_line.split(delimiter);

        ArrayList<String> headers_list = Functions.StringArrayFromCsvLine(header_line,',');
        System.out.println(headers_list.get(0));
        System.out.println(headers_list.get(1));
        System.out.println(headers_list.get(2));
        System.out.println(headers_list.get(3));
        System.out.println(headers_list.get(4));
/*
        while( (line = br.readLine()) != null && N >= 0){
            String[] registro = line.split(delimiter);

            System.out.println(headers.length);
            System.out.println(registro.length);

            int l = headers.length;
            String outputteado = "";

            for(int i = 0; i < l-1; i++)
                outputteado += (headers[i] +
                        ": " + registro[i] + " | ");

            System.out.println(outputteado);

            N--;
        }
*/

    }
}
