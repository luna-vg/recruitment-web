package vn.com.recruitment.controller;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.com.recruitment.entities.ApplyPost;
import vn.com.recruitment.entities.Category;
import vn.com.recruitment.entities.Company;
import vn.com.recruitment.entities.Cv;
import vn.com.recruitment.entities.Recruitment;
import vn.com.recruitment.entities.User;
import vn.com.recruitment.service.ApplyPostService;
import vn.com.recruitment.service.CategoryService;
import vn.com.recruitment.service.CompanyService;
import vn.com.recruitment.service.CvService;
import vn.com.recruitment.service.FileUploadUtil;
import vn.com.recruitment.service.RecruitmentService;
import vn.com.recruitment.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private RecruitmentService recruitmentService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CvService cvService;
	
	@Autowired
	private ApplyPostService applyPostService;
	
	public User getSessionUser() {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		int userId = userService.findIdByUsername(username);
		
		User user = userService.findById(userId);
		
		return user;

	}
		
	@GetMapping("/")
	public String showHomePage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			
			List<Category> commonCategories = categoryService.getMostChosenCategories();
			
			model.addAttribute("commonCategories", commonCategories);
			
			List<Recruitment> commonRecruitments = recruitmentService.getMostCommonRecruitments();
			
			model.addAttribute("commonRecruitments", commonRecruitments);
			
			List<Company> popularCompanies = companyService.getMostPopularCompanies();
			
			model.addAttribute("popularCompanies", popularCompanies);

			return "vtd/home-page";

		}
		
		return "redirect:/home";
		
	}
	
	@GetMapping("/home")
	public String showLoggedInHome(Model model) {
		
		User user = getSessionUser();
						
		model.addAttribute("user", user);
		
		if (user.getRoleId() == 1) {
			model.addAttribute("company", companyService.findByUserId(user.getId()));
		}
				
		List<Category> commonCategories = categoryService.getMostChosenCategories();
		
		model.addAttribute("commonCategories", commonCategories);
		
		List<Recruitment> commonRecruitments = recruitmentService.getMostCommonRecruitments();
		
		model.addAttribute("commonRecruitments", commonRecruitments);
		
		List<Company> popularCompanies = companyService.getMostPopularCompanies();
		
		model.addAttribute("popularCompanies", popularCompanies);
		
		return "vtd/home";
		
	}
	
	@PostMapping("/user-apply-job")
	public String applyJob(@RequestParam("recruitmentId") int recruitmentId, @RequestParam("type") int type, @RequestParam("file") MultipartFile multipartFile, @RequestParam("text") String text, Model theModel) throws IOException {
				
		User user = getSessionUser();

		Recruitment recruitment = recruitmentService.findById(recruitmentId);
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
						
		if (type==1) {
			
			ApplyPost newApplyPost = new ApplyPost(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()), recruitment, user, user.getCv().getFileName(), 1, text);
			
			applyPostService.saveApplyPost(newApplyPost);
			
			String copyPathCv = "src/main/resources/static/downloads/recruitment-" + recruitmentId + "/user-" + user.getId();
			
			String sourcePathCv = "src/main/resources/static/downloads/default-cv/user-" + user.getId();

			FileUploadUtil.copyFile(sourcePathCv, copyPathCv, user.getCv().getFileName());
		
		} else if (type==2 && !fileName.isBlank()) {
			
			ApplyPost newApplyPost = new ApplyPost(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()), recruitment, user, fileName, 1, text);
		
			applyPostService.saveApplyPost(newApplyPost);
			
			String uploadDir = "src/main/resources/static/downloads/recruitment-" + recruitmentId + "/user-" + user.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		}
				
		return "redirect:/home";
					
	}
	
	@GetMapping("/profile")
	public String showProfile(Model model, @RequestParam("userId") int theId) {
		
		// get the user from the service
		User user = userService.findById(theId);
		
		// set user as a model attribute to pre-populate the form
		model.addAttribute("user", user);
		
		// if user is an employer, add company information to the model
		if (user.getRoleId() == 1) {
			Company company = companyService.findByUserId(user.getId());
			model.addAttribute("companyInformation", company);
		}
		
		// send over to our profile-update page
		return "vtd/profile";
		
	}
	
	@PostMapping("/update-company")
	public String updateCompany(@ModelAttribute("companyInformation") Company company, Model model) {
		
		User user = getSessionUser();
		
		company.setUser(user);
		
		company.setStatus(1);
		
		companyService.saveCompany(company);
		
		model.addAttribute("saved", true);
		
		return "redirect:/home";
	}
	
	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute("user") User user) {
		
		User theUser = getSessionUser();
		
		user.setCv(theUser.getCv());
				
		userService.save(user);
		
		return "redirect:/home";

	}
	
	@PostMapping("/userCv-process")
	public String processUserCv(@RequestParam("cvFile") MultipartFile multipartFile, Model theModel) throws IOException {
				
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		if (fileName.isBlank()) {
						
			User user = getSessionUser();
			
			if (user.getRoleId() == 1) {
				theModel.addAttribute("company", companyService.findByUserId(user.getId()));
			}
			
			theModel.addAttribute("user", user);
			
			theModel.addAttribute("noFile", true);
			
			return "vtd/profile";

		}
		
		User user = getSessionUser();

		Cv theCv = new Cv(fileName);
		
		user.setCv(theCv);
		
		userService.save(user);
						
		String uploadDir = "src/main/resources/static/downloads/default-cv/user-" + user.getId();
		
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		return "redirect:/profile?userId=" + user.getId();
		
	}
	
	@PostMapping("/deleteCv")
	public String deleteCv(@RequestParam("idCv") int cvId) {
		
		Cv theCv = cvService.findById(cvId);
		
		User user = getSessionUser();
		
		user.setCv(null);
				
		cvService.deleteCv(theCv);
				
		return "redirect:/profile?userId=" + user.getId();
		
	}
	
	@GetMapping("/post-list")
	public String getPostJobs(@RequestParam("companyId") int companyId, Model theModel) {
		
		User user = getSessionUser();
		
		theModel.addAttribute("user", user);
		
		Company company = companyService.findById(companyId);
		
		company.setUser(user);
				
		theModel.addAttribute("company", company);
		
		return "vtd/post-list";
		
	}
	
	@GetMapping("/view-post")
	public String viewPost(@RequestParam("recruitmentId") int recruitmentId, Model theModel) {
		
		User user = getSessionUser();
		
		theModel.addAttribute("user", user);
		
		Recruitment theRecruitment = recruitmentService.findById(recruitmentId);
		
		theModel.addAttribute("recruitment", theRecruitment);
		
		Company theCompany = theRecruitment.getCompany();
		
		theModel.addAttribute("company", theCompany);
		
		return "vtd/view-post";
		
	}
	
	@GetMapping("/edit-post")
	public String editPost(@RequestParam("recruitmentId") int recruitmentId, Model theModel) {
		
		User user = getSessionUser();
		
		theModel.addAttribute("user", user);
		
		Recruitment theRecruitment = recruitmentService.findById(recruitmentId);
		
		theModel.addAttribute("recruitment", theRecruitment);
		
		List<Category> categories = categoryService.findAllCategories();
		
		theModel.addAttribute("categories", categories);
		
		Company theCompany = theRecruitment.getCompany();
		
		theModel.addAttribute("company", theCompany);
		
		return "vtd/edit-post";
		
	}
	
	@GetMapping("/add-post")
	public String addPost(@RequestParam("companyId") int companyId, Model theModel) {
		
		Company theCompany = companyService.findById(companyId);
		
		theModel.addAttribute("company", theCompany);
		
		Recruitment recruitment = new Recruitment();
		
		recruitment.setCompany(theCompany);
		
		theModel.addAttribute("recruitment", recruitment);
		
		List<Category> categories = categoryService.findAllCategories();
		
		theModel.addAttribute("categories", categories);
		
		return "vtd/add-post";
		
	}
	
	@PostMapping("/save-post")
	public String savePost(@ModelAttribute("recruitment") Recruitment theRecruitment, @RequestParam("categoryId") int categoryId) {
				
		User user = getSessionUser();
		
		Company theCompany = companyService.findByUserId(user.getId());
		
		theRecruitment.setCompany(theCompany);
		
		theRecruitment.setStatus(1);
		
		Category category = categoryService.findById(categoryId);
		
		if (theRecruitment.getId() != 0) {
			
			Category oldCategory = recruitmentService.findById(theRecruitment.getId()).getCategory();
			
			if (oldCategory != category) {
				category.setNumberChoose(category.getNumberChoose()+1);
				oldCategory.setNumberChoose(oldCategory.getNumberChoose()-1);
			}
		
		} else {
			category.setNumberChoose(category.getNumberChoose()+1);			
		}
		
		theRecruitment.setCategory(category);
		
		recruitmentService.saveRecruitment(theRecruitment);
		
		return "redirect:/post-list?companyId=" + theCompany.getId();
		
	}
	
	@GetMapping("/recruitment-delete")
	public String deleteRecruitment(@RequestParam("id") int recruitmentId) {
		
		User user = getSessionUser();
		
		Company theCompany = companyService.findByUserId(user.getId());
		
		Recruitment theRecruitment = recruitmentService.findById(recruitmentId);
		
		theRecruitment.getCategory().setNumberChoose(theRecruitment.getCategory().getNumberChoose() - 1);
		
		recruitmentService.deleteRecruitment(recruitmentId);
		
		return "redirect:/post-list?companyId=" + theCompany.getId();

	}
	
	@GetMapping("/list-candidates")
	public String listCandidates(@RequestParam("companyId") int companyId, Model theModel) {
		
		Company theCompany = companyService.findById(companyId);
		
		List<Recruitment> recruitments = theCompany.getRecruitments();
		
		List<ApplyPost> applyPosts = new ArrayList<>();
		
		if (recruitments != null) {
			for(Recruitment recruitment : recruitments) {
				applyPosts.addAll(recruitment.getApplyPosts());
			}
		}
		
		theModel.addAttribute("applyPosts", applyPosts);
		
		return "vtd/list-candidates";
		
	}
	
	@PostMapping("/result-search-job")
	public String searchJob(@RequestParam("keySearch") String keySearch, Model theModel) {
		
		User user = getSessionUser();
		
		if (user.getRoleId() == 1) {
			theModel.addAttribute("company", companyService.findByUserId(user.getId()));
		}
		
		theModel.addAttribute("user", user);
		
		List<Recruitment> theRecruitments = recruitmentService.findRecruitmentsByKeySearch(keySearch);
		
		theModel.addAttribute("recruitments", theRecruitments);
		
		theModel.addAttribute("keySearch", keySearch);
		
		return "vtd/result-search-job";
		
	}

	@PostMapping("/result-search-company")
	public String searchCompany(@RequestParam("keySearch") String keySearch, Model theModel) {
		
		User user = getSessionUser();
		
		if (user.getRoleId() == 1) {
			theModel.addAttribute("company", companyService.findByUserId(user.getId()));
		}
		
		theModel.addAttribute("user", user);
		
		List<Recruitment> theRecruitments = recruitmentService.findRecruitmentsByCompanyKeySearch(keySearch);
		
		theModel.addAttribute("recruitments", theRecruitments);
		
		theModel.addAttribute("keySearch", keySearch);
				
		return "vtd/result-search-job";
		
	}

	@PostMapping("/result-search-address")
	public String searchAddress(@RequestParam("keySearch") String keySearch, Model theModel) {
		
		User user = getSessionUser();
		
		if (user.getRoleId() == 1) {
			theModel.addAttribute("company", companyService.findByUserId(user.getId()));
		}
		
		theModel.addAttribute("user", user);
		
		List<Recruitment> theRecruitments = recruitmentService.findRecruitmentsByAddressKeySearch(keySearch);
		
		theModel.addAttribute("recruitments", theRecruitments);
		
		theModel.addAttribute("keySearch", keySearch);
		
		return "vtd/result-search-job";
		
	}

}
