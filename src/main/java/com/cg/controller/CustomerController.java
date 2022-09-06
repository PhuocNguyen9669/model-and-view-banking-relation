package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/list");
        Iterable<Customer> customers = customerService.findAllByDeletedIsFalse();
        modelAndView.addObject("customers", customers);

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("success", null);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/customer/edit");
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);

            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @GetMapping("/suspended/{id}")
    public ModelAndView showSuspensionForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/");

        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("success", null);
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping(value = "/create", produces = "application/json; charset=UTF-8")
    public ModelAndView save(@ModelAttribute Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/create");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("script", true);
        } else {
            if (customerService.existsByEmail(customer.getEmail())){
                modelAndView.addObject("error", "Email already exists!");
            }
            else {
                try {
                    customer.setBalance(BigDecimal.valueOf(0));
                    customerService.save(customer);

                    modelAndView.addObject("customer", new Customer());
                    modelAndView.addObject("success", "Successfully added new customers");

                } catch (Exception e) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/customer/edit");

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("error", true);
        } else {
            try {
                customerService.save(customer);

                modelAndView.addObject("customer", customer);
                modelAndView.addObject("success", "Customer updated successfully");
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("error", "Invalid data, please contact system administrator");
            }
        }

        return modelAndView;
    }


}
