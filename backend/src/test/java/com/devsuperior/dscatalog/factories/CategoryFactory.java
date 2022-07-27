package com.devsuperior.dscatalog.factories;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entities.Category;

public class CategoryFactory {

    public static Category createCategory() {
        return new Category(1L, "Automóveis");
    }

    public static CategoryDto createCategoryDto() {
        Category category = createCategory();
        return new CategoryDto(category);
    }
}
