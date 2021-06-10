package Utilidades;

import Exceptions.InvalidDateFormatException;
import TADS.ArrayList;
import TADS.ListaEnlazada;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;


public class Functions {

    public static ZoneId defaultZoneId = ZoneId.systemDefault();

    public static String[] StringArrayFromCsvLine(String line, char del, char ignorar, int cantidad_de_columnas){
        int L = line.length();
        boolean inside = false;

        String[] resultado = new String[cantidad_de_columnas];

        int LeftIndex = 0, RightIndex = 0, current_column = 0;

        while(LeftIndex < L) {
            boolean condition_while_loop = RightIndex < L;
            if(condition_while_loop)
                condition_while_loop = (line.charAt(RightIndex) != del);
            // la condicion se tiene que evaluar en partes pq si evaluamos line.charAt sin saber si el indice es < L puede Out Of Bounds Exceptions

            while (condition_while_loop) {
                if(line.charAt(RightIndex) == ignorar)
                    inside = !inside;
                RightIndex++;
                /////////


                /////////
                condition_while_loop = RightIndex < L;
                if(condition_while_loop)
                    condition_while_loop =
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

    public static String[] StringArrayFromCsvLine(String line, char del, char ignorar){
        int L = line.length();
        boolean inside = false;

        ArrayList<String> preresultado = new ArrayList<String>();


        int LeftIndex = 0, RightIndex = 0, current_column = 0;

        while(LeftIndex < L) {
            boolean condition_while_loop = RightIndex < L;
            if(condition_while_loop)
                condition_while_loop = (line.charAt(RightIndex) != del);
            // la condicion se tiene que evaluar en partes pq si evaluamos line.charAt sin saber si el indice es < L puede Out Of Bounds Exceptions

            while (condition_while_loop) {
                if(line.charAt(RightIndex) == ignorar)
                    inside = !inside;
                RightIndex++;
                /////////


                /////////
                condition_while_loop = RightIndex < L;
                if(condition_while_loop)
                    condition_while_loop = ((line.charAt(RightIndex) != del) || inside);

            }

            String temp = line.substring(LeftIndex, RightIndex);

            preresultado.add(temp);

            RightIndex++;
            LeftIndex = RightIndex;
        }
        String[] resultado = preresultado.toStringArray();
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

    public static Integer parseYear(String s){
        int l = s.length();
        String clipped = s.substring(l-4,l);
        return Integer.parseInt(clipped);
    }

    public static Date DateFromRegisterString(String s_date) throws InvalidDateFormatException {
        try {
            LocalDate ld = LocalDate.parse(s_date);
            return DateFromLocalDate(ld);

        } catch (DateTimeParseException date_ex) {
            int string_length = s_date.length();
            String possible_year_substring = s_date.substring(string_length-4,string_length);

            try {
                int possible_year = Integer.parseInt(possible_year_substring);
                LocalDate ld = LocalDate.of(possible_year,1,1);
                return DateFromLocalDate(ld);

            }
            catch(NumberFormatException number_ex){
                throw new InvalidDateFormatException("Inputted date: -" + s_date + "- has invalid format");
            }
        }
    }

    public static Date DateFromLocalDate(LocalDate ld){//https://beginnersbook.com/2017/10/java-convert-localdate-to-date/
        Date date = Date.from(ld.atStartOfDay(defaultZoneId).toInstant());
        return date;
    }

}
