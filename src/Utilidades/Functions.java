package Utilidades;

import Exceptions.InvalidDateFormatException;
import Modelo.CauseOfDeath;
import TADS.ArrayList;
import TADS.ListaEnlazada;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedList;


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
        s_date = trimChar(s_date,'"');
        try {
            LocalDate ld = LocalDate.parse(s_date);
            return DateFromLocalDate(ld);

        } catch (DateTimeParseException date_ex) {
            int string_length = s_date.length();
            if(string_length >= 4) {
                String possible_year_substring = s_date.substring(string_length - 4, string_length);

                try {
                    int possible_year = Integer.parseInt(possible_year_substring);
                    LocalDate ld = LocalDate.of(possible_year, 1, 1);
                    return DateFromLocalDate(ld);

                } catch (NumberFormatException number_ex) {

                    try {
                        possible_year_substring = s_date.substring(0,4);
                        int possible_year = Integer.parseInt(possible_year_substring);
                        LocalDate ld = LocalDate.of(possible_year, 1, 1);
                        return DateFromLocalDate(ld);

                    } catch (NumberFormatException number_ex_2) {
                        //System.out.println(s_date);
                        return null;
                        //throw new InvalidDateFormatException("Inputted date: -" + s_date + "- has invalid format");
                    }
                }
            }
            else return null;
        }
    }

    public static Date DateFromLocalDate(LocalDate ld){//https://beginnersbook.com/2017/10/java-convert-localdate-to-date/
        Date date = Date.from(ld.atStartOfDay(defaultZoneId).toInstant());
        return date;
    }

    public static String[] ParametersFromPlaceString(String place){
        if(place.length() > 0) {
            if (place.charAt(0) == '"')
                place = place.substring(1);

            if (place.charAt(place.length() - 1) == '"')
                place = place.substring(0, place.length() - 1);

            String[] arrayFromReg = StringArrayFromCsvLine(place, ',', '"');
            String[] answer = new String[3];
            int L = arrayFromReg.length;
            if (L >= 3) {
                answer[2] = trimSpaces(arrayFromReg[L - 1]);
                answer[1] = trimSpaces(arrayFromReg[L - 2]);
                answer[0] = trimSpaces(arrayFromReg[L - 3]);

            } else if (L == 2) {
                answer[2] = trimSpaces(arrayFromReg[1]);
                answer[1] = null;
                answer[0] = trimSpaces(arrayFromReg[0]);
            } else if (L == 1) {
                answer[2] = trimSpaces(arrayFromReg[0]);
                answer[1] = null;
                answer[0] = null;
            } else {
                answer[2] = null;
                answer[1] = null;
                answer[0] = null;
            }

            return answer;
        }
        else return null;

    }

    public static ArrayList<CauseOfDeath> CausasDeMuerte(String s_cause_of_death){

        if(s_cause_of_death == null)
            return new ArrayList<CauseOfDeath>();
        if(s_cause_of_death.length()<=0)
            return new ArrayList<CauseOfDeath>();

        while (s_cause_of_death.charAt(0) == '"' && s_cause_of_death.charAt(s_cause_of_death.length()-1) == '"')
            s_cause_of_death = s_cause_of_death.substring(1,s_cause_of_death.length()-1);

        if(s_cause_of_death.length()<=0)
            return new ArrayList<CauseOfDeath>();

        int index1 = 0;
        int index2 = 0;
        int L = s_cause_of_death.length();

        ArrayList<CauseOfDeath> resultado = new ArrayList();

        while(index1 < L) {

            boolean break_now_condition = false;
            boolean comma_flag = false;
            boolean and_flag = false;

            while (!break_now_condition) {
                index2++;
                break_now_condition = false;
                if (index2 < L)
                    comma_flag = (s_cause_of_death.charAt(index2) == ',');
                if (index2 + 5 < L)
                    and_flag = (s_cause_of_death.substring(index2, index2 + 5).equalsIgnoreCase(" and "));//que pasa si tiene algo antes???

                break_now_condition = comma_flag || and_flag;
                if(index2 >= L)
                    break_now_condition = true;
            }
            ////
            String sumando = s_cause_of_death.substring(index1, index2);
            //// trimming
            sumando = trimSpaces(sumando);

            resultado.add(new CauseOfDeath(sumando));
            ////
            if(comma_flag){
                index1 = index2+1; // Capaz deberia ser +2, porque despues de cada, hay un espacio
                index2 = index1;
            }
            else if(and_flag){
                index1 = index2+5;
                index2 = index1;
            }
            else{
                index1 = index2;
                index2 = index1;
            }

        }
        return resultado;
    }

    public static String trimSpaces(String s){
        return trimChar(s,' ');
    }

    public static String trimChar(String s, char c){
        while(s.charAt(0) == c)
            s = s.substring(1);
        while(s.charAt(s.length()-1) == c)
            s = s.substring(0, s.length()-1);

        return s;
    }

    public static boolean multiContains(String analizada, String[] array_of_strings){
        int L = array_of_strings.length;
        for(int i = 0; i < L; i++){
            if(analizada.contains(array_of_strings[i]))
                return true;
        }
        return false;
    }

}
