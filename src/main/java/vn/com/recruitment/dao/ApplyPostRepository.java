package vn.com.recruitment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.com.recruitment.entities.ApplyPost;

public interface ApplyPostRepository extends JpaRepository<ApplyPost, Integer> {

	// query most common recruitments (based on number of people applied)
	@Query(value = "SELECT a.recruitment_id FROM applypost a GROUP BY a.recruitment_id ORDER BY COUNT(*) DESC LIMIT 3", nativeQuery = true)
	public List<Integer> mostCommonRecruitments();
	
}
