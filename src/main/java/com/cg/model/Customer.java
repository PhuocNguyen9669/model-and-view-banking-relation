package com.cg.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Email(message = "The email address is invalid.")
    @Size(min = 5, max = 50, message = "The length of email must be between 5 and 50 characters.")
    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String address;

    @Digits(integer = 12, fraction = 0)
    @Column(updatable = false)
    private BigDecimal balance;

    @OneToMany(targetEntity = Deposit.class, mappedBy = "customer")
    private Set<Deposit> deposits;

    @OneToMany(targetEntity = Withdraw.class, mappedBy = "customer")
    private Set<Withdraw> withdraws;

    @OneToMany(targetEntity = Transfer.class, mappedBy = "sender")
    private Set<Transfer> sender;

    @OneToMany(targetEntity = Transfer.class, mappedBy = "recipient")
    private Set<Transfer> recipient;
}
