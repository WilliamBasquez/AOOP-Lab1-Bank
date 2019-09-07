import java.util.*;
class Client{
    private String firstName, lastName;
    private int accountNumber;
    private Boolean checkingAcc, savingsAcc;
    private float interestRate, accountBalance;
    private List<String> transactions = new ArrayList<String>(100);

    public Client(String fName, String lName, int accNum, Boolean checkAcc, Boolean savAcc, float balance, float rate){
        this.firstName = fName;
        this.lastName = lName;
        this.accountNumber = accNum;
        this.checkingAcc = checkAcc;
        this.savingsAcc = savAcc;
        this.interestRate = rate;
        this.accountBalance = balance;
    }

    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }
    public void setLastName(String newLastName){
        this.lastName = newLastName;
    }
    public void setAccountNumber(int newAccountNumber){
        this.accountNumber = newAccountNumber;
    }
    public void setCheckingAccount(Boolean newCheckAccount){
        this.checkingAcc = newCheckAccount;
    }
    public void setSavingsAccount(Boolean newSavingAccount){
        this.savingsAcc = newSavingAccount;
    }
    public void setInterestRate(float newInterestRate){
        this.interestRate = newInterestRate;
    }
    public void setAccountBalance(float newBalance){
        this.accountBalance = newBalance;
    }
    public void setTransaction(String record){
        this.transactions.add(record);
    }
    
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public int getAccountNumber(){
        return this.accountNumber;
    }
    public Boolean getCheckingAccount(){
        return this.checkingAcc;
    }
    public Boolean getSavingsAccount(){
        return this.savingsAcc;
    }
    public float getInterestRate(){
        return this.interestRate;
    }
    public float getAccountBalance(){
        return this.accountBalance;
    }
    public List<String> getTransactions(){
        return this.transactions;
    }

    //This method returns the information of a client
    public void printInfo(){
        System.out.println("\nFirst Name: " + this.firstName);
        System.out.println("Last Name: " + this.lastName);
        System.out.println("Account #: " + this.accountNumber);
        System.out.println("Checking Acc.: " + this.checkingAcc);
        System.out.println("Savings Acc.: " + this.savingsAcc);        
        System.out.printf("Account Balance: $%.2f\n", this.accountBalance);
        System.out.println("Interest Rate: " + this.interestRate + "%");
    }
}