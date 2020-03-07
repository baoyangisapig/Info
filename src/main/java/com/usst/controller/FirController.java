package com.usst.controller;

import com.usst.controller.service.Tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by 包杨 on 2020/02/09.
 */
@Controller
@RequestMapping(value="/demo")

@SessionAttributes(value={"username"})
public class FirController {
    /**
     * method that used to login into the website.
     *
     * @param mv
     * @return
     */
    @RequestMapping("logindemo")
    public ModelAndView LoginDemo(ModelAndView mv) {
        mv.setViewName("welcomepage/page");
        return mv;
    }

    /**
     * construct the inverted indexes by the input file path.
     *
     * @param file file path.
     * @param mv
     * @return
     * @throws IOException
     */
    @RequestMapping("get")
    public ModelAndView getFileName(@RequestParam(value = "file") String file, ModelAndView mv) throws IOException {
        System.out.println(file);
        new Tool().upload("C:\\Users\\DELL\\IdeaProjects\\pdp_git\\PDP_5\\SpringMvc1\\src\\main\\java\\com\\usst\\cfc\\" + file);
        mv.setViewName("welcomepage/page");
        return mv;
    }

    /**
     * search a specific word and return the index based on the inverted indexes.
     *
     * @param mv
     * @param word
     * @return
     * @throws Exception
     */
    @RequestMapping("search")
    public ModelAndView SearchDemo(ModelAndView mv, @RequestParam(value = "id") String word) throws Exception {
        mv.setViewName("welcomepage/out");
        if (Tool.resMap.size() == 0 || !Tool.resMap.containsKey(word)) {
            mv.addObject("username", "no such word");
            return mv;
        }
        mv.addObject("word", word);
        mv.addObject("username", Tool.resMap.get(word).toString());
        return mv;
    }
}
