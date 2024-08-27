package vn.com.recruitment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.recruitment.entities.SaveJob;

public interface SaveJobRepository extends JpaRepository<SaveJob, Integer> {

}
