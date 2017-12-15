package com.justin.Demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/toDoList")
public class ToDoController {

    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy/hh:mm:ss");
    private ArrayList<ToDoItem> toDoList = new ArrayList<ToDoItem>();

    private ToDoItem findItemByTitle(String title){
        for(int i = 0; i < toDoList.size(); i++){
            ToDoItem item = toDoList.get(i);
            String curTitle = item.getTitle();
            if(curTitle.equals(title)){
                return item;
            }
        }

        return null;
    }

    @GetMapping("/getAllItems")
    public ArrayList<ToDoItem> getAllItems(){
        return toDoList;
    }

    @GetMapping("/getItem")
    public ResponseEntity getOneItem(@RequestParam(value="title") String title){
        //TODO get from local store
        if(toDoList.size() == 0){
            ToDoItem newItem = new ToDoItem("Test", "Test Description", new Date());
            toDoList.add(newItem);
        }
        ToDoItem item = findItemByTitle(title);
        if(item != null) {
            return ResponseEntity.ok(item);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addItem")
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

        if(newItem != null) {
            toDoList.add(newItem);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{title}")
                    .buildAndExpand(newItem.getTitle()).toUri();
            return ResponseEntity.created(location).body(newItem);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/updateItem")
    public ResponseEntity updateItem(@RequestParam(value="title") String title,
                           @RequestParam(value="desc", defaultValue = "") String desc,
                           @RequestParam(value="dueDate", defaultValue = "") String dueDate){
        ToDoItem item = findItemByTitle(title);
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

                item = findItemByTitle(title);
                return ResponseEntity.ok(item);
            }
        }

        return ResponseEntity.notFound().build();
    }

}
