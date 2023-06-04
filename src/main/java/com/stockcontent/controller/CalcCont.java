package com.stockcontent.controller;

import com.stockcontent.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calc")
public class CalcCont extends Attributes {

    @GetMapping
    public String calc(Model model) {
        AddAttributes(model);
        return "calc";
    }

    @PostMapping
    public String calc(Model model, @RequestParam int days, @RequestParam int ads, @RequestParam int price, @RequestParam int tax) {
        model.addAttribute("answer", (days * ads * price) * (1 - ((double) tax / 100)));
        AddAttributes(model);
        return "calc";
    }
}
