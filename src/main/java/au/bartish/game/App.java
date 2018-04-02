package au.bartish.game;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println("Welcome to the Dark of Darkness!\n");
        System.out.println("You are in the legend. You see a beautiful village with monsters. There is a " +
                "dark mansion in front of you.\n");

        while (true) {
            final Scanner keyboard = new Scanner(System.in);
            System.out.println("Do you go inside?");
            final String response = keyboard.next();

            if (response.equalsIgnoreCase("yes")) {
                System.out.println("You go inside.");
                System.exit(0);
            } else if (response.equalsIgnoreCase("no")) {
                System.out.println("You walk away and get eaten by a monster in the villiage.");
                System.exit(0);
            } else {
                System.out.println("You stand there thinking about if you want to go in.");
            }
        }
    }

}
