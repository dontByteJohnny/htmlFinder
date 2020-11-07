package com.agileengine;

import org.apache.log4j.BasicConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class MainRunner {
    private static Logger LOGGER = LoggerFactory.getLogger(MainRunner.class);

    private static String CHARSET_NAME = "utf8";

    public static void main(String[] args) {
        BasicConfigurator.configure();

        // String resourcePath = "./samples/startbootstrap-sb-admin-2-examples/sample-0-origin.html";
        String resourcePath = args[0];

        System.out.println("This is the resource you supply to be analyzed: " + resourcePath);
        System.out.println();

        //String entryKey = "Make-Button";
        String entryKey = args[1];
        System.out.println("This is the your entry key to be found on HTML: " + entryKey + " | Consider that it could be and id, class or text in the HTML");
        System.out.println();

        try {
            File htmlFile = new File(resourcePath);
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());
            Optional<StringBuilder> stringBuilderResult;

            Optional<List<String>> htmlResult = Finder.findElementById(doc, entryKey);

            if(!htmlResult.isPresent())
                htmlResult = Finder.findElementByCssOrText(doc, entryKey);

            if(htmlResult.isPresent()) {
                Path file = Paths.get("./htmlFinderResult.txt");
                Files.write(file, htmlResult.get(), StandardCharsets.UTF_8);
                System.out.println();
                System.out.println("There was an result in the resource given with the key you have entered.");
                System.out.println("You will find the XPath in a file in the same path you are running the application with the name 'htmlFinderResult'.");
                //Files.write(file, htmlResult, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            } else {
                System.out.println("Nothing found with the parameter given");
                System.out.println();
            }
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file ", resourcePath, e);
        }

    }
}
