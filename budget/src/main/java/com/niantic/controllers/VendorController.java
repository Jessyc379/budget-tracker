package com.niantic.controllers;

import com.niantic.models.User;
import com.niantic.services.UserDao;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserDao userDao = new UserDao();


    @GetMapping("/user")
    public String getAllUsers(Model model)
    {
        ArrayList<User> users = userDao.getAllUsers();

        model.addAttribute("users", users);

        return "users/index";
    }

    @GetMapping("/user/add")
    public String addUser(Model model)
    {
        model.addAttribute("user", new User());
        model.addAttribute("action", "add");

        return "users/add_edit";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute("user") User user)
    {
        userDao.addUser(user);

        return "redirect:/transactions";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(Model model, @PathVariable int id)
    {
        User user = userDao.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("action", "edit");

        return "users/add_edit";

    }

    @PostMapping("/user/{id}/edit")
    public String editUser(@ModelAttribute("user") User user, @PathVariable int id)
    {
        user.setUserId(id);
        userDao.updateUser(user);

        return "redirect:/transactions";

    }





}
