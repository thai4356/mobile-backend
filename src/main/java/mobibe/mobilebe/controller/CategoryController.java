package mobibe.mobilebe.controller;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.service.category.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import mobibe.mobilebe.entity.category.Category;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.findAllActive());
    }

    // @PostMapping
    // public ResponseEntity<Category> create(@RequestBody Category category) {
    // return ResponseEntity.ok(categoryService.create(category));
    // }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(categoryService.search(keyword));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Category> update(
    // @PathVariable Integer id,
    // @RequestBody Category category) {
    // return ResponseEntity.ok(categoryService.update(id, category));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable Integer id) {
    // categoryService.delete(id);
    // return ResponseEntity.noContent().build();
    // }
}
