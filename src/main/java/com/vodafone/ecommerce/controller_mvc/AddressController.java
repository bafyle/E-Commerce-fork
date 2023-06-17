package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.AddressService;
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
@RequestMapping("/customer/{customerId}/address")
@PreAuthorize("hasAuthority('Customer')")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String getAllCustomerAddresses(@PathVariable(name = "customerId") Long customerId,
                                          @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());

//        Address a1 = new Address();
//        a1.setAddress("Add 1");
//        addressService.addAddress(customerId, a1);
        Set<Address> addresses = addressService.getAllAddressesByCustomerId(customerId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("all_addresses", addresses);
        return "customer-addressesRead";
    }

    @GetMapping(value = "/{addressId}")
    public String getCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                     @PathVariable("addressId") Long addressId,
                                     @AuthenticationPrincipal SecurityUser user,
                                     Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Address address = addressService.getAddressById(customerId, addressId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("address", address);
        return "customer-addressRead";
    }

    @GetMapping(value = "/add")
    public String addCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                     @AuthenticationPrincipal SecurityUser user,
                                     Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Address address = new Address();
        model.addAttribute("customerId", customerId);
        model.addAttribute("address", address);
        return "customer-addressCreate";
    }

    @PostMapping("/add")
    public String addCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                     @Valid @ModelAttribute("address") String address,
                                     @AuthenticationPrincipal SecurityUser user,
                                     BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        if (bindingResult.hasErrors())
        {
            model.addAttribute("customerId", customerId);
            model.addAttribute("address",address);
            return "customer-addressCreate";
        }

        Address address1 = new Address();
        address1.setAddress(address);
    //    address1.setCustomer();
        addressService.addAddress(customerId,address1);
        return "redirect:/customer/"+customerId+"/address";
    }



    @GetMapping(value = "/{addressId}/edit")
    public String updateCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                        @PathVariable("addressId") Long addressId,
                                        @AuthenticationPrincipal SecurityUser user,
                                        Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Address address = addressService.getAddressById(customerId,addressId);
        model.addAttribute("to_be_updatedAddress", address);
        return "customer-addressEdit";
    }

    @PostMapping(value = "/{addressId}/edit")
    public String updateCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                        @PathVariable("addressId") Long addressId,
                                        @ModelAttribute("to_be_updatedAddress") Address address,
                                        @AuthenticationPrincipal SecurityUser user,
                                        BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        if (bindingResult.hasErrors())
        {
            model.addAttribute("address", addressService.getAddressById(customerId,addressId));
            return "customer-addressEdit";
        }

        Address updatedAddress = addressService.getAddressById(customerId,addressId);
        updatedAddress.setAddress(address.getAddress());
        addressService.updateAddress(customerId,addressId, updatedAddress);
        Set<Address> addresses = addressService.getAllAddressesByCustomerId(customerId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("all_addresses", addresses);
        return "redirect:/customer/"+customerId+"/address";
    }

    @GetMapping(value = "/{addressId}/delete")
    public String deleteAddress(@PathVariable(name = "customerId") Long customerId,
                                @PathVariable("addressId") Long addressId,
                                @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        addressService.deleteAddress(customerId, addressId);
        return "redirect:/customer/"+customerId+"/address";
    }






}
