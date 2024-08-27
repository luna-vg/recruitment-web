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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "address")
	private String address;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "password")
	private String password;
			
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "status")
	private int status;
	
	@Column(name = "role_id")
	private int roleId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cv_id")
	private Cv cv;
	
	@ManyToMany
	@JoinTable(
			name="save_job",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="recruitment_id")
			)
	private List<Recruitment> savedJobs;
	
	@ManyToMany
	@JoinTable(
			name="follow_company",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="company_id")
			)
	private List<Company> followings;
	
	@OneToMany(mappedBy = "user")
	private List<ApplyPost> appliedJobs;
		
	public User() {
		
	}
	
	public User(String address, String description, String email, String fullName, String image, String password,
			String phoneNumber, int status, int role_id, Cv cv) {
		this.address = address;
		this.description = description;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.roleId = role_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		if (password != null) {
			return password.substring(6);
		} else {
			return password;
		}
	}

	public void setPassword(String password) {
		this.password = "{noop}" + password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Cv getCv() {
		return cv;
	}

	public void setCv(Cv cv) {
		this.cv = cv;
	}

	public List<Recruitment> getSavedJobs() {
		return savedJobs;
	}

	public void setSavedJobs(List<Recruitment> savedJobs) {
		this.savedJobs = savedJobs;
	}

	public List<Company> getFollowings() {
		return followings;
	}

	public void setFollowings(List<Company> followings) {
		this.followings = followings;
	}

	public List<ApplyPost> getAppliedJobs() {
		return appliedJobs;
	}

	public void setAppliedJobs(List<ApplyPost> appliedJobs) {
		this.appliedJobs = appliedJobs;
	}

}
