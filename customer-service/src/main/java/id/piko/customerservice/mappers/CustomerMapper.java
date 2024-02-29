package id.piko.customerservice.mappers;


import id.piko.customerservice.dtos.CustomerDTO;
import id.piko.customerservice.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    private ModelMapper modelMapper = new ModelMapper();


    public CustomerDTO fromCustomer(Customer customer){

     return modelMapper.map(customer,CustomerDTO.class);}


    public Customer fromCustomerDTO(CustomerDTO customerDTO){

        return modelMapper.map(customerDTO,Customer.class);}


    public List<CustomerDTO> fromListCustomer(List<Customer> customerList){


        return customerList.stream().map(cust->modelMapper.map(cust, CustomerDTO.class)).collect(Collectors.toList());

    }







}
