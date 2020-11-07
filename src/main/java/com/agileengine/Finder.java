package com.agileengine;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Finder {

    private static Logger LOGGER = LoggerFactory.getLogger(Finder.class);

    public static Optional<List<String>> findElementById(Document doc, String targetElementId) {

            Element targetElement = doc.getElementById(targetElementId);

            if (targetElement != null) {
                return Optional.of(dataPrinter(targetElement));
            }

            return Optional.empty();

    }

    public static Optional<List<String>> findElementByCssOrText(Document doc, String targetElementId) {
        // select by class
        Elements targetElement = doc.select("a."+targetElementId);
        targetElement = targetElement.isEmpty() ? doc.select("a."+targetElementId.replaceAll(" ", ".")) : targetElement;
        // if nothing has found by css class, it is going to try by text
        if (targetElement.isEmpty())
            targetElement = doc.select("a:contains("+targetElementId+")");

        if(targetElement != null) {
            List<String> data = new ArrayList<>();
            targetElement.forEach(element -> {
                data.addAll((dataPrinter(element)));
            });

            return Optional.of(data);
        }
        return Optional.empty();
    }

    private static List<String> dataPrinter(Element element) {
        //StringBuilder absPath = new StringBuilder();
        //absPath.append(element.tag());

        String strPath = "";

        Elements parents = element.parents();
        for (Element parentElement:
                parents) {
            strPath = parentElement.tagName() + "[" + parentElement.siblingIndex() + "]" + " > " + strPath;
            //absPath.insert(0, "[" + parentElement.siblingIndex() + "]");
            //absPath.insert(0, parentElement.tag() + " > ");
        }

        System.out.println(strPath);

        Optional<String> stringifiedAttributesOpt = Optional.of(element).map(button ->
                button.attributes().asList().stream()
                        .map(attr -> attr.getKey() + " = " + attr.getValue())
                        .collect(Collectors.joining(", "))
        );

        stringifiedAttributesOpt.ifPresent(attrs -> System.out.println(" Target element attrs: [" + attrs + "]"));

        return Arrays.asList(strPath, stringifiedAttributesOpt.toString());
    }

}
