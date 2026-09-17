package com.bank.api;
import java.util.*;

import com.bank.domain.Customer;
import com.bank.business.CustomerService;
public class Repl {
    private final Scanner sc = new Scanner(System.in); 
    private final CustomerService customerService;
    

    public Repl(CustomerService customerService)
    {
        this.customerService = customerService;


    }




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
            // CASE 1 : registering user
            case "1": 
                System.out.println("Please enter accountID: ");
                try {
                    System.out.println("Please enter account ID:");
                    String input = sc.nextLine().trim();

                    int accountId = Integer.parseInt(input);
                    
                    
                    System.out.println("Please enter PIN:");
                    input = sc.nextLine().trim();

                    int pin = Integer.parseInt(input);
                    Customer customer = new Customer(accountId, pin, 0);
                    customerService.register(customer);

                    } 
                catch (NumberFormatException e) {
                    System.out.println("Account ID must be a number.");
                }
                catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                }
             
                
            //CASE 2 : LOGIN
            //case "2" -> LOGIN();
        }
    }
    
    
}
