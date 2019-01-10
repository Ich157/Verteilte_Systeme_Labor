package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class BeerController {
	
	private final RestRepository repository;
	
	BeerController(RestRepository repository){
		this.repository = repository;
	}
	
	@GetMapping("/beer")
	List<Beer> all(){
		return (List<Beer>) repository.findAll();
	}
	
	@PostMapping("/beer")
	Beer newBeer(@RequestBody Beer newBeer) {
		return repository.save(newBeer);
	}
	
	@GetMapping("/beer/{id}")
	Optional<Beer> one(@PathVariable Long id) {
		
		return repository.findById(id);
	}
	
	@PutMapping("/beer/{id}")
	Beer replaceBeer(@RequestBody Beer newBeer, @PathVariable Long id) {
		return repository.findById(id)
				.map(beer ->{
					beer.setName(newBeer.getName());
					beer.setAbv(newBeer.getAbv());
					return repository.save(beer);
				})
				.orElseGet(() ->{
					newBeer.setId(id);
					return repository.save(newBeer);
				});
	}
	
	@DeleteMapping("/beer/{id}")
	void deleteBeer(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
