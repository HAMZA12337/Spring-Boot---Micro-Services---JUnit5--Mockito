package id.piko.customerservice.repositories;


import id.piko.customerservice.entities.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@ActiveProfiles("test")
@DataJpaTest
class CustomerRepositoryTest {



    @Autowired
    private CustomerRepository customerRepository;


    @BeforeEach
    public void setUp(){

        customerRepository.save(Customer.builder()
                .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build());

        customerRepository.save(Customer.builder()
                .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build());

        customerRepository.save(Customer.builder()
                .firstName("Hanane").lastName("yamal").email("hanane@gmail.com").build());

    }

        @Test
        public void testFindCustomerByEmail(){

                String email="hamza@gmail.com";

            Optional<Customer> result=customerRepository.findByEmail(email);

            AssertionsForClassTypes.assertThat(result).isPresent();
  }

    @Test
    public void testNotFindCustomerByEmail(){

        String email="hamzacdcd@gmail.com";

        Optional<Customer> result=customerRepository.findByEmail(email);

        AssertionsForClassTypes.assertThat(result).isEmpty();
    }


    @Test

    public void testFindCustomersByFirstName(){

        String name="a";


        List<Customer> expectedList=List.of(

                Customer.builder()
                        .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build(),

                Customer.builder()
                .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build(),

                Customer.builder()
                .firstName("Hanane").lastName("yamal").email("hanane@gmail.com").build()

        );

        List<Customer> customerList=customerRepository.findByFirstNameContainsIgnoreCase(name);

                 assertThat(customerList).isNotNull();

                 assertThat(customerList.size()).isEqualTo(expectedList.size());

                 // Compare By Content

                assertThat(customerList).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedList);




    }






}