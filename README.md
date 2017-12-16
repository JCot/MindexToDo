# MindexToDo

Simple REST ToDo app.

In a command prompt navigate to the folder where the repo is stored and run `mvn clean package`. This will create a .jar you can run.
Alternativley run `./mvnw spring-boot:run`. This will start the app right away.

App runs at `localhost:8080/toDoList/*`

Endpoints
GET: `/getItem`
Params: String `title`

POST: `/addItem`
Params: String 'title', String 'desc', String `dueDate` (format `MM/dd/yyyy/hh:mm:ss`)

PATCH: `/updateItem`
Params: String 'title', String 'desc', String `dueDate` (format `MM/dd/yyyy/hh:mm:ss`)
