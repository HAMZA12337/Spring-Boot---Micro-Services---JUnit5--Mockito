# Mastering Unit Testing with Junit5 & Mockito :Micro Services End To End Testing .

![alt text](junit5.png)

When you're Creating a Java application,you need to make sure it's working as expected .This is where Junit testing comes in. Junit is Unit testing framework
for Java programming language.It plays a crucial role in test-driven development (TDD).This ensures that  your code is working correctly from the beginning.
JUnit is an instance of the xUnit architecture for unit testing frameworks. It provides assertions to identify test methods, test-cases, setup methods, and so on. Note that JUnit is not built into the Java language,
but it’s widely used by Java developers to perform unit testing.

### Step 1 :Installing JUnit

    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <
    </dependency>

### Basic Structure of a Junit Test 

Once you have installed Junit,Now you can start writing your tests.Each test Methode is annotated with @Test and contains the code to test a particular unit of functionality.
Here’s a simple example:

    import org.junit.Test;
    import static org.junit.Assert.assertEquals;
        public class MyFirstJUnitTest {
            @Test
            public void testAddition() {
            int result = 1 + 1;
            assertEquals(2, result);
    }


### Making Assertions in JUnit

Assertions are the core component of your tests.They're what allow you to verify that your code is 
working as expected .Junit provides a variety of assertion methods including assertsEquals ,assertTrue,assertThat.assertNull,and more .Here's an example of how
you might assertions in your tests:

        @Test
        public void testFindCustomerByEmail(){
        
                        String email="hamza@gmail.com";
        
                    Optional<Customer> result=customerRepository.findByEmail(email);
        
                    AssertionsForClassTypes.assertThat(result).isPresent();
        }


### Unit Testing in Micro service

The first step is to create customer service as a microservice. Therefore, we should begin by installing the following dependencies:

![customer-service1.PNG](Captures%2Fcustomer-service1.PNG)


#### Customer entities

    @Entity
    @AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
    public class Customer {
    
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id ;
        @NotEmpty
        @Size(min = 3)
        private String firstName;
        @NotEmpty @Size(min = 3)
        private String lastName;
        @NotEmpty @Size(min = 5)
        @Column(unique=true)
        private String email;
    }

#### Customer Repository

    public interface CustomerRepository extends JpaRepository<Customer,Long> {
         public Optional<Customer> findByEmail(String email);
         List<Customer> findByFirstNameContainsIgnoreCase(String keyword);
    }

#### Customer DTO

    @AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
    public class CustomerDTO {
            private Long id ;
            @NotEmpty
            @Size(min = 3)
            private String firstName;
            @NotEmpty @Size(min = 3)
            private String lastName;
            @NotEmpty @Size(min = 5)
            @Column(unique=true)
            private String email;
    
    }

#### Customer Mapper

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
    

### Customer Repository Test

In this stage, we should revisit testing all the functions that we defined in the Customer Repository interface.


@ActiveProfiles("test"): This annotation sets the active profiles to "test", indicating that this test class
 should use the test-specific configurations defined in the application.


@DataJpaTest:This annotation is used to test JPA repositories. It will configure an in-memory
 database, set up Spring Data JPA repositories, and provide some useful testing
 features such as transaction management.

    class CustomerRepositoryTest {
    
        // Autowiring the CustomerRepository bean to interact with the database.
        @Autowired
        private CustomerRepository customerRepository;
    
        // This method runs before each test method to set up the test data.
        @BeforeEach
        public void setUp(){
    
            // Saving sample customer data into the repository for testing purposes.
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

### Mockito - JUnit Integration

After starting this part, we should understand what Mockito is and why we need it ?
  
  => Mockito is a mocking framework, JAVA-based library that is used for effective unit testing of JAVA applications.
  Mockito is particularly valuable in until testing because it helps verify interactions between different  parts of the code and ensures that each unit behaves as expected. 
  Mockito is used to mock interfaces so that a dummy functionality can be added to a mock interface that can be used in unit testing.
  This tutorial should help you learn how to create unit tests with Mockito as well as how to use its APIs in a simple and intuitive way.


#### @ExtendWith(MockitoExtension.class): This annotation is used to enable Mockito support for this test class, allowing the usage of Mockito annotations like @Mock, @InjectMocks, etc.

#### @Mock: This annotation is used to mock dependencies or collaborators of the class under test (CustomerServiceImp). In this case, CustomerRepository and CustomerMapper are being mocked.

#### @InjectMocks: This annotation injects mock dependencies into the class under test (CustomerServiceImp).

#### @Test: This annotation denotes that the method annotated with it is a test method.

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


Developed by Piko Dev => Hamza Braimi :)
