package com.reservation.foodTable.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.foodTable.entity.Category;
import com.reservation.foodTable.repository.CategoryRepository;



@Service
@Transactional(readOnly=true)
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public List<Category> getCategory() {
		
		return (List<Category>)repo.findAll();
	}
	public Category getCategory(Integer category) {
		return repo.findById(category).get();
	}
}
