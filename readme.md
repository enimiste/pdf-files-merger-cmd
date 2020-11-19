#PDF Files Merger
##Requirements :
- Java 11 installed  
You can check your java by opening your command line and type `java -v`, you should see in the output `java 11`  

##Usage
`java -jar pdf-files-merger.jar ./files ./out.pdf`
##Docs
```
private static void showHelp() {
        System.out.println("Usage :");
        System.out.println("java -jar program.jar  dirPath outFilePath");
        System.out.println("dirPath : path to the directory containing PDF files to be merged in one PDF file");
        System.out.println("outFilePath : path to the file on which the merged PDFs will be saved. Should ends with .pdf suffix");
        System.out.println("Thanks !");
    }
```

## Credits
Enimiste