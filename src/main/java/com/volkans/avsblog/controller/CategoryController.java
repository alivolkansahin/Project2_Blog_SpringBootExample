package com.volkans.avsblog.controller;

import com.volkans.avsblog.dto.request.CategoryCreateRequestDto;
import com.volkans.avsblog.dto.request.CategoryUpdateRequestDto;
import com.volkans.avsblog.entity.Category;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.volkans.avsblog.constant.EndPoint.*;

@RestController
@RequestMapping(ROOT + CATEGORY)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(GETALL)   // Tüm kategorileri listeler
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping(GET + "/{categoryId}") // Belirli bir kategorinin detayları getirir.
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId) {
        if(categoryId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(categoryService.getCategoryById(Long.valueOf(categoryId)));
    }

    @PostMapping(CREATE) // Yeni bir kategori oluşturur.
    public ResponseEntity<Category> create(@RequestBody @Valid CategoryCreateRequestDto dto){
        return ResponseEntity.ok(categoryService.create(dto));
    }


    @PutMapping("{categoryId}") // Belirli bir kategorinin bilgilerini günceller.
    public ResponseEntity updatePostById(@PathVariable String categoryId, @RequestBody @Valid CategoryUpdateRequestDto dto){
        if(categoryId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        categoryService.updatePostById(Long.valueOf(categoryId), dto);
        return ResponseEntity.ok("Update Successful!");
    }


    @DeleteMapping(DELETE + "/{categoryId}")  // Belirli bir kategoriyi siler
    public ResponseEntity deleteCategoryById(@PathVariable String categoryId){
        if(categoryId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        categoryService.deleteCategoryById(Long.valueOf(categoryId));
        return ResponseEntity.ok("Delete Successful! (Status Changed to \"REMOVED\")");
    }

}
