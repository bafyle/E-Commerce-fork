package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.exception.AddressNotFoundException;
import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.AddressService;
import com.vodafone.ecommerce.service.CartService;
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
    private final CartService cartService;

    public CashController(OrderService orderService, AddressService addressService, CartService cartService) {
        this.orderService = orderService;
        this.addressService = addressService;
        this.cartService = cartService;
    }

    @GetMapping
    public String chooseCustomerAddresses(@PathVariable(name = "customerId") Long customerId,
                                          @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Set<Address> addresses = addressService.getAllAddressesByCustomerId(customerId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("all_addresses", addresses);
        model.addAttribute("selectedAddress","");
        return "customer-addressesChoose";
    }

    @GetMapping(value = "/cash")
    public String chooseCustomerAddressesCash(@PathVariable(name = "customerId") Long customerId,
                                          @AuthenticationPrincipal SecurityUser user,
                                          Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());

        Cart cart = cartService.getCartByCustomerId(customerId);
        if (!(cart != null && cart.getAddress() != null &&
                !cart.getAddress().isEmpty()))
        {
            throw new AddressNotFoundException("Address Not Found, Please select an adress for the order");
        }

        Cart order = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = order.getCartItems();
        model.addAttribute("customerId", customerId);
        model.addAttribute("cart_items", cartItems);
        model.addAttribute("selectedAddress","");

        orderService.checkoutCart(customerId);
        return "redirect:/customer/"+customerId+"/order";
    }

    @PostMapping(value = "/choose")
    public String selectCustomerAddress(@PathVariable(name = "customerId") Long customerId,
                                        @Valid @ModelAttribute("selectedAddress") String selectedAddress,
                                        @AuthenticationPrincipal SecurityUser user,
                                        BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());

        Set<Address> addresses = addressService.getAllAddressesByCustomerId(customerId);
        if (bindingResult.hasErrors())
        {
            model.addAttribute("customerId", customerId);
            model.addAttribute("all_addresses", addresses);
            model.addAttribute("selectedAddress",selectedAddress);
            return "customer-addressesChoose";
        }

           Cart cart =cartService.getCartByCustomerId(customerId);
           cart.setAddress(selectedAddress);
        model.addAttribute("customerId", customerId);
        model.addAttribute("all_addresses", addresses);
      //  model.addAttribute("selectedAddress","");
        cartService.updateCartAddress(selectedAddress,customerId);
        return "redirect:/customer/"+customerId+"/order/address";
    }
}
