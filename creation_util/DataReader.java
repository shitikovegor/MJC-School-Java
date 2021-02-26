package com.epam.esm.creation_util;

import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReader {
    private DataReader() {
    }

    public static List<String> readFile(String fileName) throws IOException, UncheckedIOException {
        List<String> text = new ArrayList<>();
        Path path = Paths.get(fileName);

        if (Files.exists(path) && !Files.isDirectory(path) && Files.isReadable(path)) {
            Stream<String> dataStream = Files.lines(path);
            text = dataStream.collect(Collectors.toList());
        }
        return text;
    }

    public static void writeCertificateNamesFile(List<String> sentences) throws IOException {
        Path path = Files.createFile(Paths.get("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
                "Certificates System/for_generate_bd/name.txt"));

        List<String> names = new ArrayList<>();
        sentences.forEach(sentence -> {
            int count = RandomUtils.nextInt(2,5);
            names.add(Arrays.stream(sentence.split(" "))
                    .limit(count)
                    .collect(Collectors.joining(" ")));
        });

        Files.write(path, names);
    }

    public static void writeEmailFile(List<String> logins) throws IOException {
        Path path = Files.createFile(Paths.get("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
                "Certificates System/for_generate_bd/email.txt"));

        List<String> domen = List.of("@epam.com", "@mail.ru", "@gmail.com", "@tut.by");
        List<Character> delim = List.of('_', '.');

        List<String> emails = new ArrayList<>();
        logins.forEach(login -> {
            int countDomen = RandomUtils.nextInt(0, 4);
            int countDelim = RandomUtils.nextInt(0, 2);
            String[] loginAr = login.split(" ");
            String email = loginAr[0] + delim.get(countDelim) + loginAr[1] + domen.get(countDomen);
            emails.add(email);
        });

        Files.write(path, emails);
    }

//    public static void main(String[] args) throws IOException {
//        DataReader dataReader = new DataReader();
//        List<String> sentences = dataReader.readFile("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
//                "Certificates System/for_generate_bd/description.txt");
//        dataReader.writeCertificateNamesFile(sentences);
//
//        List<String> emails = dataReader.readFile("C:/Users/shitikov_egor/IdeaProjects/MJCS/Gift " +
//                "Certificates System/for_generate_bd/namesUsers.txt");
//        dataReader.writeEmailFile(emails);
//    }
}
