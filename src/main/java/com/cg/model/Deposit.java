package com.cg.model;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
@EqualsAndHashCode(cache = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deposits")
@Accessors(chain = true)
public class Deposit extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Digits(integer = 9, fraction = 0)
    @Column(name = "transaction_amount", nullable = false)
    private BigDecimal transactionAmount;

    public Deposit(Customer customer, BigDecimal transactionAmount){
        this.customer = customer;
        this.transactionAmount = transactionAmount;
    }

}
