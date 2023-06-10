package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.repository.AddressRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AddressService {
    private final AddressRepo addressRepo;
    private final CustomerService customerService;

    @Autowired
    public AddressService(AddressRepo addressRepo, CustomerService customerService) {
        this.addressRepo = addressRepo;
        this.customerService = customerService;
    }

    public Set<Address> getAllAddressesByCustomerId(Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);

        return customer.getAddresses();
    }

    public Address getAddressById(Long customerId, Long addressId) {
        Customer customer = customerService.getCustomerById(customerId);
        Optional<Address> addressById = customer.getAddresses().stream().filter(address -> address.getId().equals(addressId)).findFirst();

        if (addressById.isEmpty()) {
            throw new NotFoundException("This address is not registered to this customer");
        }

        return addressById.get();
    }


    public Address addAddress(Long customerId, Address address) {
        Customer customer = customerService.getCustomerById(customerId);

        if (customer.getAddresses().stream().anyMatch(address1 -> address1.getAddress().equals(address.getAddress()))) {
            throw new DuplicateEntityException("This address is already registered to this customer");
        }

        address.setCustomer(customer);

        return addressRepo.save(address);
    }

    public Address updateAddress(Long customerId, Long addressId, Address address) {
        Customer customer = customerService.getCustomerById(customerId);

        if (customer.getAddresses().stream().noneMatch(address1 -> address1.getId().equals(addressId))) {
            throw new NotFoundException("This address is not registered to this customer");
        }

        address.setCustomer(customer);
        address.setId(addressId);
        return addressRepo.save(address);
    }

    public void deleteAddress(Long customerId, Long addressId) {
        Optional<Address> address = addressRepo.findByIdAndCustomerId(addressId,customerId);

        if (address.isEmpty()) {
            throw new NotFoundException("This address is not registered to this customer");
        }

        addressRepo.delete(address.get());
    }
}
