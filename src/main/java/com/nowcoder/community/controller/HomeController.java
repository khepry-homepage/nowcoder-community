package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/home", method = RequestMethod.GET)
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private DiscussPostService discussPostService;
    @RequestMapping("/index")
    public String getIndexPage(Model model, Page page) {
        page.setTotalRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/home/index");
        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPostsList = new ArrayList<>();
        if (discussPostsList != null) {
            for (DiscussPost discussPost : discussPosts) {
                Map<String, Object> map = new HashMap<>();
                User user = userService.findUserById(discussPost.getUserId());
                map.put("post", discussPost);
                map.put("user", user);
                discussPostsList.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPostsList);
        return "index";
    }
}