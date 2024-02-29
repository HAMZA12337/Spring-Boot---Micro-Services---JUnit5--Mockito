package id.piko.customerservice.services;

import id.piko.customerservice.dtos.CustomerDTO;
import id.piko.customerservice.exceptions.CustomerNotFoundException;
import id.piko.customerservice.exceptions.EmailAlreadyExistsException;

import java.util.List;

public interface CustomerService {


    CustomerDTO saveNewCustomer(CustomerDTO customerDTO) throws EmailAlreadyExistsException;
    List<CustomerDTO> getAllCustomers();
    CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException;
    List<CustomerDTO> searchCustomers(String keyword);
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO)throws CustomerNotFoundException;
    void deleteCustomer(Long id)throws CustomerNotFoundException;










}
