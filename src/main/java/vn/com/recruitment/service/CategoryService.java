package vn.com.recruitment.service;

import java.util.List;

import vn.com.recruitment.entities.Category;

public interface CategoryService {

	Category findById(int id);

	List<Category> getMostChosenCategories();

	List<Category> findAllCategories();

}
