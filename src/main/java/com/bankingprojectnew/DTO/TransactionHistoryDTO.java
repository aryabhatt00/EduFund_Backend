package com.bankingprojectnew.DTO;

import java.util.Date;
import java.util.List;

public class TransactionHistoryDTO {

    private long accountNumber;
    private double accountBalance;
    private List<TransactionEntry> transactions;

    public TransactionHistoryDTO(long accountNumber, double accountBalance, List<TransactionEntry> transactions) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.transactions = transactions;
    }

    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(double accountBalance) { this.accountBalance = accountBalance; }

    public List<TransactionEntry> getTransactions() { return transactions; }
    public void setTransactions(List<TransactionEntry> transactions) { this.transactions = transactions; }

    // Nested DTO for individual transaction
    public static class TransactionEntry {
        private long transactionId;
        private String transactionType;
        private double transactionAmount;
        private Date transactionDate;
        private double balanceAfterTransaction;
        private long accountNumber;  
        private String customerName; 

        public double getBalanceAfterTransaction() {
            return balanceAfterTransaction;
        }

        public void setBalanceAfterTransaction(double balanceAfterTransaction) {
            this.balanceAfterTransaction = balanceAfterTransaction;
        }


        public TransactionEntry(long transactionId, String transactionType, double transactionAmount, Date transactionDate, double balanceAfterTransaction,long accountNumber, String customerName) {
            this.transactionId = transactionId;
            this.transactionType = transactionType;
            this.transactionAmount = transactionAmount;
            this.transactionDate = transactionDate;
            this.balanceAfterTransaction=balanceAfterTransaction;
            this.accountNumber = accountNumber;
            this.customerName = customerName;
        }

        public long getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(long accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public long getTransactionId() { return transactionId; }
        public void setTransactionId(long transactionId) { this.transactionId = transactionId; }

        public String getTransactionType() { return transactionType; }
        public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

        public double getTransactionAmount() { return transactionAmount; }
        public void setTransactionAmount(double transactionAmount) { this.transactionAmount = transactionAmount; }

        public Date getTransactionDate() { return transactionDate; }
        public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }
    }
}
