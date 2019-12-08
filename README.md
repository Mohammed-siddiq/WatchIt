# WATCH-IT

### Try your watch before you buy your watch!

An Ecommerce platform for watches where your watch selection is driven by image search/image recognition and Augemented Reality. Just show us how your watch looks and we will do the rest for you and you will try your watch before you buy your watch!


## WEB SCRAPER

- The scraper was written in python which scrapes the watchbase
website for watch details and their
images.
- The scraper checkpointed the data as and when required to the
local machine (through pickle files) to avoid re-requests to
website once visited.
- The data captured form the website was stored into a csv format.
So that it can be easily loaded into data frames and further data
processing/analysis could be done
- The images were persisted on the machine with their path
updated and linked to their respective information in the csv.

##  BACKEND
- The backend is primarily focused on interacting with the data
store (which is MongoDb) and the Prediction service and performing any operation on the platform.
- The backend is written using spring MVC in Java.
- Exposes APIs to interact with any downstream system. It can be a
native app(android or ios) or react native app or a webapp.
- The code is completely decoupled with the service layer and data
interaction layer.
- This decoupling is very important because any iterative changes
on the backend system can be easily done. Like for example changing the db from mongo to let’s say MySQL would not require much of an effort.
- This is achieved using the repository pattern, which is popular for systems that require incremental changes.
- Maintains and exposes the data to the outside world through REST apis. While also Ensuring the integrity of the data.
- Keeps the backend decoupled so that any changes are easily implemented and tested in the production

## AR SYSTEM
-  AR system is native android implementation for having you try the different watches on your wrist through your app.
- Scans image, to track the wrist 
- On the wrist places the  3d models of the watches, where user can select different models of the watches and try them on their wrist.

## FRONTEND
- Web app written in html/css and javascript.
- Allows the user to upload a watch image and displays the results of similar watches 
- Interacts with the backend and responds to user requests.
