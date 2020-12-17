package com.example.smartApplication.controller;

import com.example.smartApplication.po.HomeFormPO;
import com.example.smartApplication.service.HomeService;
import com.example.smartApplication.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class HomeController {
    private HomeService homeService;

    @RequestMapping("/")
    public String helloHtml(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "/index";
    }

    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public Response init(){
        return homeService.init();
    }

    @RequestMapping(value = "/run",method = RequestMethod.POST)
    public Response run(@RequestBody HomeFormPO homeFormPO){
        return homeService.run(homeFormPO);
    }

    @RequestMapping(value = "/get_result",method = RequestMethod.POST)
    public Response get_result(@RequestBody HomeFormPO homeFormPO){
        return homeService.get_result(homeFormPO);
    }

    @RequestMapping(value = "/get",produces = MediaType.IMAGE_JPEG_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public BufferedImage getImage(@RequestBody HomeFormPO homeFormPO) throws IOException {
        return ImageIO.read(new FileInputStream(new File("D:\\project\\backend_WebTest\\SmartForecast/src/main/resource/img.png")));
    }











}
