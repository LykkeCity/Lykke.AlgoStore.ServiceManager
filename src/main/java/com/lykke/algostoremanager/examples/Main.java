package com.lykke.algostoremanager.examples;

import com.lykke.algostoremanager.impl.ContainerStatusMatcher;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Prints "Hello, World" to the terminal window.
        int counter = 0;

        try {
            System.out.println("Status: " + ContainerStatusMatcher.statusMatcher("test 12435"));

        }catch (RuntimeException rte){

        }
        while(true) {
            System.out.println("Hello, World "+ counter);
            Thread.sleep(10000);
            counter++;
        }
    }

}