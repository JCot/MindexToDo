package com.justin.Demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class ToDoController {

    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh:mm:ss");

    @RequestMapping("/getAllItems")
    public ArrayList<ToDoItem> getAllItems(){
        return new ArrayList<ToDoItem>();
    }

    @RequestMapping("/getItem")
    public ToDoItem getOneItem(@RequestParam(value="title") String title){
        //TODO get from local store
        return new ToDoItem(title,"", new Date());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addItem(@RequestParam(value="title") String title,
                                  @RequestParam(value="desc") String desc,
                                  @RequestParam(value="dueDate") String dueDate){
        ToDoItem newItem = null;
        try {
            newItem = new ToDoItem(title, desc, formatter.parse(dueDate));
        }
        catch(ParseException e){
            System.out.println("Error attempting to parse date string");
        }
        //TODO put in local store/database

        if(newItem != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{title}")
                    .buildAndExpand(newItem.getTitle()).toUri();
            return ResponseEntity.created(location).build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity updateItem(@RequestParam(value="title") String title,
                           @RequestParam(value="desc", defaultValue = "") String desc,
                           @RequestParam(value="dueDate", defaultValue = "") String dueDate){
        ToDoItem item = getOneItem(title);
        if(item != null) {
            if (!desc.equals("") || !dueDate.equals("")) {
                if (!desc.equals("")) {
                    item.setDescription(desc);
                }

                if (!dueDate.equals("")) {
                    try {
                        item.setDueDate(formatter.parse(dueDate));
                    } catch (ParseException e) {
                        System.out.println("Error attempting to parse date string");
                    }
                }

                //TODO update data store

                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

}
