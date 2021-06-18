package Main;
import Exceptions.IlegalIndexException;

import java.util.Scanner;

public class Main {


    boolean running = false;
    Scanner scanner;
    MovieDataBase db;

    int menu_current_location;

    public static void main(String[] args) throws Exception{

        Main main_instance = new Main();
        main_instance.Main_loop(); // Ojo con el 2.6, puede estar mal

    }

    public Main(){
        running = true;
        scanner = new Scanner(System.in);
        db = new MovieDataBase();
        menu_current_location = 0;
    }

    public void Main_loop(){


        while(running){
            if(menu_current_location == 0) {
                menu0();
            }
            if(menu_current_location == 1) {
                menu1();
            }
            if(menu_current_location == 2) {
                menu2();
            }
            if(menu_current_location == 3) {
                running = false;
            }

        }

    }

    private void menu2() {
        System.out.println("1. Indicar el Top 5 de actores/actrices que más apariciones han tenido a lo largo de los años.");
        System.out.println("2. Indicar el Top 5 de las causas de muerte más frecuentes en directores y productores nacidos en Italia, Estados Unidos, Francia y UK.");
        System.out.println("3. Mostrar de las 14 películas con más weightedAverage, el promedio de altura de sus actores/actrices si su valor es distinto de nulo.");
        System.out.println("4. Indicar el año más habitual en el que nacen los actores y las actrices.");
        System.out.println("5. Indicar el Top 10 de géneros de películas más populares, en las cuales al menos un actor/actriz tiene 2 o más hijos.");
        System.out.println("6. Salir.");

        String error_msg = "Debes insertar un numero entero positivos entre 1 y 6 (inclusivos)";

        String input_string = scanner.nextLine();
        Integer input_number;
        try{
            input_number = Integer.parseInt(input_string);
            if(input_number >= 1 && input_number <= 6){
                if(input_number<6) {
                    try {
                        db.Query(input_number);
                    }
                    catch (IlegalIndexException e){
                        System.out.println("Hubo un error haciendo la Query " + input_number);
                    }
                }
                else
                    menu_current_location = 0; //????????running = false;
            }
            else {
                System.out.println(error_msg);
            }

        }
        catch(NumberFormatException e){
            System.out.println(error_msg);
        }
    }

    private void menu1() {
        db.load();

        menu_current_location = 0;
    }

    public void menu0(){
        System.out.println("Seleccione la opción que desee:");
        System.out.println("    1. Carga de datos");
        System.out.println("    2. Ejecutar consultas");
        System.out.println("    3. Salir");

        String error_msg = "Debes insertar un numero entero positivos entre 1 y 3 (inclusivos)";

        String input_string = scanner.nextLine();
        Integer input_number;
        try{
            input_number = Integer.parseInt(input_string);
            if(input_number >= 1 && input_number <= 3){
                menu_current_location = input_number;
            }
            else {
                System.out.println(error_msg);
            }

        }
        catch(NumberFormatException e){
            System.out.println(error_msg);
        }
    }

}
