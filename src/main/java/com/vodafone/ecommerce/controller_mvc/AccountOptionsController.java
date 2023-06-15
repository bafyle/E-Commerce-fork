package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.util.AuthUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer/{customerId}/account")
@PreAuthorize("hasAuthority('Customer')")
public class AccountOptionsController {

    @GetMapping
    public String getAllCustomerOptions(@PathVariable(name = "customerId") Long customerId, @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        model.addAttribute("customerId", customerId);
        return "customer-accountOptionsRead";
    }
}
