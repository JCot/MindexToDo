package com.justin.Demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MindexToDoListApplicationTests {

	private ToDoController controller = new ToDoController();

	@Test
	public void getValidItemShouldReturnOK() throws Exception{
	    ResponseEntity response = controller.getOneItem("Test");
	    assert response.getStatusCode() == HttpStatus.OK;
	}

	@Test
    public void addItemShouldReturnCreated() throws Exception{
	    ResponseEntity response = controller.addItem("Auto Test", "Unit Test Item", "12/25/2017/10:00:00");
	    assert response.getStatusCode() == HttpStatus.CREATED;
    }

    @Test
    public void updateBadShouldReturnNotFound() throws Exception{
	    ResponseEntity response = controller.updateItem("Bad Title", "New Description", "");
	    assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

}
