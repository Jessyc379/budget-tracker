package com.niantic.controllers;

import com.niantic.models.Transaction;
import com.niantic.services.TransactionDao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
public class TransactionsController {

//    http://localhost:8080/transactions
    private TransactionDao transactionDao = new TransactionDao();

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
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("action", "add");
        return "transactions/add_edit";
    }

    @PostMapping("/transactions/add")
    public String addTransaction(@ModelAttribute("transaction") Transaction transaction)
    {
        transactionDao.addTransaction(transaction);

        return "redirect:/transactions";

    }




}
