package vn.com.recruitment.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.CategoryRepository;
import vn.com.recruitment.entities.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category findById(int id) {
		Optional<Category> result = categoryRepository.findById(id);
		
		Category theCategory = null;
		
		if (result.isPresent()) {
			theCategory = result.get();
		}
		else {
			// we didn't find the user
			throw new RuntimeException("Did not find category id - " + id);
		}
		
		return theCategory;

	}
	
	@Override
	public List<Category> findAllCategories() {
		
		List<Category> categories = categoryRepository.findAll();
		
		return categories;
	}
	
	@Override
	public List<Category> getMostChosenCategories() {
		
		List<Category> theCategories = new ArrayList<>();
		
		List<Integer> mostChosenCategories = categoryRepository.mostChosenCategory();
		
		for (int i : mostChosenCategories) {
			theCategories.add(findById(i));
		}
		
		return theCategories;
	}

}
