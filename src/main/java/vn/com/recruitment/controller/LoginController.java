package vn.com.recruitment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.com.recruitment.entities.Company;
import vn.com.recruitment.entities.User;
import vn.com.recruitment.service.CompanyService;
import vn.com.recruitment.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
			
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage(Model theModel) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			
			theModel.addAttribute("user", new User());
			
			return "vtd/login";

		}
		
		return "redirect:/home";
				
	}
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute("user") User theUser, Model theModel) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			
			theUser.setStatus(1);
			
			userService.save(theUser);
			
			theModel.addAttribute("successfulRegister", true);

			return "vtd/login";

		}
		
		return "redirect:/home";
				
	}
	
}
