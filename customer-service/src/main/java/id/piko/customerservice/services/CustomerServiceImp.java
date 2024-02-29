package id.piko.customerservice.services;


import id.piko.customerservice.dtos.CustomerDTO;
import id.piko.customerservice.entities.Customer;
import id.piko.customerservice.exceptions.CustomerNotFoundException;
import id.piko.customerservice.exceptions.EmailAlreadyExistsException;
import id.piko.customerservice.mappers.CustomerMapper;
import id.piko.customerservice.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CustomerServiceImp implements CustomerService {


    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) throws EmailAlreadyExistsException {

        log.info(String.format("Saving new Customer => %s ", customerDTO.toString()));

        Optional<Customer> byEmail = customerRepository.findByEmail(customerDTO.getEmail());

        if(byEmail.isPresent()) {
            log.error(String.format("This email %s already exist", customerDTO.getEmail()));
            throw new EmailAlreadyExistsException ();
        }

        Customer customer = customerRepository.save(customerMapper.fromCustomerDTO(customerDTO));

        return customerMapper.fromCustomer(customer);}

    @Override
    public List<CustomerDTO> getAllCustomers() {

       List<Customer> customerList= customerRepository.findAll();


        return customerMapper.fromListCustomer(customerList);
    }

    @Override
    public CustomerDTO findCustomerById(Long id) throws CustomerNotFoundException {

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) throw new CustomerNotFoundException();
        return customerMapper.fromCustomer(customer.get());
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customersList= customerRepository.findByFirstNameContainsIgnoreCase(keyword);
        return customerMapper.fromListCustomer(customersList);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws CustomerNotFoundException {

        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        customerDTO.setId(id);
        Customer customerToUpdate = customerMapper.fromCustomerDTO(customerDTO);
        Customer updatedCustomer = customerRepository.save(customerToUpdate);

        return customerMapper.fromCustomer(updatedCustomer);

    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        customerRepository.deleteById(id);
    }
}
