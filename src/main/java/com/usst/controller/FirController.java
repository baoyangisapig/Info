package com.usst.controller;

import com.usst.dao.CountDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 包杨 on 2020/02/09.
 */
@Controller
@RequestMapping(value="/demo")

@SessionAttributes(value={"username"})
public class FirController {



    @RequestMapping("logindemo")
    public  ModelAndView LoginDemo(ModelAndView mv)
    {
        mv.setViewName("welcomepage/page");
        return mv;
    }
    @RequestMapping("get")
    public  ModelAndView getFileName(@RequestParam(value = "file")String file,ModelAndView mv) throws IOException {
        System.out.println(file);
        new Tool().upload("C:\\Users\\DELL\\IdeaProjects\\pdp_git\\PDP_5\\SpringMvc1\\src\\main\\java\\com\\usst\\cfc\\"+file);
        mv.setViewName("welcomepage/page");
        System.out.println(Tool.resMap.size());
        return mv;
    }
    @RequestMapping("search")
    public ModelAndView SearchDemo(ModelAndView mv, @RequestParam(value="id")String word) throws Exception
    {
        mv.setViewName("welcomepage/out");
       if (Tool.resMap.size()==0||!Tool.resMap.containsKey(word)){
           mv.addObject("username","no such word");
           return mv;
       }
       mv.addObject("word",word);
        mv.addObject("username", Tool.resMap.get(word).toString());
        return mv;
    }



}
