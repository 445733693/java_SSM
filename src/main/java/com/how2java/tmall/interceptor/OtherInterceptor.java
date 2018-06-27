package com.how2java.tmall.interceptor;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Orderitem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OtherInterceptor implements HandlerInterceptor {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        List<Category> cs = categoryService.list();
        request.setAttribute("cs",cs);

        Integer cartTotalItemNumber=0;
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            List<Orderitem> ois = orderItemService.listByUser(user.getId());
            for (Orderitem oi : ois) {
                cartTotalItemNumber+=oi.getNumber();
            }
        }
        modelAndView.addObject("cartTotalItemNumber",cartTotalItemNumber);

        String contextPath = request.getServletContext().getContextPath();
        modelAndView.addObject("contextPath",contextPath);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
