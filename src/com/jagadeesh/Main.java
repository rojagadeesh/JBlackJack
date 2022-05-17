package com.jagadeesh;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of players");
        }
        int number_of_players = 0;
        try {
            number_of_players = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid argument.  Setting default number of players to ONE");
            number_of_players = 1;
        }

        System.out.println("Starting game with 1 player");
        new Game();

    }
}
