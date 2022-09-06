package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.dto.DepositDTO;
import com.cg.model.dto.WithdrawDTO;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import com.cg.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository  customerRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private WithdrawRepository withdrawRepository;



    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Iterable<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }


    @Override
    public void remove(Long id) {
        customerRepository.deleteById(id);
    }


    @Override
    public Optional<DepositDTO> findByIdWithDepositDTO(Long id) {
        return customerRepository.findByWithDepositDTO(id);
    }

    @Override
    public Optional<WithdrawDTO> findByWithWithdrawDTO(Long id) {
        return customerRepository.findByWithWithdrawDTO(id);
    }

    @Override
    public void doDeposit(Long customerId, DepositDTO depositDTO) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        customerRepository.incrementBalance(depositDTO.getTransactionAmount(), customerId);

        depositRepository.save(depositDTO.toDeposit(customer));
    }

    @Override
    public void doWithdraw(Long customerId, WithdrawDTO withdrawDTO) {

        Optional<Customer> customer = customerRepository.findById(customerId);

        customerRepository.reduceBalance(withdrawDTO.getTransactionAmount(), customerId);

        withdrawRepository.save(withdrawDTO.toWithdraw(customer));
    }

    @Override
    public void incrementBalance(BigDecimal balance, Long id) {
        customerRepository.incrementBalance(balance, id);
    }

    @Override
    public void reduceBalance(BigDecimal balance, Long id) {
        customerRepository.reduceBalance(balance, id);
    }
}
