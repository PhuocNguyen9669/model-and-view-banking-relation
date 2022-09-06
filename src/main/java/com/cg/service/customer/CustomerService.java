package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.model.dto.DepositDTO;
import com.cg.model.dto.WithdrawDTO;
import com.cg.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CustomerService extends IGeneralService<Customer> {
    Boolean existsByEmail(String email);

    Iterable<Customer> findAllByDeletedIsFalse();

    Optional<DepositDTO> findByIdWithDepositDTO(Long id);

    Optional<WithdrawDTO> findByWithWithdrawDTO(Long id);

    void doDeposit(Long customerId, DepositDTO depositDTO);

    void doWithdraw(Long customerId, WithdrawDTO withdrawDTO);

    void incrementBalance(BigDecimal balance, Long id);

    void reduceBalance(BigDecimal balance, Long id);
}
