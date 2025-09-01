package com.bankingprojectnew.Repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bankingprojectnew.DTO.AdminAccountDTO;
import com.bankingprojectnew.Entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT new com.bankingprojectnew.DTO.AdminAccountDTO(a.accountId, a.accountNumber, a.accountBalance, a.customer.customerName) FROM Account a")
	List<AdminAccountDTO> getAllAdminAccounts();

	Optional<Account> findByAccountNumber(long accountNumber);

	Optional<Account> findById(long id);

	void deleteById(Long id);
   
}


