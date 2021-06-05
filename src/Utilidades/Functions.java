package Utilidades;

import TADS.ArrayList;
import TADS.ListaEnlazada;


public class Functions {

    public static String[] StringArrayFromCsvLine(String line, char del, char ignorar, int cantidad_de_columnas){
        int L = line.length();
        boolean inside = false;

        String[] resultado = new String[cantidad_de_columnas];

        int LeftIndex = 0, RightIndex = 0, current_column = 0;

        while(LeftIndex < L) {
            boolean condition_while_loop = RightIndex < L;
            if(condition_while_loop)
                condition_while_loop = (condition_while_loop) && (line.charAt(RightIndex) != del);
            // la condicion se tiene que evaluar en partes pq si evaluamos line.charAt sin saber si el indice es < L puede Out Of Bounds Exceptions

            while (condition_while_loop) {
                if(line.charAt(RightIndex) == ignorar)
                    inside = !inside;
                RightIndex++;
                /////////


                /////////
                condition_while_loop = RightIndex < L;
                if(condition_while_loop)
                    condition_while_loop = (condition_while_loop) &&
                            ((line.charAt(RightIndex) != del) || inside);

            }

            String temp = line.substring(LeftIndex, RightIndex);

            if(current_column >= cantidad_de_columnas) break;
            resultado[current_column] = temp;
            current_column++;

            RightIndex++;
            LeftIndex = RightIndex;
        }
        return resultado;
    }

    public static ListaEnlazada<String> ListaEnCelda(String line){
        ListaEnlazada<String> resultado = new ListaEnlazada<String>();
        if(line.length() <= 0)
            return resultado;
        else if(line.charAt(0) != '"'){
            resultado.add(line);
            return resultado;
        }
        else{
            String line_sin_commillas = line.substring(1,line.length()-2);

            String[] string_arr = line_sin_commillas.split("'");
            for(int i = 0; i < string_arr.length; i++)
                resultado.add(string_arr[i]);
            return resultado;
        }
    }

    public static Integer parseIntNullEnabled(String s){
        // https://javarevisited.blogspot.com/2014/12/9-things-about-null-in-java.html#axzz6wvZ1ouSu
        if(s == null)
            return null;

        int l = s.length();
        if(s == "")
            return null;
        else if (l>=3)
            if (s.substring(l-2,l).equals(".0"))
                return Integer.parseInt(s.substring(0,l-2));
            else
                return Integer.parseInt(s);
        else
            return Integer.parseInt(s);
    }

}
