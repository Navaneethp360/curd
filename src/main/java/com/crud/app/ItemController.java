package com.crud.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public String listItems(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "item/list"; // Thymeleaf template for listing items
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Item());
        return "item/create"; // Thymeleaf template for creating a new item
    }

    @PostMapping
    public String createItem(@ModelAttribute Item item) {
        itemService.save(item);
        return "redirect:/items"; // Redirect to the list of items
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("item", item);
        return "item/edit"; // Thymeleaf template for editing an item
    }

    @PostMapping("/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute Item item) {
        item.setId(id);
        itemService.save(item);
        return "redirect:/items"; // Redirect to the list of items
    }

    @GetMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
        return "redirect:/items"; // Redirect to the list of items
    }
}
