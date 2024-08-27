package vn.com.recruitment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.com.recruitment.entities.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
	
	// query recruitments based on title key search
	@Query(value = "SELECT r.id FROM recruitment r WHERE UPPER(r.title) LIKE %?1%", nativeQuery = true)
	public List<Integer> recruitmentsByTitleKeySearch(String keySearch);

	// query recruitments based on address key search
	@Query(value = "SELECT r.id FROM recruitment r WHERE UPPER(r.address) LIKE %?1%", nativeQuery = true)
	public List<Integer> recruitmentsByAddressKeySearch(String addressKeySearch);
	
	// query recruitments based on company name key search
	@Query(value = "SELECT r.id FROM recruitment r JOIN company c ON r.company_id = c.id WHERE UPPER(c.name_company) LIKE %?1%", nativeQuery = true)
	public List<Integer> recruitmentsByCompanyKeySearch(String companyKeySearch);

}
