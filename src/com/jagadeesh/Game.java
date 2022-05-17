package com.jagadeesh;

public class Game {

    private Deck deck, discarded;

    private Dealer dealer;
    private Player player;

    public Game() {
        deck = new Deck(true);
        discarded = new Deck();

        dealer = new Dealer();
        player = new Player();

        deck.shuffle();
        startRound();
    }

    private void startRound() {

        if (deck.cardsLeft() < 4) {
            deck.reloadDeckFromDiscard(discarded);
        }

        //Give the player and Dealer one cards
        player.getHand().takeCardFromDeck(deck);
        dealer.getHand().takeCardFromDeck(deck);

        //Show the player's hand
        player.printHand();
        System.out.print("\n");

        //Show the dealers hand with one card face down
        dealer.printFirstHand();
        System.out.print("\n");

        //Give the player and Dealer second cards
        player.getHand().takeCardFromDeck(deck);
        dealer.getHand().takeCardFromDeck(deck);

        //Check if player has blackjack to start
        if (player.hasBlackjack()) {
            System.out.println("You have Blackjack! You win!");
        } else if (dealer.hasBlackjack()) {  //Check if dealer has BlackJack to start
            dealer.printHand();

            //Check if the player also has BlackJack
            if (player.hasBlackjack()) {
                player.printHand();
                System.out.println(". You both have 21 - Push.");
            } else {
                System.out.println(". Dealer has BlackJack. You lose.");
            }
        } else {
            //Let the player decide what to do next
            player.makeDecision(deck, discarded);

            if (player.getHand().calculatedValue() > 21) {
                player.printHand();
                System.out.println(". Busted over 21.");
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
                if (player.getHand().calculatedValue() > dealer.getHand().calculatedValue()) {
                    System.out.println("Scoring " + player.getName() + " has " + player.getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + player.getName() + " wins.");
                } else {
                    System.out.println("Scoring " + player.getName() + " has " + player.getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + dealer.getName() + " wins.");
                }
            } else if (dealer.getHand().calculatedValue() > player.getHand().calculatedValue()) {
                dealer.printHand();
                System.out.println(". Dealer stands.");
                System.out.println("Scoring " + player.getName() + " has " + player.getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + dealer.getName() + " wins.");
            } else if (player.getHand().calculatedValue() > dealer.getHand().calculatedValue()) {
                dealer.printHand();
                System.out.println(". Dealer stands.");
                if (player.getHand().calculatedValue() > 21) {
                    System.out.println("Scoring " + player.getName() + " busted. " + dealer.getName() + " wins.");
                } else {
                    System.out.println("Scoring " + player.getName() + " has " + player.getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". " + player.getName() + " wins.");
                }
            } else {
                dealer.printHand();
                System.out.println(". Dealer stands.");
                System.out.println("Scoring " + player.getName() + " has " + player.getHand().calculatedValue() + ", " + dealer.getName() + " has " + dealer.getHand().calculatedValue() + ". Scores Tie.  And " + dealer.getName() + " wins.");
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
