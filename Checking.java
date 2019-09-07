/*Name: William Basquez Salas
Alias: 'Scott's Tot'
Date: [9/7/2019]
Course: Adv. Obj.-Oriented Program.
Instructor: Dr. Daniel Mejia
Lab Assignment: Lab 1: Bank
Lab Description: To create a simple bank that can handle transaction between 2 people, or a single person. It delivers an updated text file
and a new text file with the total transactions made by the customer(s).
Honesty Statement: "I confirm that the work of this assignment is completely my own. By turning in this assignment, I declare that I did not receive unauthorized assistance.
Moreover, all deliverates including, but not limited to the source code, lab report and output files were written and produced by me alone"*/
import java.io.*;
import java.util.*;
class Checking{
    public static void main(String[] args){
        try{
            int numberOfPeople, transactionChoice;
            float deposit, withdrawn;
                String fileName = "clients.txt";
            List<Client> myClients = clientBuilder(fileName);
            //The list of transactions is set to 20, a small number of transactions possible
            List<String> transactionList = new ArrayList<String>(20);
                Scanner scnr = new Scanner(System.in);
            System.out.println("Welcome to William's Bank!\nPlease enter user's account number: ");
                int userAccountNumber = scnr.nextInt();
            while(true){
                /*The method 'findClient' assumes that the user inputs an existing account number
                Otherwise, the program ends.*/
                Client user = findClient(myClients, userAccountNumber);
                System.out.print("\n1) Single Person Transaction\n2) 2 People transaction\n3) Exit\nOption: ");
                    numberOfPeople = scnr.nextInt();
                if(numberOfPeople == 1){
                    while(true){
                        System.out.print("\nWhat kind of transaction would you like to perform?\n1) Deposit\n2) Withdraw\n3) Inquiry Balance\n4) Exit\nOption: ");
                            transactionChoice = scnr.nextInt();
                        if(transactionChoice == 1){
                            System.out.print("\nHow much money would you like to deposit: ");
                                deposit = scnr.nextFloat();
                            depositMoney(user, deposit, transactionList);
                        }                        
                        else if(transactionChoice == 2){
                            System.out.print("\nHow much money would you like to withdraw: ");
                                withdrawn = scnr.nextFloat();
                            withdrawMoney(user, withdrawn, transactionList);
                        }
                        else if(transactionChoice == 3){
                            inquiryAccount(user);
                        }
                        else if(transactionChoice == 4)
                            break;
                        else
                            System.out.println("\nCannot Proceed:\n\tInvalid Transaction choice");
                    }
                }
                else if(numberOfPeople == 2){
                    while(true){
                        System.out.print("\nWhat kind of transaction would you like to perform?\n1) Pay someone\n2) Exit\nOption: ");
                            transactionChoice = scnr.nextInt();
                        if(transactionChoice == 1){
                            System.out.print("\nEnter the account number of the receiving account: ");
                                int receivingUserNumber = scnr.nextInt();
                            //'findClient' assumes that the user inputs a valid account number
                            Client receivingUser = findClient(myClients, receivingUserNumber);
                            System.out.print("Enter the amount you want to pay: ");
                                float payment = scnr.nextFloat();
                            payPerson(user, receivingUser, payment, transactionList);
                        }
                        else if(transactionChoice == 2)
                            break;
                        else
                            System.out.println("\nCannot Proceed:\n\tInvalid Transaction choice");
                    }
                }
                else if(numberOfPeople == 3)
                    break;
                else
                    System.out.println("\nCannot Proceed:\n\tInvalid Transaction choice");    
            }
            scnr.close();
            transactionListFile(transactionList);
            //Change this to the actual name of the file to be updated
            updateFile(myClients, "updated-file.txt");
		} catch (FileNotFoundException a){
			System.out.println("File not found.");
		} catch (InputMismatchException b){
            System.out.println("Wrong User Input");
        } catch (NullPointerException d){
            System.out.println("User not found");
        } catch (IOException e){
            System.out.println("Error writing transactions file");
        }
    }

    //This method calculates the number of rows in a file, which will later be used to determine the number of clients
    public static int rowCount(String filename){
        int rows = 0;
        File infile = new File(filename);
        try{
            Scanner scan1 = new Scanner(infile);

            while(scan1.hasNextLine()){
                scan1.nextLine();
                rows++;
            }
            scan1.close();
        } catch(Exception b){
            System.out.println("Could not calculate number of rows");
        }
        return rows;
    }

    /*This method takes a filenam as parameter, and reads the file, store the info in a 2D array
    and then creates a List of type 'Client' to store all clients*/
    public static List<Client> clientBuilder(String filename)throws FileNotFoundException{
        String[][] customers = new String[rowCount(filename)][];
        List<Client> newList = new ArrayList<Client>(customers.length);
        int i = 0;
        Scanner reader = new Scanner(new File(filename));

        while(reader.hasNextLine()){
            String lines = reader.nextLine();
            String[] temp = lines.split("\t");
            customers[i] = new String[temp.length];

            for(int j = 0; j < temp.length; j++)
                customers[i][j] = temp[j].toString();
            i++;
        }
        reader.close();

        for(int j = 1; j < customers.length; j++){
            Client newClient = new Client(customers[j][0], customers[j][1], Integer.parseInt(customers[j][2]), Boolean.parseBoolean(customers[j][3]), Boolean.parseBoolean(customers[j][4]), Float.parseFloat(customers[j][5]), Float.parseFloat(customers[j][6].substring(0, 3)));
            newList.add(newClient);
		}
        return newList;
    }

    //This method searches for a client in the List of clients, returns null if a user is not found
    public static Client findClient(List<Client> clients, int clientNumber){
        for(Client c: clients){
            if(c.getAccountNumber() == clientNumber)
                return c;
        }
        return null;
    }

    //This method returns all the info of a specific client, as well as a list of all the transactions made
    public static void inquiryAccount(Client myClient){
        myClient.printInfo();
        System.out.println("\tDate\t\t\tAccount #\tAmount\tFormer Balance");
        for(String s: myClient.getTransactions())
            System.out.println(s);
    }

    /*This method takes in 2 clients, the amount to be paid, and the list of transactions
    client 1 pays $X to client 2, and updates both clients' transaction list
    as well as the bank's total transactions*/
    public static void payPerson(Client client1, Client client2, float amountToPay, List<String> totalTransactions){
        if(client1.getAccountBalance() < amountToPay)
            System.out.println("Cannot proceed:\n\tBalance is not enough\n");
        else{
            float client1Balance = client1.getAccountBalance(), client2Balance = client2.getAccountBalance();

            client1.setAccountBalance(client1Balance-=amountToPay);
            client2.setAccountBalance(client2Balance+=amountToPay);

            Date transactionDate = new Date();

            client1.setTransaction(transactionDate + "\t" + client1.getAccountNumber() + "\t-> $" + amountToPay + "\t$" + client1Balance);
            client2.setTransaction(transactionDate + "\t" + client2.getAccountNumber() + "\t<- $" + amountToPay + "\t$" + client2Balance);
            totalTransactions.add(transactionDate + "\t" + client1.getAccountNumber() + "\t-> $" + amountToPay + "\t$" + client1Balance);
            totalTransactions.add(transactionDate + "\t" + client2.getAccountNumber() + "\t<- $" + amountToPay + "\t$" + client2Balance);
        }
    }

    /*This method takes in a client and the amount wished to be deposit, it updates the bank's transaction list
    as well as the client's transaction list*/
    public static void depositMoney(Client myClient, float newAmountDeposit, List<String> totalTransactions){
        float previousBalance = myClient.getAccountBalance();    
        Date transactionDate = new Date();
        myClient.setAccountBalance(previousBalance + newAmountDeposit);
        myClient.setTransaction(transactionDate + "\t" + myClient.getAccountNumber() + "\t+$" + newAmountDeposit + "\t$" + previousBalance);
        totalTransactions.add(transactionDate + "\t" + myClient.getAccountNumber() + "\t+$" + newAmountDeposit + "\t$" + previousBalance);
    }

    /*This method takes in a client and the amount wished to be withdrawn, it updates the bank's transaction list
    as well as the client's transaction list*/
    public static void withdrawMoney(Client myClient, float amountWithdrew, List<String> totalTransactions){
        float previousBalance = myClient.getAccountBalance();
        Date transactionDate = new Date();
        if(previousBalance < amountWithdrew)
            System.out.println("Cannot Proceed:\n\tBalance is not Enough");
        else{
            myClient.setAccountBalance(previousBalance-amountWithdrew);
            myClient.setTransaction(transactionDate + "\t" + myClient.getAccountNumber() + "\t-$" + amountWithdrew + "\t$" + previousBalance);
            totalTransactions.add(transactionDate + "\t" + myClient.getAccountNumber() + "\t-$" + amountWithdrew + "\t$" + previousBalance);
        }
    }

    /*This method takes in the list of clients, as well as the filename that is to be updated,
    preferably the initial file*/
    public static void updateFile(List<Client> clientList, String fileName)throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("First Name\tLast Name\tAccount Number\tChecking\tSavings\tCheckings Starting Balance\tInterest Rate\n");
        for(Client c: clientList)
            writer.write(c.getFirstName() + "\t" + c.getLastName() + "\t" + c.getAccountNumber() + "\t" + c.getCheckingAccount() + "\t" + c.getSavingsAccount() + "\t" + c.getAccountBalance() + "\t" + c.getInterestRate() + "%\n");
        writer.close();
    }

    //This method updates the bank's transaction list, to be later reported
    public static void transactionListFile(List<String> l) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("transactions-sample.txt"));
        writer.write("\tDate\t\t\tAccount #\tAmount\tFormer Balance\n");
        for(String s: l)
            writer.write(s + "\n");
        writer.close();
    }
}
