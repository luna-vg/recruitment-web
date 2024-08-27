package vn.com.recruitment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.recruitment.entities.Cv;

public interface CvRepository extends JpaRepository<Cv, Integer> {

}
