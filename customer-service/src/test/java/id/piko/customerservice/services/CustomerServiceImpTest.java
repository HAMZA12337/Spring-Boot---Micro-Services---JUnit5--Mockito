package id.piko.customerservice.services;

import id.piko.customerservice.dtos.CustomerDTO;
import id.piko.customerservice.entities.Customer;
import id.piko.customerservice.exceptions.CustomerNotFoundException;
import id.piko.customerservice.mappers.CustomerMapper;
import id.piko.customerservice.repositories.CustomerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
class CustomerServiceImpTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerServiceImp undertestCustomerServiceImp;



    @Test

    public void testSaveNewCustomer(){

        // Create a CustomerDTO object with sample data
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();

        // Create a sample saved customer object
        Customer savedCustomer = Customer.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();

        // Create another customer object with the same data
        Customer customer = Customer.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();

        // Create an expected CustomerDTO object with the same data
        CustomerDTO expected = CustomerDTO.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();

        // Mocking the behavior of customerRepository to return an empty optional when findByEmail is called
        Mockito.when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(Optional.empty());

        // Mocking the behavior of customerMapper to return customer when fromCustomerDTO is called with customerDTO
        Mockito.when(customerMapper.fromCustomerDTO(customerDTO)).thenReturn(customer);

        // Mocking the behavior of customerRepository to return savedCustomer when save is called with customer
        Mockito.when(customerRepository.save(customer)).thenReturn(savedCustomer);

        // Mocking the behavior of customerMapper to return expected when fromCustomer is called with savedCustomer
        Mockito.when(customerMapper.fromCustomer(savedCustomer)).thenReturn(expected);

        // Calling the method to be tested
        CustomerDTO result = undertestCustomerServiceImp.saveNewCustomer(customerDTO);

        // Assertions to ensure that result is not null and is equal to expected
        AssertionsForClassTypes.assertThat(result).isNotNull();
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }


@Test

    public void testGetAllCustomers(){

    List<Customer> customers = List.of(
            Customer.builder().firstName("Mohamed").lastName("Youssfi").email("med@gmail.com").build(),
            Customer.builder().firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build()
    );
    List<CustomerDTO> expected = List.of(
            CustomerDTO.builder().firstName("Mohamed").lastName("Youssfi").email("med@gmail.com").build(),
            CustomerDTO.builder().firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build()
    );
    Mockito.when(customerRepository.findAll()).thenReturn(customers);
    Mockito.when(customerMapper.fromListCustomer(customers)).thenReturn(expected);
    List<CustomerDTO> result = undertestCustomerServiceImp.getAllCustomers();
    AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

}


@Test

    public void testFindCustomerById() throws CustomerNotFoundException {


        Customer customer=Customer.builder().
                id(1L).firstName("Mohamed").lastName("Youssfi").email("med@gmail.com").build();



    CustomerDTO customerDTO=CustomerDTO.builder().
            id(1L).firstName("Mohamed").lastName("Youssfi").email("med@gmail.com").build();



    Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
    Mockito.when(customerMapper.fromCustomer(customer)).thenReturn(customerDTO);

    CustomerDTO result=undertestCustomerServiceImp.findCustomerById(1L);

    AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(customerDTO);
    }


    @Test

    public void testSearchCustomers(){

    String keyword="m";
        List<Customer> customersList = List.of(

                Customer.builder()
                        .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build(),

                Customer.builder()
                        .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build(),

                Customer.builder()
                        .firstName("Hamane").lastName("yamal").email("hanane@gmail.com").build()

        );
        List<CustomerDTO> customersListDTO = List.of(

                CustomerDTO.builder()
                        .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build(),

                CustomerDTO.builder()
                        .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build(),

                CustomerDTO.builder()
                        .firstName("Hamane").lastName("yamal").email("hanane@gmail.com").build()

        );

     Mockito.when(customerRepository.findByFirstNameContainsIgnoreCase(keyword)).thenReturn(customersList);
     Mockito.when(customerMapper.fromListCustomer(customersList)).thenReturn(customersListDTO);

     List<CustomerDTO> result= undertestCustomerServiceImp.searchCustomers(keyword);

     AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(customersListDTO);


    }














}