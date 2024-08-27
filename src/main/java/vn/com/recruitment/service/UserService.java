package vn.com.recruitment.service;


import vn.com.recruitment.entities.User;

public interface UserService {

	void save(User theUser);

	User findById(int id);

	int findIdByUsername(String username);

}
