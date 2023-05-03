package com.amigoscode;

import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.amigoscode.customer.Gender;
import com.amigoscode.s3.S3Buckets;
import com.amigoscode.s3.S3Service;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;


@SpringBootApplication
public class Main {
	
	private final static String BUCKET_NAME= "amigosecode-fullstack-customer-test"; 

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder, 
            S3Service s3Service,
            S3Buckets s3Buckets) {
        return args -> {
            fakeUserLoadOnStartup(customerRepository, passwordEncoder);
//            testBucket(s3Service, s3Buckets);
        
        };
        
        
    }

	private void testBucket(S3Service s3Service, S3Buckets s3Buckets) {
		s3Service.putObject(s3Buckets.getCustomer(), "foo", "Hello world".getBytes());
        byte[] object = s3Service.getObject(s3Buckets.getCustomer(), "foo");
        System.out.println("Hooray: " + new String(object));
	}

	private void fakeUserLoadOnStartup(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		var faker = new Faker();
		Random random = new Random();
		Name name = faker.name();
		String firstName = name.firstName();
		String lastName = name.lastName();
		int age = random.nextInt(16, 99);
		Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
		String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com";
		Customer customer = new Customer(
		        firstName +  " " + lastName,
		        email,
		        passwordEncoder.encode("password"),
		        age,
		        gender);
		customerRepository.save(customer);
		System.out.println(email);
	}

}
