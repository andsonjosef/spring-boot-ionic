package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Category;
import com.example.cursomc.domain.Product;
import com.example.cursomc.repositories.CategoryRepository;
import com.example.cursomc.repositories.ProductRepository;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Product find(Integer id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + Product.class.getName()));
	}

	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids); 
		return repo.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);

	}
}
