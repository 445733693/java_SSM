package com.how2java.tmall.controller;

import com.how2java.tmall.comparator.*;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs",cs);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String foreregister(User u, Model model){
        String name = u.getName();
        name=HtmlUtils.htmlEscape(name);
        u.setName(name);
        Boolean exist = userService.isExist(name);
        if(exist==true){
            model.addAttribute("msg","用户名已存在");
            model.addAttribute("user",null);
            return "fore/register";
        }else{
            userService.add(u);
            return "redirect:registerSuccessPage";
        }
    }

    @RequestMapping("forelogin")
    public String forelogin(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session){
        name=HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);
        if(user==null){
            model.addAttribute("msg","账号密码错误");
            model.addAttribute("user",null);
            return "redirect:loginPage";
        }
        session.setAttribute("user",user);
        return "redirect:forehome";
    }

    @RequestMapping("forelogout")
    public String forelogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String foreproduct(Integer pid,Model model){
        Product p = productService.get(pid);
        List<Productimage> productSingleImages = productImageService.list(pid, ProductImageService.TYPE_SINGLE);
        List<Productimage> productDetailImages = productImageService.list(pid, ProductImageService.TYPE_DETAIL);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        List<Review> reviews = reviewService.list(pid);
        List<Propertyvalue> pvs = propertyValueService.list(pid);
        model.addAttribute("p",p);
        model.addAttribute("reviews",reviews);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "fail";
        }
        return "success";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session){
        name=HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);
        if(user==null){
            return "fail";
        }
        session.setAttribute("user",user);
        return "success";
    }

    @RequestMapping("forecategory")
    public String category(Integer cid,String sort,Model model){
        Category c = categoryService.get(cid);
        productService.fill(c);
        List<Product> ps = c.getProducts();
        productService.setSaleAndReviewNumber(ps);
        if (sort != null) {
            switch (sort) {
                case "all":
                    ps.sort(new ProductAllComparator());
                    break;
                case "date":
                    ps.sort(new ProductDateComparator());
                    break;
                case "price":
                    ps.sort(new ProductPriceComparator());
                    break;
                case "review":
                    ps.sort(new ProductReviewComparator());
                    break;
                case "saleCount":
                    ps.sort(new ProductSaleCountComparator());
                    break;
            }
        }
        model.addAttribute("c",c);
        return "fore/category";
    }

    @RequestMapping("foresearch")
    public String search(String keyword,Model model){
        List<Product> ps = productService.search(keyword);
        productService.setSaleAndReviewNumber(ps);
        model.addAttribute("ps",ps);
        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String buyone(Integer pid,Integer num,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer oiid=0;
        List<Orderitem> orderitems = orderItemService.listByUser(user.getId());
        for (Orderitem oi : orderitems) {
            if(pid.equals(oi.getPid())){
                Integer number = oi.getNumber();
                oi.setNumber(num+number);
                orderItemService.update(oi);
                oiid=oi.getId();
                return "redirect:forebuy?oiid="+oiid;
            }
        }

        Orderitem oi = new Orderitem();
        oi.setNumber(num);
        oi.setPid(pid);
        oi.setUid(user.getId());
        orderItemService.add(oi);
        oiid=oi.getId();
        return "redirect:forebuy?oiid="+oiid;
    }

    @RequestMapping("forebuy")
    public String buy(Integer[] oiid, Model model,HttpSession session){
        List<Orderitem> ois=new ArrayList<>();
        float total=0;
        for(Integer id:oiid){
            Orderitem oi = orderItemService.get(id);
            ois.add(oi);
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
        }
        session.setAttribute("ois",ois);//为两个页面传数据：结算，提交订单
        model.addAttribute("total",total);
        return "fore/buy";
    }

    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(Integer pid,Integer num,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer oiid=0;
        List<Orderitem> orderitems = orderItemService.listByUser(user.getId());
        for (Orderitem oi : orderitems) {
            if(pid.equals(oi.getPid())){
                Integer number = oi.getNumber();
                oi.setNumber(num+number);
                orderItemService.update(oi);
                oiid=oi.getId();
                return "success";
            }
        }

        Orderitem oi = new Orderitem();
        oi.setNumber(num);
        oi.setPid(pid);
        oi.setUid(user.getId());
        orderItemService.add(oi);
        oiid=oi.getId();
        return "success";
    }

    @RequestMapping("forecart")
    public String cart(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        List<Orderitem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois",ois);
        return "fore/cart";
    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(Integer pid,Integer number ,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "fail";
        }
        List<Orderitem> ois = orderItemService.listByUser(user.getId());
        for (Orderitem oi : ois) {
            if(pid.equals(oi.getPid())){
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(Integer oiid ,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "fail";
        }
        orderItemService.delete(oiid);
        return "success";
    }

    @RequestMapping("forecreateOrder")
    public String creatOrder(Order order,Model model,HttpSession session){
        List<Orderitem> ois = (List<Orderitem>) session.getAttribute("ois");
        User user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        order.setStatus(OrderService.waitPay);
        String orderCode=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+ RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setUid(uid);
        order.setCreateDate(new Date());
        Float total=orderService.add(order,ois);
        return "redirect:forealipay?oid="+order.getId()+"&total="+total;
    }

    @RequestMapping("forepayed")
    public String forepayed(Integer oid,Model model){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitDelivery);
        o.setPayDate(new Date());
        orderService.update(o);
        model.addAttribute("o",o);
        return "fore/payed";
    }

    @RequestMapping("forebought")
    public String bought(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Order> os = orderService.list(user.getId(), OrderService.delete);
        model.addAttribute("os",os);
        return "fore/bought";

    }

    @RequestMapping("foreconfirmPay")
    public String confirmPay(Integer oid,Model model){
        Order o = orderService.get(oid);
        model.addAttribute("o",o);
        return "fore/confirmPay";
    }

    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(Integer oid){
        Order o = orderService.get(oid);
        o.setConfirmDate(new Date());
        o.setStatus(OrderService.waitReview);
        orderService.update(o);
        return "fore/orderConfirmed";
    }

    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Integer oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return "success";
    }

    @RequestMapping("forereview")
    public String review(Integer oid, Model model){
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        List<Orderitem> ois = o.getOrderItems();
        Product p = ois.get(0).getProduct();
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("p",p);
        model.addAttribute("o",o);
        List<Review> reviews = reviewService.list(p.getId());
        model.addAttribute("reviews",reviews);
        return "fore/review";
    }

    @RequestMapping("foredoreview")
    public String doreview(Integer oid,Integer pid,String content,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        Review review = new Review();
        content= HtmlUtils.htmlEscape(content);
        review.setContent(content);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        review.setPid(pid);
        reviewService.add(review);

        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        return "redirect:forereview?oid="+o.getId()+"&showonly=true";
    }
}
