package com.jagadeesh;

public class Dealer extends Person{

    public Dealer(){
        super.setName("computer");
    }

    public void printFirstHand(){
        super.getHand().getCard(0);
        System.out.print("Dealing to computer, card:  face down");
    }
}
