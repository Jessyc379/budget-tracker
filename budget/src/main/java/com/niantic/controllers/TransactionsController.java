package com.niantic.controllers;

import com.niantic.models.Category;
import com.niantic.models.Transaction;
import com.niantic.models.User;
import com.niantic.models.Vendor;
import com.niantic.services.CategoryDao;
import com.niantic.services.TransactionDao;

import com.niantic.services.UserDao;
import com.niantic.services.VendorDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
public class TransactionsController {

//    http://localhost:8080/transactions
    private TransactionDao transactionDao = new TransactionDao();
    private CategoryDao categoryDao = new CategoryDao();
    private UserDao userDao = new UserDao();
    private VendorDao vendorDao = new VendorDao();

    @GetMapping("/transactions")
    public String getAllTransactions(Model model)
    {
        ArrayList<Transaction> transactions = transactionDao.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions/index";
    }

    @GetMapping("/transactions/add")
    public String addTransaction(Model model)
    {
        ArrayList<Category> categories = categoryDao.getAllCategories();
        ArrayList<User> users = userDao.getAllUsers();
        ArrayList<Vendor> vendors = vendorDao.getAllVendors();

        model.addAttribute("transaction", new Transaction());
        model.addAttribute("categories", categories);
        model.addAttribute("users", users);
        model.addAttribute("vendors", vendors);
        model.addAttribute("action", "add");
        return "transactions/add_edit";
    }

    @PostMapping("/transactions/add")
    public String addTransaction(@ModelAttribute("transaction") Transaction transaction)
    {
        transactionDao.addTransaction(transaction);

        return "redirect:/transactions";

    }

    @GetMapping("/transactions/{id}/edit")
    public String editTransactions(Model model, @PathVariable int id)
    {
        Transaction transaction = transactionDao.getTransactionById(id);
        ArrayList<Category> categories = categoryDao.getAllCategories();
        ArrayList<User> users = userDao.getAllUsers();
        ArrayList<Vendor> vendors = vendorDao.getAllVendors();

        model.addAttribute("categories", categories);
        model.addAttribute("users", users);
        model.addAttribute("vendors", vendors);
        model.addAttribute("transaction", transaction);
        model.addAttribute("action", "edit");

        return "transactions/add_edit";

    }

    @PostMapping("/transactions/{id}/edit")
    public String editTransactions(@ModelAttribute("transaction") Transaction transaction, @PathVariable int id)
    {
        transaction.setTransactionId(id);
        transactionDao.updateTransaction(transaction);
        return "redirect:/transactions";


    }

    @GetMapping("/transactions/{id}/delete")
    public String deleteTransactions(Model model, @PathVariable int id)
    {
        Transaction transaction = transactionDao.getTransactionById(id);

        model.addAttribute("transaction", transaction);

        return "transactions/delete";
    }

    @PostMapping("/transactions/{id}/delete")
    public String deleteTransactions(@PathVariable int id)
    {

        transactionDao.deleteTransaction(id);

        return "redirect:/transactions";
    }


}
