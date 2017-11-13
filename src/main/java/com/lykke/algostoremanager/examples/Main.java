package com.lykke.algostoremanager.examples;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Prints "Hello, World" to the terminal window.
        int counter = 0;
        while(true) {
            System.out.println("Hello, World "+ counter);
            Thread.sleep(10000);
            counter++;
        }
    }

}