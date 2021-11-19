package com.fpt.controller;

import com.fpt.dto.CategoryDTO;
import com.fpt.dto.ProductDTO;
import com.fpt.service.CategoryService;
import com.fpt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @ModelAttribute
    public void showAllCategory(Model model) {
        List<CategoryDTO> listC = categoryService.findAllCategory();
        model.addAttribute("listC", listC);
    }

    @GetMapping("")
    public ModelAndView homePages() {
        ModelAndView model = new ModelAndView("Index");
        List<ProductDTO> listP = productService.findAllProduct();
        model.addObject("listP", listP);
        return model;
    }

    @GetMapping("/category")
    public ModelAndView getProductByCategory(@RequestParam("id") int categoryId) {
        List<ProductDTO> listP = productService.findALlProductByCategoryId(categoryId);
        ModelAndView model = new ModelAndView("Index");
        model.addObject("listP", listP);
        return model;
    }

    @GetMapping("/detail")
    public ModelAndView getDetailProductById(@RequestParam("id") int id) {
        ModelAndView model = new ModelAndView("Shop-details");
        ProductDTO product = productService.findProductById(id);
        String nameOfCategory = product.getCategoryDTO().getName();
        List<ProductDTO> listProductByCategoryName = productService.findAllProductByCategoryName(nameOfCategory);
        model.addObject("product", product);
        model.addObject("listP", listProductByCategoryName);
        return model;
    }

}
