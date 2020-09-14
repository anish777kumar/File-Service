# Getting Started

Start the Application 
----------------------

FileServiceApplication --> Run as Java Application



Please visit , "http://localhost:8080/swagger-ui.html" for  Api documentation

Services Provided
-----------------

1) GET : http://localhost:8080/file-utils/{idOrNameOfFile}

This will download the file ,

It takes header("username") as parameter to check that you have access to files , anyway its optional , so you will be able to download all files uploaded as public

2) POST : http://localhost:8080/file-utils

This will upload/create a file
It takes header("username") as parameter to decide whether the file being uploaded is public or private , for private provide your username else you can skip this header and the file will be marked as public
It as takes "file"  as body which basically is the file we want to upload

3) DELETE : http://localhost:8080/file-utils/{idOrNameOfFile}
This will delete the file

It takes header("username")  as parameter to check the authority , it is mandatory to provide username