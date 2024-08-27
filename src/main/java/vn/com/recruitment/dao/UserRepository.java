package vn.com.recruitment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.com.recruitment.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT u.id FROM user u WHERE u.email = ?1", nativeQuery = true)
	public Optional<Integer> searchIdByUsername(String username);

}
