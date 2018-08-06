package ru.timmson.gidget;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class App {

    public static void main(String[] args) throws Exception {
        log.info("Input args: " + Arrays.asList(args));

        final String inputPath = "";
        final String outputPath = "";

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File(""));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);


        Template temp = cfg.getTemplate("Test.ftlh");

        try {
            Files.walk(Paths.get(inputPath)).filter(i -> !Files.isDirectory(i)).forEach(i -> {
                try {
                    String className = i.getFileName().toString().substring(0, i.getFileName().toString().lastIndexOf('.'));
                    String testClassName = className + "Test";
                    String testFileName = testClassName + ".java";
                    String aPackage = getPackageNameByPath(i);
                    String outFile = outputPath + "\\" + aPackage.replace(".", "\\") + "\\" + testFileName;
                    if (!Files.exists(Paths.get(outFile))) {

                        Map<String, Object> root = new HashMap<>();
                        root.put("package", aPackage);
                        root.put("name", className);
                        File file  = new File(outFile);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        Writer out = new FileWriter(file);
                        log.info(root.toString());
                        temp.process(root, out);
                        out.close();
                    }
                } catch (Exception e1) {
                    log.error("Some error has occurred", e1);
                }
            });
        } catch (Exception e2) {
            log.error("Some error has occurred", e2);
        }
    }

    private static String getPackageNameByPath(Path path) throws IOException {
        return Files.lines(path).filter(line -> line.lastIndexOf("package ") == 0).collect(Collectors.toList()).
                get(0).replaceAll(";", "").replace("package ", "");
    }

}
