package com.amigoscode.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Integer id);
    Optional<Customer> findCustomerByEmail(String email);
    
    @Modifying
    @Query(value="UPDATE Customer c SET c.profileImageId=:imageId WHERE c.id=:customerId")
    int updateProfileImageId(
    		@Param("imageId") String profileImageId, 
    		@Param("customerId") Integer customerId);
}
