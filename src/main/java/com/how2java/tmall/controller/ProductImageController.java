package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Productimage;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.impl.ProductImageServiceImpl;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;

    @RequestMapping("admin_productImage_list")
    public String list(Integer pid, Model model) {
        Product p = productService.get(pid);
        List<Productimage> pisSingle = productImageService.list(pid, "type_single");
        List<Productimage> pisDetail = productImageService.list(pid, "type_detail");
        model.addAttribute("p",p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String add(Productimage pi, String type, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        productImageService.add(pi);
        String typeFolder = null;
        String imageFolder_middle=null;
        String imageFolder_small=null;
        String filename = pi.getId() + ".jpg";
        if (productImageService.TYPE_SINGLE.equals(type)) {
            typeFolder = "productSingle";
        } else if (productImageService.TYPE_DETAIL.equals(type)) {
            typeFolder = "productDetail";
        }
        File imageFolder = new File(session.getServletContext().getRealPath("/img/" + typeFolder));
        File file = new File(imageFolder, filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file);
        ImageIO.write(image,"jpg",file);

        if (productImageService.TYPE_SINGLE.equals(type)){
            imageFolder_small=session.getServletContext().getRealPath("/img/productSingle_small");
            imageFolder_middle=session.getServletContext().getRealPath("/img/productSingle_middle");
            File f_small=new File(imageFolder_small,filename);
            File f_middle=new File(imageFolder_middle,filename);
            ImageUtil.resizeImage(file,56,56,f_small);
            ImageUtil.resizeImage(file,217,190,f_middle);
        }

        return "redirect:/admin_productImage_list?pid="+pi.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(Integer id,HttpSession session) {
        Productimage pi = productImageService.get(id);
        productImageService.delete(id);

        String type=pi.getType();
        String typeFolder=null;
        String imageFolder_middle=null;
        String imageFolder_small=null;
        String filename = pi.getId() + ".jpg";
        if (productImageService.TYPE_SINGLE.equals(type)) {
            typeFolder = "productSingle";
        } else if (productImageService.TYPE_DETAIL.equals(type)) {
            typeFolder = "productDetail";
        }
        File imageFolder = new File(session.getServletContext().getRealPath("/img/" + typeFolder));
        File file = new File(imageFolder, filename);
        if(file.exists()){
            file.delete();
        }

        if (productImageService.TYPE_SINGLE.equals(type)){
            imageFolder_small=session.getServletContext().getRealPath("/img/productSingle_small");
            imageFolder_middle=session.getServletContext().getRealPath("/img/productSingle_middle");
            File f_small=new File(imageFolder_small,filename);
            File f_middle=new File(imageFolder_middle,filename);
            if (f_small.exists()) {
                f_small.delete();
            } if (f_middle.exists()) {
                f_middle.delete();
            }
        }

        return "redirect:/admin_productImage_list?pid="+pi.getPid();
    }
}
