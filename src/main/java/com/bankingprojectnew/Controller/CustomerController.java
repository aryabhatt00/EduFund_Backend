package com.bankingprojectnew.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bankingprojectnew.DTO.LoginRequest;
import com.bankingprojectnew.DTO.CustomerLoginResponseDTO;
import com.bankingprojectnew.DTO.CustomerResponseDTO;
import com.bankingprojectnew.Entity.Customer;
import com.bankingprojectnew.Repository.CustomerRepository;
import com.bankingprojectnew.Service.JwtService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 1. Create new customer
    @PostMapping("/create")
    public Map<String, Object> createCustomer(@RequestBody @Valid Customer customer) {
        boolean exists = customerRepository.existsByCustomerEmail(customer.getCustomerEmail())
                || customerRepository.existsByCustomerPhone(customer.getCustomerPhone());

        Map<String, Object> response = new HashMap<>();

        if (exists) {
            response.put("message", "Use unique customer details");
            return response;
        }

        if (customer.getPassword() != null) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        }

        if (customer.getCustomerAccount() != null) {
            long generatedAccountNumber = 1_000_000_000L + (long)(Math.random() * 9_000_000_000L);
            customer.getCustomerAccount().setAccountNumber(generatedAccountNumber);
        }

        customerRepository.save(customer);

        response.put("message", "Customer created successfully");
        response.put("customerId", customer.getCustomerId());
        response.put("accountNumber", customer.getCustomerAccount().getAccountNumber());
        return response;
    }

    // 2. Find customer with questions
    @PostMapping("/find")
    public ResponseEntity<?> findCustomer(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        long phone = ((Number) payload.get("phone")).longValue();
        String question1 = (String) payload.get("question1");
        String question2 = (String) payload.get("question2");
        String question3 = (String) payload.get("question3");

        Optional<Customer> opt = customerRepository
            .findByCustomerEmailAndCustomerPhoneAndQuestion1AndQuestion2AndQuestion3(
                email, phone, question1, question2, question3
            );

        if (opt.isPresent()) {
            Customer customer = opt.get();
            return ResponseEntity.ok(new CustomerResponseDTO(
                customer.getCustomerId(),
                customer.getCustomerAccount().getAccountNumber()
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "Customer not found"));
    }

    // 3. Get customer by ID
    @GetMapping("/{id}")
    public Object getCustomer(@PathVariable int id){
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // 4. Get all customers
    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // 5. Customer login — returns JWT token + name + email
    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<Customer> optionalCustomer = customerRepository.findByCustomerEmail(email);
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        Customer customer = optionalCustomer.get();

        if (!passwordEncoder.matches(password, customer.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        String token = jwtService.generateToken(customer.getCustomerEmail(), "CUSTOMER");

        CustomerLoginResponseDTO responseDTO = new CustomerLoginResponseDTO();
        responseDTO.setToken(token);
        responseDTO.setName(customer.getCustomerName());
        responseDTO.setEmail(customer.getCustomerEmail());

        return ResponseEntity.ok(responseDTO);
    }


}
