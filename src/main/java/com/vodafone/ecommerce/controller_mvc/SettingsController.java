package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.AddressService;
import com.vodafone.ecommerce.service.CustomerService;
import com.vodafone.ecommerce.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer/{customerId}/account/settings")
@PreAuthorize("hasAuthority('Customer')")
public class SettingsController {

    private final CustomerService customerService;

    @Autowired
    public SettingsController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String getAllSettings(@PathVariable(name = "customerId") Long customerId, @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        model.addAttribute("customerId", customerId);
        return "customer-settingsRead";
    }

    @GetMapping(value = "/edit/name")
    public String updateCustomerName(@PathVariable(name = "customerId") Long customerId,
                                     @AuthenticationPrincipal SecurityUser user,
                                     Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "customer-settingsEditFullName";
    }

    @PostMapping(value = "/edit/name")
    public String updateCustomerName(@PathVariable(name = "customerId") Long customerId,
                                     @Valid @ModelAttribute("customer") Customer customer,
                                     @AuthenticationPrincipal SecurityUser user,
                                     BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        if (bindingResult.hasErrors())
        {
            model.addAttribute("customer", customer);
            return "customer-settingsEditFullName";
        }

        Customer updatedCustomer = customerService.getCustomerById(customerId);
        updatedCustomer.setName(customer.getName());
        customerService.updateCustomer(updatedCustomer, customerId);
        return "redirect:/customer/"+customerId+"/account";
    }

    @GetMapping(value = "/edit/email")
    public String updateCustomerEmail(@PathVariable(name = "customerId") Long customerId,
                                     @AuthenticationPrincipal SecurityUser user,
                                     Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "customer-settingsEditEmail";
    }

    @PostMapping(value = "/edit/email")
    public String updateCustomerEmail(@PathVariable(name = "customerId") Long customerId,
                                     @Valid @ModelAttribute("customer") Customer customer,
                                     @AuthenticationPrincipal SecurityUser user,
                                     BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        if (bindingResult.hasErrors())
        {
            model.addAttribute("customer", customer);
            return "customer-settingsEditEmail";
        }

        Customer updatedCustomer = customerService.getCustomerById(customerId);
        updatedCustomer.setEmail(customer.getEmail());
        customerService.updateCustomer(updatedCustomer, customerId);
        return "redirect:/customer/"+customerId+"/account";
    }

    @GetMapping(value = "/edit/password")
    public String updateCustomerPassword(@PathVariable(name = "customerId") Long customerId,
                                     @AuthenticationPrincipal SecurityUser user,
                                     Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Customer customer = customerService.getCustomerById(customerId);
        model.addAttribute("customer", customer);
        return "customer-settingsEditPassword";
    }

    @PostMapping(value = "/edit/password")
    public String updateCustomerPassword(@PathVariable(name = "customerId") Long customerId,
                                     @Valid @ModelAttribute("customer") Customer customer,
                                     @AuthenticationPrincipal SecurityUser user,
                                     BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        if (bindingResult.hasErrors())
        {
            model.addAttribute("customer", customer);
            return "customer-settingsEditPassword";
        }

        Customer updatedCustomer = customerService.getCustomerById(customerId);
        updatedCustomer.setPassword(customer.getPassword());
        customerService.updateCustomer(updatedCustomer, customerId);
        return "redirect:/customer/"+customerId+"/account";
    }


}
