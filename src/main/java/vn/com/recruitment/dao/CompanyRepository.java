package vn.com.recruitment.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.com.recruitment.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	// query most popular companies (based on number of recruitments that the company offer)
	@Query(value = "SELECT c.id FROM company c JOIN (SELECT r.company_id, COUNT(*) AS position_count FROM recruitment r GROUP BY r.company_id) r ON c.id = r.company_id ORDER BY r.position_count DESC LIMIT 2", nativeQuery = true)
	public List<Integer> mostPopularCompanies();
	
	@Query(value = "SELECT c.id FROM company c WHERE c.user_id = ?1", nativeQuery = true)
	public Optional<Integer> findCompanyIdByUserId(int userId);

}
