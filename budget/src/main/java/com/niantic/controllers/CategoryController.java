package com.niantic.controllers;

import com.niantic.models.Category;
import com.niantic.services.CategoryDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class CategoryController {
    private CategoryDao categoryDao = new CategoryDao();


    @GetMapping("/category")
    public String getAllCategories(Model model)
    {
        ArrayList<Category> categories = categoryDao.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "All Categories");

        return "categories/index";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model)
    {
        model.addAttribute("category", new Category());
        model.addAttribute("action", "add");
        model.addAttribute("pageTitle", "Add Category");

        return "categories/add_edit";
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute("category") Category category)
    {
        categoryDao.addCategory(category);

        return "redirect:/category";
    }

    @GetMapping("/category/{id}/edit")
    public String editCategory(Model model, @PathVariable int id)
    {
        Category category = categoryDao.getCategoryById(id);
        model.addAttribute("category", category);
        model.addAttribute("action", "edit");
        model.addAttribute("pageTitle", String.format("Edit Category: %s", category.getCategoryName()));

        return "categories/add_edit";

    }

    @PostMapping("/category/{id}/edit")
    public String editCategory(@ModelAttribute("category") Category category, @PathVariable int id)
    {
        category.setCategoryId(id);
        categoryDao.updateCategory(category);

        return "redirect:/category";

    }





}
