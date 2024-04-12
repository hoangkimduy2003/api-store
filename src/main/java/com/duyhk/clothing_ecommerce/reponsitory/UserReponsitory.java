package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserReponsitory extends JpaRepository<Users, Long> {
    @Query("select u from Users u where u.phoneNumber = :phoneNumber")
    Optional<Users> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("select u from Users u where u.phoneNumber = :phoneNumber and u.password = :pass")
    Optional<Users> login(@Param("phoneNumber") String phoneNumber,@Param("pass") String pass);
    @Query("select u from Users u where u.role = com.duyhk.clothing_ecommerce.entity.Role.CUSTOMER " +
            "and (:phoneNumber is null or :phoneNumber = '' or u.phoneNumber like :phoneNumber)")
    Page<Users> getCustomer(@Param("phoneNumber") String phoneNumber, Pageable pageable);

    @Query("select u from Users u where u.role = com.duyhk.clothing_ecommerce.entity.Role.ADMIN " +
            "and (:phoneNumber is null or :phoneNumber = '' or u.phoneNumber like :phoneNumber)")
    Page<Users> getStaff(@Param("phoneNumber") String phoneNumber, Pageable pageable);

    @Query("select u from Users u where u.email is not null")
    List<Users> findUserHaveEmail();
}
