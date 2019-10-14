package ru.mifodiy67.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mifodiy67.project.dao.ItemDao;

import java.util.List;

@Controller
public class ParsingController {

    @PostMapping(value = "/test")
    public @ResponseBody
    List<Integer> getItemIdWithCurrentColor(@RequestParam String box, @RequestParam String color) throws ClassNotFoundException {
        ItemDao itemDao = new ItemDao();
        int boxId = Integer.parseInt(box);
        return itemDao.getItemsInBoxWithColor(color, boxId);
    }
}
