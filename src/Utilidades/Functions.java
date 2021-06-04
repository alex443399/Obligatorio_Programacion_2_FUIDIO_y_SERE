package Utilidades;

import TADS.ArrayList;

public class Functions {

    public static ArrayList<String> StringArrayFromCsvLine(String line, char del){
        int L = line.length();
        System.out.println(L);
        boolean inside = false;
        ArrayList<String> resultado = new ArrayList<String>();
        int LeftIndex = 0, RightIndex = 0;

        while(LeftIndex < L) {
            System.out.println(RightIndex);

            boolean condition_while_loop = RightIndex < L;
            // la condicion se tiene que evaluar en partes pq si evaluamos line.charAt sin saber si el indice es < L puede Out Of Bounds Exceptions
            if(condition_while_loop)
                condition_while_loop = (condition_while_loop) && (line.charAt(RightIndex) != del);

            while (condition_while_loop) {
                RightIndex++;

                condition_while_loop = RightIndex < L;
                if(condition_while_loop)
                    condition_while_loop = (condition_while_loop) && (line.charAt(RightIndex) != del);
            }
            String temp = line.substring(LeftIndex, RightIndex);
            resultado.add(temp);
            RightIndex++;
            LeftIndex = RightIndex;
        }
        return resultado;
    }


}
