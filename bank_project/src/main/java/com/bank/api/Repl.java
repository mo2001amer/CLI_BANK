package com.bank.api;

import java.util.List;
import java.util.Scanner;

import com.bank.business.CustomerService;
import com.bank.domain.Customer;
import com.bank.domain.Transaction;

public class Repl {

    private final Scanner sc = new Scanner(System.in);

    private final CustomerService customerService;


    public Repl(CustomerService customerService) {
                this.customerService =customerService;
    }


    // =========================
    // MAIN MENU
    // =========================
    public void run() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========== BANK OF CLI =========="
            );

            System.out.println();

            System.out.println("1. Register" );

            System.out.println("2. Login");

            System.out.println("3. Exit" );

            System.out.println();

            System.out.print("Choose an option: " );


            String command =
                    sc.nextLine()
                            .trim();


            if (command.equals("3")) {

                System.out.println();

                System.out.println("Thank you for using Bank of CLI!" );

                return;
            }


            try {

                handle(command);

            } catch (
                    IllegalArgumentException e) {

                System.out.println("Error: "+ e.getMessage());

            } catch (
                    IllegalStateException e) {

                System.out.println( "Service temporarily unavailable. "+ "Please try again later.");
            }
        }
    }


    // =========================
    // HANDLE MAIN MENU
    // =========================
    private void handle(
            String command) {

        switch (command) {

            case "1":
                register();
                break;

            case "2":
                login();
                break;

            default:

                System.out.println("Invalid option." );

                break;
        }
    }


    // =========================
    // REGISTER
    // =========================
    private void register() {

        try {

            System.out.println();

            System.out.println(
                    "========== REGISTER =========="
            );

            System.out.println();

            System.out.print("Enter a 4-digit PIN: ");


            int pin =Integer.parseInt(sc.nextLine() .trim());


            Customer customer = new Customer(pin, 0 );


            int accountId = customerService.register(customer);


            System.out.println();

            System.out.println("Registration successful!");

            System.out.println("Your Account ID is: " + accountId);

            System.out.println( "Please save your Account ID.");


        } catch (NumberFormatException e) {

            System.out.println("PIN must be a number.");

        } catch (IllegalArgumentException e) {

            System.out.println( e.getMessage());
        }
    }


    // =========================
    // LOGIN
    // =========================
    private void login() {

        try {

            System.out.println();

            System.out.println( "========== LOGIN ==========" );

            System.out.println();

            System.out.print("Enter Account ID: ");


            int accountId =Integer.parseInt( sc.nextLine().trim());


            System.out.print("Enter PIN: ");


            int pin =Integer.parseInt(sc.nextLine().trim());


            boolean loginSuccessful =customerService.login(accountId,pin);


            if (loginSuccessful) {

                System.out.println();

                System.out.println("Login successful!");


                accountMenu(accountId);

            } else {

                System.out.println();

                System.out.println("Invalid Account ID or PIN.");
            }


        } catch (NumberFormatException e) {

            System.out.println("Account ID and PIN "+ "must be numbers.");
        }
    }


    // =========================
    // ACCOUNT MENU
    // =========================
    private void accountMenu(
            int accountId) {

        while (true) {

            System.out.println();

            System.out.println("========== MY ACCOUNT ==========");

            System.out.println();

            System.out.println("Account ID: "+ accountId);

            System.out.println();

            System.out.println("1. Check Balance");

            System.out.println("2. Deposit");

            System.out.println("3. Withdraw");

            System.out.println("4. Transfer");

            System.out.println("5. Transaction History");

            System.out.println("6. Logout" );

            System.out.println();

            System.out.print("Choose an option: " );


            String command =sc.nextLine().trim();


            if (command.equals("6")) {

                System.out.println();

                System.out.println( "Logged out successfully.");

                return;
            }


            try {
                 handleAccountCommand(command,accountId);

            } catch (IllegalArgumentException e) {

                System.out.println("Error: "+ e.getMessage()
                );

            } catch (IllegalStateException e) {

                System.out.println("Service temporarily unavailable. " + "Please try again later.");
            }
        }
    }


    // =========================
    // HANDLE ACCOUNT MENU
    // =========================
    private void handleAccountCommand(
            String command,
            int accountId) {

        switch (command) {

            case "1":

                checkBalance(accountId);

                break;


            case "2":

                deposit(accountId);

                break;


            case "3":

                withdraw( accountId );

                break;


            case "4":

                transfer( accountId );

                break;


            case "5":

                transactionHistory( accountId);

                break;


            default:

                System.out.println("Invalid option." );

                break;
        }
    }


    // =========================
    // CHECK BALANCE
    // =========================
    private void checkBalance(int accountId) {

        double balance =customerService.getBalance( accountId);


        System.out.println();

        System.out.printf("Current Balance: $%.2f%n",balance);
    }


    // =========================
    // DEPOSIT
    // =========================
    private void deposit(
            int accountId) {

        try {

            System.out.println();

            System.out.println( "========== DEPOSIT ==========" );

            System.out.println();

            System.out.print( "Enter amount: $");


            double amount =Double.parseDouble(sc.nextLine().trim() );


            customerService.deposit(accountId,amount);


            System.out.println();

            System.out.printf("Successfully deposited $%.2f%n",amount);


            double balance = customerService.getBalance( accountId);


            System.out.printf("New Balance: $%.2f%n", balance);


        } catch (NumberFormatException e) {

            System.out.println("Amount must be a number.");
        }
    }


    // =========================
    // WITHDRAW
    // =========================
    private void withdraw(int accountId) {

        try {

            System.out.println();

            System.out.println("========== WITHDRAW ==========" );

            System.out.println();

            System.out.print("Enter amount: $"
            );
             double amount =Double.parseDouble(sc.nextLine().trim());


            customerService.withdraw(accountId,amount );


            System.out.println();

            System.out.printf("Successfully withdrew $%.2f%n",amount );


            double balance =customerService.getBalance(accountId);


            System.out.printf("New Balance: $%.2f%n",balance);


        } catch (NumberFormatException e) {

            System.out.println("Amount must be a number.");
        }
    }


    // =========================
    // TRANSFER
    // =========================
    private void transfer(int accountId) {

        try {

            System.out.println();

            System.out.println("========== TRANSFER ==========");

            System.out.println();

            System.out.print("Enter receiving Account ID: ");


            int receivingAccountId =Integer.parseInt(sc.nextLine().trim());


            System.out.print("Enter amount: $");


            double amount =Double.parseDouble(sc.nextLine().trim() );


            customerService.transfer(accountId,receivingAccountId,amount   );


            System.out.println();

            System.out.printf("Successfully transferred $%.2f%n",amount );


            double balance = customerService.getBalance( accountId );


            System.out.printf("New Balance: $%.2f%n",balance );


        } catch (NumberFormatException e) {

            System.out.println("Account ID and amount "+ "must be numbers.");
        }
    }


    // =========================
    // TRANSACTION HISTORY
    // =========================
    private void transactionHistory(int accountId) {

        List<Transaction> transactions = customerService.getTransactionHistory(  accountId );


        System.out.println();

        System.out.println(  "======= TRANSACTION HISTORY ======="  );

        System.out.println();


        if (transactions.isEmpty()) {

            System.out.println( "No transactions found.");

            return;
        }


        for (
            Transaction transaction: transactions
        ) {

            System.out.println( "Type: "+ transaction .getTransactionType());


            System.out.printf("Amount: $%.2f%n", transaction.getAmount());


            if (transaction.getFromAccountId()!= null  ) {

                System.out.println( "From Account: "+ transaction.getFromAccountId());
            }


            if ( transaction.getToAccountId()!= null
            ) {

                System.out.println("To Account: " + transaction.getToAccountId() );
            }


            System.out.println( "Date: " + transaction.getCreatedAt());


            System.out.println("------------------------------");
        }
    }
}