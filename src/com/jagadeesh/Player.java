package com.jagadeesh;

import java.util.Scanner;

public class Player extends Person {

    Scanner input = new Scanner(System.in);

    public Player() {
        super.setName("Player");
    }

    public Player(String name) {
        super.setName(name);
    }

    public void makeDecision(Deck deck, Deck discard) {
        String decision = "";
        boolean getNum = true;

        while (getNum) {
            try {
                super.printHand();
                System.out.print(". Hit or Stand? > ");
                decision = input.next();
                if ("hit".equalsIgnoreCase(decision) || "stand".equalsIgnoreCase(decision)) {
                    getNum = false;
                } else {
                    System.out.println("Invalid input.  Enter either Hit or Stand > ");
                }
            } catch (Exception e) {
                System.out.println("Invalid");
                input.next();
            }
        }

        if ("hit".equalsIgnoreCase(decision)) {
            this.hit(deck, discard);
            if (this.getHand().calculatedValue() > 20) {
                if (this.getHand().calculatedValue() <= 21) {
                    super.printHand();
                    System.out.println(". Hit or Stand? > Stand (score: " + this.getHand().calculatedValue() + ")");
                }
                return;
            } else {
                this.makeDecision(deck, discard);
            }

        }
    }
}
