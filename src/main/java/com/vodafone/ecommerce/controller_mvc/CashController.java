package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.AddressService;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/customer/{customerId}/order/address")
@PreAuthorize("hasAuthority('Customer')")
public class CashController {

    private final OrderService orderService;
    private final AddressService addressService;


    @Autowired
    public CashController(OrderService orderService, AddressService addressService) {
        this.orderService = orderService;
        this.addressService = addressService;
    }

    @GetMapping
    public String chooseCustomerAddresses(@PathVariable(name = "customerId") Long customerId,
                                          @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Set<Address> addresses = addressService.getAllAddressesByCustomerId(customerId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("all_addresses", addresses);
        return "customer-addressesChoose";
    }

    @PostMapping
    public String chooseCustomerAddresses(@PathVariable(name = "customerId") Long customerId,
                                          @AuthenticationPrincipal SecurityUser user,
                                          @Valid @ModelAttribute("address") Address address,
                                          BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("address", address);
            return "customer-addressesChoose";
        }


        orderService.checkoutCart(customerId, address);
        return "redirect:/customer/"+customerId+"/order";
    }
}
