package com.jagadeesh;

public class Game {

    private Deck deck, discarded;

    private Dealer dealer;
    private Player[] player;
    private int number_of_players;

    public Game(int number_of_players) {
        this.number_of_players = number_of_players;
        deck = new Deck(true);
        discarded = new Deck();

        dealer = new Dealer();

        player = new Player[number_of_players];
        for (int i = 0; i < this.number_of_players; i++) {
            player[i] = new Player("Player" + (i+1));
        }

        deck.shuffle();
        startRound();
    }

    private void startRound() {

        if (deck.cardsLeft() < 4) {
            deck.reloadDeckFromDiscard(discarded);
        }

        //Give the player and Dealer one cards
        for (int i = 0; i < number_of_players; i++) {
            player[i].getHand().takeCardFromDeck(deck);
        }
        dealer.getHand().takeCardFromDeck(deck);

        //Show the player's hand
        for (int i = 0; i < number_of_players; i++) {
            player[i].printHand();
            System.out.print("\n");
        }

        //Show the dealers hand with one card face down
        dealer.printFirstHand();
        System.out.print("\n");

        //Give the player and Dealer second cards
        for (int i = 0; i < number_of_players; i++) {
            player[i].getHand().takeCardFromDeck(deck);
        }
        dealer.getHand().takeCardFromDeck(deck);


        if (dealer.hasBlackjack()) {  //Check if dealer has BlackJack to start
            dealer.printHand();
            System.out.println(". Dealer has BlackJack. All players lose.");
        } else {
            //Let the player decide what to do next
            for (int i = 0; i < number_of_players; i++) {
                player[i].makeDecision(deck, discarded);

                if (player[i].getHand().calculatedValue() > 21) {
                    player[i].printHand();
                    System.out.println(". Busted over 21.");
                }
            }

            //Dealer turn
            while (dealer.getHand().calculatedValue() < 17) {
                dealer.printHand();
                System.out.println(". Dealer hits.");
                dealer.hit(deck, discarded);
            }

            if (dealer.getHand().calculatedValue() > 21) {
                dealer.printHand();
                System.out.print("\n");
                for (int i = 0; i < number_of_players; i++) {
                    if (player[i].getHand().calculatedValue() <= 21) {
                        System.out.println("Scoring " + player[i].getName() + " has " + player[i].getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + player[i].getName() + " wins.");
                    } else {
                        System.out.println("Scoring " + player[i].getName() + " has " + player[i].getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + dealer.getName() + " wins.");
                    }
                }
            } else {
                dealer.printHand();
                System.out.println(". Dealer stands.");
                for (int i = 0; i < number_of_players; i++) {
                    if (dealer.getHand().calculatedValue() > player[i].getHand().calculatedValue()) {
                        System.out.println("Scoring " + player[i].getName() + " has " + player[i].getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + dealer.getName() + " wins.");
                    } else if (player[i].getHand().calculatedValue() > dealer.getHand().calculatedValue()) {
                        if (player[i].getHand().calculatedValue() > 21) {
                            System.out.println("Scoring " + player[i].getName() + " busted. " + dealer.getName() + " wins.");
                        } else {
                            System.out.println("Scoring " + player[i].getName() + " has " + player[i].getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + player[i].getName() + " wins.");
                        }
                    } else {
                        System.out.println("Scoring " + player[i].getName() + " has " + player[i].getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". Scores Tie.  And " + dealer.getName() + " wins.");
                    }
                }
            }
        }
    }

    public static void pause() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
