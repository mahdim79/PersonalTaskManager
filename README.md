# TaskManager
[![MIT License](https://img.shields.io/badge/Version-1.0.0-green)](https://choosealicense.com/licenses/mit/)

A personal task manager app that allows users to add, update, and delete tasks with deadlines.
## Major Highlights

- CleanMVVM Architecture
- Kotlin
- Java
- Hilt
- Coroutines
- Flow
- Retrofit
- Room
- Work Manager
## The Architecture
The architecture of this app, called cleanMvvm, generally consists of 5 parts:

- domain
- data
- usecase
- presentation
- framework

The application has a separate module that includes the data, domain, and usecase layers.

The rest of the layers (presentation,framework) are in the main module.

- About CleanMvvm Layers:
  Domain: The Domain Layer ensures business logic remains independent of data sources.

Data: The Data layer is responsible for handling data from external sources , such as databases and web services. this layer contains two packages: DataSources and Repositories.

Usecase: This layer is responsible for interacting between domain entities and external layers like presentation.

Presentation: Consists of user interface elements, including views, widgets, and user inputs. it's responsible for managing and handling user interactions.

Framework: Includes all implementations related to local database and API communications.
## The app has following packages

- di: It contains all the dependency injection related classes and interfaces.
- presentation: View classes along with their corresponding ViewModel.
- utils: Utility classes.
- framework: contains room and retrofit implementations.
- ...
## How to use

After installing the app, you can add a new task by clicking the + button. To create each task, you need to enter a title and description for that task. You also specify a time for that task so that it will notify you at the same time every day.

As soon as you enter the app, server-side tasks are received and saved. Also, new tasks are received from the server in the background every 15 minutes.

You can also update or remove any task you want.
## Other details

This application uses CleanMvvm because:

- Each layer is responsible for its own logic, improving code maintainability
- Reusability
- Ability to easily test each layer in isolation


I used hilt for dependency injection because of its ease of use and simpler setup.

I decided to use Retrofit for network requests due to its simplicity and flexibility, combined with Gson for JSON parsing.

I used Kotlin Coroutines for background tasks because of its simplicity, scalability, and the ability to handle concurrency in a more readable manner.
## API Reference

#### Get all Tasks

```http
  GET https://run.mocky.io/v3/e168946a-1dce-4e3d-823b-bc47f0c640a0
```

Return a list containing all tasks.

## Features

- Add, update and remove tasks
- Automatically set ararm for tasks
- Background Synchronization
- Light/dark mode
## Authors

- [@mahdim79](https://www.github.com/mahdim79)

