package com.bank.api;
import java.util.*;
public class Repl {
    private final Scanner sc = new Scanner(System.in); 



    // FIRST WE ASK WHAT THE USER WANTS TO DO 
     public void run() {
        while (true) {
            System.out.println("Pick a number:");
            System.out.println("1.Register");
            System.out.println("2.LOGIN");
            System.out.println("3.exit");
            System.out.print("> ");
            String command = sc.nextLine().trim();

            if (command.equals("3")) {
                return;
            }

            try {
                handle(command);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    

    //THIS HANDLES THE USER PICK
    private void handle(String command){
        switch (command) {
            // IF user chooses register make them create new id and pin
            case "1": 
                System.out.println();
                //register(id, pin);
             
                
            //IF user asks login ask for username and password
            //case "2" -> LOGIN();
        }
    }
    
    
}
