package vn.com.recruitment.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "recruitment")
public class Recruitment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "address")
	private String address;

	@Column(name = "description")
	private String description;

	@Column(name = "experience")
	private String experience;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "salary")
	private String salary;

	@Column(name = "status")
	private int status;

	@Column(name = "title")
	private String title;

	@Column(name = "type")
	private String type;

	@Column(name = "deadline")
	private String deadline;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
					CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "company_id")
	private Company company;
		
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToMany
	@JoinTable(
			name="save_job",
			joinColumns=@JoinColumn(name="recruitment_id"),
			inverseJoinColumns=@JoinColumn(name="user_id")
			)
	private List<User> usersSaved;
	
	@OneToMany(mappedBy = "recruitment")
	private List<ApplyPost> applyPosts;	
		
	public Recruitment() {
		
	}

	public Recruitment(String address, String created, String description, String experience, int quantity,
			String salary, int status, String title, String type, int views, String deadline, Company company,
			Category category) {
		this.address = address;
		this.description = description;
		this.experience = experience;
		this.quantity = quantity;
		this.salary = salary;
		this.status = status;
		this.title = title;
		this.type = type;
		this.deadline = deadline;
		this.company = company;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<User> getUsersSaved() {
		return usersSaved;
	}

	public void setUsersSaved(List<User> usersSaved) {
		this.usersSaved = usersSaved;
	}

	public List<ApplyPost> getApplyPosts() {
		return applyPosts;
	}

	public void setApplyPosts(List<ApplyPost> applyPosts) {
		this.applyPosts = applyPosts;
	}
	
}
