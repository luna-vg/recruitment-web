package vn.com.recruitment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.com.recruitment.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	// query most common categories (based on number of recruitments having the category)
	@Query(value = "SELECT c.id FROM category c ORDER BY c.number_choose DESC LIMIT 4", nativeQuery = true)
	public List<Integer> mostChosenCategory();
	
}
