package com.niantic.controllers;

import com.niantic.models.Category;
import com.niantic.models.Transaction;
import com.niantic.models.User;
import com.niantic.services.CategoryDao;
import com.niantic.services.TransactionDao;
import com.niantic.services.UserDao;
import com.niantic.services.VendorDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class ReportsController {

    private TransactionDao transactionDao = new TransactionDao();
    private CategoryDao categoryDao = new CategoryDao();
    private UserDao userDao = new UserDao();
    private VendorDao vendorDao = new VendorDao();

    @GetMapping("/report/category")
    public String getAllCategories(Model model)
    {
        ArrayList<Category> categories = categoryDao.getAllCategories();

        model.addAttribute("categories", categories);

        return "reports/category_menu";
    }



    @GetMapping("/report/category/{id}")
    public String reportByCategory(Model model, @PathVariable int id)
    {
        ArrayList<Transaction> transactions = transactionDao.getTransactionByCategory(id);
        Category category = categoryDao.getCategoryById(id);

        // Making Header with String Format
        String catName = category.getCategoryName();
        String message = String.format("Report by Category: %s", catName);

        model.addAttribute("transactions", transactions);
        model.addAttribute("category", category);
        model.addAttribute("message", message);

        return "reports/reports";
    }

    @GetMapping("/report/user")
    public String getAllUsers(Model model)
    {
        ArrayList<User> users = userDao.getAllUsers();

        model.addAttribute("users", users);

        return "reports/user_menu";
    }

    @GetMapping("/report/user/{id}")
    public String reportByUser(Model model, @PathVariable int id)
    {
        ArrayList<Transaction> transactions = transactionDao.getTransactionByUser(id);
        User user = userDao.getUserById(id);

        // Making Header with String Format
        String useName = user.getUserName();
        String message = String.format("Report by User: %s", useName);

        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        model.addAttribute("message", message);

        return "reports/reports";
    }


}
