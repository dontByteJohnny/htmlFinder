# TestTask for AgileEngine

This application is built on top of [Jsoup](https://jsoup.org/).

This application works as an element finder on a HTML file.
The user should supply 2 parameters, the path to the HTML file and the keyword to be found on it.
This keyword could be and ID, CLASS or TEXT on HTML file.
If the element is found a new file will be created with the xpath to the element found.

To run this application:

1- Download htmlkeyfinder.jar  
2- Open command line and go to the path where you downloaded the previous .jar  
3- Run it as: Java -jar htmlkeyfinder.jar <path to the .html> <keyword>  
  example: java -jar ae-backend-xml-java-snippets.main.jar  C:/Java/apimanager/sample-0-origin.html btn btn-success
  
