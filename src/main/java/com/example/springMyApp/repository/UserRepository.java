package com.example.springMyApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springMyApp.dao.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value="SELECT * FROM user where city=:city", nativeQuery=true)
	List<User> findByCity(@Param("city") String city);
	
	@Query(value="SELECT * FROM user where email=:email", nativeQuery = true)
	Optional<User> findByUserEmail(@Param("email") String email);
}
