package id.piko.customerservice.mappers;

import id.piko.customerservice.dtos.CustomerDTO;
import id.piko.customerservice.entities.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.instrument.IllegalClassFormatException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;



@ActiveProfiles("test")
class CustomerMapperTest {


    CustomerMapper underTest= new CustomerMapper();


    @Test
    public void testMapCustomerToCustomerDTO(){


        Customer givenCustomer = Customer.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();


        Customer expectedCustomer = Customer.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();

        CustomerDTO result=underTest.fromCustomer(givenCustomer);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedCustomer);


    }



    @Test
    public void testMapCustomerDTOToCustomer(){


        CustomerDTO givenCustomer = CustomerDTO.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();


        Customer expectedCustomer = Customer.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build();

        Customer result=underTest.fromCustomerDTO(givenCustomer);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedCustomer);


    }

    @Test
    public void testMapListCustomersToListCustomersDTO(){


        List<Customer> givenListCustomers = List.of(

                Customer.builder()
                        .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build(),

                Customer.builder()
                        .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build(),

                Customer.builder()
                        .firstName("Hanane").lastName("yamal").email("hanane@gmail.com").build()

        );

       List<CustomerDTO> expectedListCustomers = List.of(

               CustomerDTO.builder()
                       .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build(),

               CustomerDTO.builder()
                       .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build(),

               CustomerDTO.builder()
                       .firstName("Hanane").lastName("yamal").email("hanane@gmail.com").build()

       );

        List<CustomerDTO> result=underTest.fromListCustomer(givenListCustomers);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedListCustomers);


    }






    @Test
    public void testMapNullCustomersToListCustomersDTO(){


        List<Customer> givenListCustomers = null;

        List<CustomerDTO> expectedListCustomers = List.of(

                CustomerDTO.builder()
                        .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build(),

                CustomerDTO.builder()
                        .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build(),

                CustomerDTO.builder()
                        .firstName("Hanane").lastName("yamal").email("hanane@gmail.com").build()

        );

         assertThatThrownBy(()->underTest.fromListCustomer(givenListCustomers)).isInstanceOf(IllegalClassFormatException.class);

        List<CustomerDTO> result=underTest.fromListCustomer(givenListCustomers);

    }







}