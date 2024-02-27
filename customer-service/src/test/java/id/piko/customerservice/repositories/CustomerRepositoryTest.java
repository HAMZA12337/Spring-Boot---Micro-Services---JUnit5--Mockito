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




}