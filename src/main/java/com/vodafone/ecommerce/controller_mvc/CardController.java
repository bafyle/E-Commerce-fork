package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.*;
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
@RequestMapping("/customer/{customerId}/order/card")
@PreAuthorize("hasAuthority('Customer')")
public class CardController {

    private final OrderService orderService;
    private final CartService cartService;


    @Autowired
    public CardController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping
    public String addCard(@PathVariable(name = "customerId") Long customerId, @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Card card = new Card();
        model.addAttribute("customerId", customerId);
        model.addAttribute("card", card);
        return "customer-card";
    }

    @PostMapping
    public String addCard(@PathVariable(name = "customerId") Long customerId,
                          @Valid @ModelAttribute("card") Card card,
                          @AuthenticationPrincipal SecurityUser user,
                          BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerId", customerId);
            model.addAttribute("card", card);
            return "customer-card";
        }


        orderService.checkoutCart(customerId, card);
        return "redirect:/customer/"+customerId+"/order";
    }
}
