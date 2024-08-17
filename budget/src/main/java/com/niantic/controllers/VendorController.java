package com.niantic.controllers;

import com.niantic.models.Vendor;
import com.niantic.services.VendorDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class VendorController {
    private VendorDao vendorDao = new VendorDao();


    @GetMapping("/vendor")
    public String getAllVendors(Model model)
    {
        ArrayList<Vendor> vendors = vendorDao.getAllVendors();

        model.addAttribute("vendors", vendors);
        model.addAttribute("pageTitle", "All Vendors");

        return "vendors/index";
    }

    @GetMapping("/vendor/add")
    public String addVendor(Model model)
    {
        model.addAttribute("vendor", new Vendor());
        model.addAttribute("action", "add");
        model.addAttribute("pageTitle", "Add Vendor");

        return "vendors/add_edit";
    }

    @PostMapping("/vendor/add")
    public String addVendor(@ModelAttribute("vendor") Vendor vendor)
    {
        vendorDao.addVendor(vendor);

        return "redirect:/vendor";
    }

    @GetMapping("/vendor/{id}/edit")
    public String editVendor(Model model, @PathVariable int id)
    {
        Vendor vendor = vendorDao.getVendorById(id);
        model.addAttribute("vendor", vendor);
        model.addAttribute("action", "edit");
        model.addAttribute("pageTitle", String.format("Edit Vendor - %s", vendor.getVendorName()));

        return "vendors/add_edit";

    }

    @PostMapping("/vendor/{id}/edit")
    public String editVendor(@ModelAttribute("vendor") Vendor vendor, @PathVariable int id)
    {
        vendor.setVendorId(id);
        vendorDao.updateVendor(vendor);

        return "redirect:/vendor";

    }





}
