package com.bankingprojectnew.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankingprojectnew.Entity.Account;
import com.bankingprojectnew.Entity.BankTransaction;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Integer> {

    List<BankTransaction> findByTransactionAccount_AccountNumber(long accountNumber);

    List<BankTransaction> findByTransactionAccount(Account account);
    void deleteByTransactionAccount(Account account);

}
