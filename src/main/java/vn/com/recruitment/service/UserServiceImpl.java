package vn.com.recruitment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.UserRepository;
import vn.com.recruitment.entities.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(User theUser) {
		userRepository.save(theUser);
	}
	
	@Override
	public User findById(int id) {
		Optional<User> result = userRepository.findById(id);
		
		User theUser = null;
		
		if (result.isPresent()) {
			theUser = result.get();
		}
		else {
			// we didn't find the user
			throw new RuntimeException("Did not find user id - " + id);
		}
		
		return theUser;

	}
	
	public int findIdByUsername(String username) {
		
		Optional<Integer> userId = userRepository.searchIdByUsername(username);
		
		if (userId.isPresent()) {
			return (int) userId.get();
		} else {
			throw new RuntimeException("Did not find username - " + username);
		}
		
	}
}
