package com.bankingprojectnew.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bankingprojectnew.DTO.AdminCustomerDTO;
import com.bankingprojectnew.Entity.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByCustomerEmail(String customerEmail);  
    boolean existsByCustomerPhone(Long customerPhone);

    // Your new DTO-based query:
    @Query("SELECT new com.bankingprojectnew.DTO.AdminCustomerDTO(c.customerId, c.customerName, c.customerEmail, c.customerPhone, c.customerDateOfBirth) FROM Customer c")
    List<AdminCustomerDTO> getAllAdminCustomers();
    Optional<Customer> findByCustomerEmail(String email);

    Optional<Customer> findByCustomerEmailAndCustomerPhoneAndQuestion1AndQuestion2AndQuestion3(
        String email, long phone, String question1, String question2, String question3);
}
