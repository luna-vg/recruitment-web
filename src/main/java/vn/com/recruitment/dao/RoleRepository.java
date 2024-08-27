package vn.com.recruitment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.recruitment.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
