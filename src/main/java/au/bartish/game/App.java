package au.bartish.game;

import au.bartish.game.enterence.OutsideEntrance;

import java.util.Scanner;

public class App
{
    public static void main( String[] args ) {
        System.out.println("Welcome to the Dark of Darkness!\n");

        Backpack backpack = new Backpack();
        House house = new House();
        Location currentLocation = house.get("outsideEntrance");
        while (true) {
            System.out.println(currentLocation.getStory());
            System.out.println();
            final Scanner keyboard = new Scanner(System.in);
            System.out.println(currentLocation.getQuestion());
            final String response = keyboard.nextLine();
            currentLocation = currentLocation.doAction(response, backpack);
        }
    }

}
