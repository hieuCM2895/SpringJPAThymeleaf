package com.fpt.controller;

import com.fpt.dto.CategoryDTO;
import com.fpt.dto.ProductDTO;
import com.fpt.dto.UserDTO;
import com.fpt.model.Product;
import com.fpt.service.CategoryService;
import com.fpt.service.ProductService;
import com.fpt.service.UserService;
import com.fpt.validation.StringValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void showAllCategory(Model model) {
        List<CategoryDTO> listC = categoryService.findAllCategory();
        double minPrice = (double) productService.findRangePriceOfProduct("min");
        double maxPrice = (double) productService.findRangePriceOfProduct("max");
        model.addAttribute("listC", listC);
        model.addAttribute("minP", (int) minPrice);
        model.addAttribute("maxP", (int) maxPrice);
    }

    @GetMapping("/index")
    public ModelAndView shoppingProduct(@RequestParam(value = "page", required = false) Integer currentNumberPage) {
        ModelAndView model = new ModelAndView("Shop-grid");

        if (currentNumberPage == null) {
            currentNumberPage = 1;
        }

        List<ProductDTO> listOfProduct = productService.paginationForProduct(currentNumberPage, 12);
        long count = (long) productService.getAmountOfAllProduct();
        long totalPages = count / 12;
        if (count % 12 != 0) {
            totalPages++;
        }

        List<ProductDTO> listNewProduct = productService.findNewProduct();

        double minPrice = (double) productService.findRangePriceOfProduct("min");
        double maxPrice = (double) productService.findRangePriceOfProduct("max");

        model.addObject("totalPages", totalPages);
        model.addObject("currentPage", currentNumberPage);
        model.addObject("listP", listOfProduct);
        model.addObject("listN", listNewProduct);
        model.addObject("minP", (int) minPrice);
        model.addObject("maxP", (int) maxPrice);

        return model;
    }

    @GetMapping("/category")
    public ModelAndView shoppingProductByCategory(@RequestParam(value = "id", required = false) int id) {
        ModelAndView model = new ModelAndView("Shop-grid");

        List<ProductDTO> listOfProduct = productService.findALlProductByCategoryId(id);
        long count = listOfProduct.size();
        long totalPages = count / 12;
        if (count % 12 != 0) {
            totalPages++;
        }

        List<ProductDTO> listNewProduct = productService.findNewProduct();

        model.addObject("totalPages", totalPages);
        model.addObject("currentPage", 1);
        model.addObject("listP", listOfProduct);
        model.addObject("listN", listNewProduct);

        return model;
    }

    @GetMapping("/order")
    public ModelAndView shoppingProductByOrder(@RequestParam(value = "status", required = false) String status,
                                               @RequestParam(value = "page", required = false) Integer currentNumberPage) {
        ModelAndView model = new ModelAndView("Shop-grid");

        if (currentNumberPage == null) {
            currentNumberPage = 1;
        }

        List<ProductDTO> listOfProduct = productService.paginationForProductByOrder(currentNumberPage, 12, status);
        long count = (long) productService.getAmountOfAllProduct();
        long totalPages = count / 12;
        if (count % 12 != 0) {
            totalPages++;
        }

        List<ProductDTO> listNewProduct = productService.findNewProduct();

        model.addObject("totalPages", totalPages);
        model.addObject("currentPage", currentNumberPage);
        model.addObject("listP", listOfProduct);
        model.addObject("listN", listNewProduct);
        model.addObject("status", status);

        return model;
    }

    @PostMapping("/cart")
    public String cartForProduct(@ModelAttribute("product") ProductDTO product, HttpServletRequest req, HttpServletResponse resp) {

        String productId = String.valueOf(product.getId());
        int amount = product.getAmount();
        Cookie arr[] = req.getCookies();
        String txt = "";

        for (Cookie o : arr) {

            if (StringValidation.validationString(o.getName(), StringValidation.predicate2, o.getValue())) {

                txt = txt + o.getValue();
                o.setMaxAge(0);
                resp.addCookie(o);

            }
        }

        for (int i = 0; i < amount; i++) {
            txt = txt + "-" + productId;
        }

        Cookie c = new Cookie("cart", txt);
        c.setMaxAge(60 * 60 * 24);
        resp.addCookie(c);
        return "redirect:/shop/cart";

    }

    @GetMapping("/cart")
    public ModelAndView orderForProduct(HttpServletRequest req) {

        Cookie arr[] = req.getCookies();
        List<ProductDTO> list = new ArrayList<>();

        for (Cookie o : arr) {

            if (StringValidation.validationString(o.getName(), StringValidation.predicate2, o.getValue())) {

                String txt[] = o.getValue().split("-");

                Set<Integer> listNumber = new HashSet<>();

                for (String s : txt) {

                    if ("".equals(s)) {
                        continue;
                    }

                    if (!listNumber.contains(Integer.parseInt(s))) {
                        listNumber.add(Integer.parseInt(s));
                        list.add(productService.findProductById(Integer.parseInt(s)));
                    } else {
                        for (ProductDTO listP : list) {
                            if (listP.getId() == Integer.parseInt(s)) {
                                listP.setAmount(listP.getAmount() + 1);
                            }
                        }
                    }
                }
            }
        }

        double total = 0;
        for (ProductDTO o : list) {
            total = total + o.getAmount() * o.getPrice();
        }

        double vat = 0.1 * total;
        double sum = 1.1 * total;

        ModelAndView model = new ModelAndView("Shopping-cart");
        model.addObject("listP", list);
        model.addObject("total", total);
        model.addObject("vat", vat);
        model.addObject("sum", (long) sum);

        return model;
    }

    @GetMapping("/adjust")
    public String changeAmountOfOrder(@RequestParam("id") String productId,
                                      @RequestParam("amount") int amount,
                                      HttpServletRequest req,
                                      HttpServletResponse resp) {

        if (amount <= 0) {
            amount = 1;
        }

        Cookie arr[] = req.getCookies();
        String result = "";

        for (Cookie o : arr) {

            if (StringValidation.validationString(o.getName(), StringValidation.predicate2, o.getValue())) {

                String txt[] = o.getValue().split("-");
                o.setMaxAge(0);
                resp.addCookie(o);
                int flag = 0;
                for (String s : txt) {

                    if (!productId.equals(s)) {
                        result += s + "-";
                    }

                    if (productId.equals(s) && flag == 0) {
                        flag++;
                        for (int i = 1; i <= amount; i++) {
                            result = result + productId + "-";
                        }
                    }

                }
            }
        }

        Cookie c = new Cookie("cart", result);
        c.setMaxAge(60 * 60 * 24);
        resp.addCookie(c);
        return "redirect:/shop/cart";
    }

    @PostMapping("/delete")
    public String removeProductCart(@RequestParam("id") String id,
                                    HttpServletRequest req,
                                    HttpServletResponse resp) {

        Cookie arr[] = req.getCookies();
        String result = "";

        for (Cookie o : arr) {

            if (StringValidation.validationString(o.getName(), StringValidation.predicate2, o.getValue())) {

                String txt[] = o.getValue().split("-");
                o.setMaxAge(0);
                resp.addCookie(o);
                for (String s : txt) {
                    if (!id.equals(s)) {
                        result += s + "-";
                    }
                }
            }
        }

        Cookie c = new Cookie("cart", result);
        c.setMaxAge(60 * 60 * 24);
        resp.addCookie(c);
        return "redirect:/shop/cart";

    }

    @GetMapping("/checkout")
    public ModelAndView orderCheckOutProduct(HttpServletRequest req) {

        Cookie arr[] = req.getCookies();
        List<ProductDTO> list = new ArrayList<>();

        HttpSession session = req.getSession();
        SecurityContextImpl result = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        String username = result.getAuthentication().getName();
        UserDTO user = userService.findUserByLastName(username);

        for (Cookie o : arr) {

            if (StringValidation.validationString(o.getName(), StringValidation.predicate2, o.getValue())) {

                String txt[] = o.getValue().split("-");

                Set<Integer> listNumber = new HashSet<>();

                for (String s : txt) {

                    if ("".equals(s)) {
                        continue;
                    }

                    if (!listNumber.contains(Integer.parseInt(s))) {
                        listNumber.add(Integer.parseInt(s));
                        list.add(productService.findProductById(Integer.parseInt(s)));
                    } else {
                        for (ProductDTO listP : list) {
                            if (listP.getId() == Integer.parseInt(s)) {
                                listP.setAmount(listP.getAmount() + 1);
                            }
                        }
                    }
                }
            }
        }

        double total = 0;
        for (ProductDTO o : list) {
            total = total + o.getAmount() * o.getPrice();
        }

        double vat = 0.1 * total;
        double sum = 1.1 * total;

        ModelAndView model = new ModelAndView("Checkout");
        model.addObject("listP", list);
        model.addObject("total", total);
        model.addObject("vat", vat);
        model.addObject("sum", (long) sum);
        model.addObject("user", user);

        return model;
    }

    @PostMapping("/filter")
    public ModelAndView filterPriceBySlider(@RequestParam(value = "page", required = false) Integer currentNumberPage,
                                      @RequestParam(value = "min", required = false) String min,
                                      @RequestParam(value = "max", required = false) String max) {

        ModelAndView model = new ModelAndView("Shop-grid");

        String[] minConvert = min.substring(0, min.length() - 2).split(",");
        String minAfterConvert = "";
        for (String num : minConvert) {
            minAfterConvert += num;
        }
        int minF = Integer.parseInt(minAfterConvert);

        String[] maxConvert = max.substring(0, max.length() - 2).split(",");
        String maxAfterConvert = "";
        for (String num : maxConvert) {
            maxAfterConvert += num;
        }
        int maxF = Integer.parseInt(maxAfterConvert);

        if (currentNumberPage == null) {
            currentNumberPage = 1;
        }

        List<ProductDTO> listOfProduct = productService.paginationForProduct(currentNumberPage, 12,
                                                                        minF,
                                                                        maxF);
        long count = (Integer) productService.getAmountOfAllProductByFilterPrice(minF, maxF);
        long totalPages = count / 12;
        if (count % 12 != 0) {
            totalPages++;
        }

        List<ProductDTO> listNewProduct = productService.findNewProduct();

        model.addObject("totalPages", totalPages);
        model.addObject("currentPage", currentNumberPage);
        model.addObject("listP", listOfProduct);
        model.addObject("listN", listNewProduct);

        return model;
    }

}
