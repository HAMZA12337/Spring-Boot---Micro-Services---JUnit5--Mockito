package id.piko.customerservice;

import id.piko.customerservice.entities.Customer;
import id.piko.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {



		SpringApplication.run(CustomerServiceApplication.class, args);


	}


 @Bean
 @Profile("!test")
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){

        return args ->{

            List<Customer> customers = List.of(
                    Customer.builder()
                            .firstName("HAMZA").lastName("BRAIMI").email("hamza@gmail.com").build(),
                    Customer.builder()
                            .firstName("Ahmed").lastName("Yassine").email("ahmed@gmail.com").build(),
                    Customer.builder()
                            .firstName("Hanane").lastName("yamal").email("hanane@gmail.com").build()
            );
            customerRepository.saveAll(customers);

        };
    }






}
