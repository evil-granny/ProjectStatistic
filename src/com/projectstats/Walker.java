package com.projectstats;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Walker {
    private long size = 0L;
    private int files = 0;
    private int folders = 0;
    private long lines = 0L;
    private long emptyLines = 0L;
    private long notEmptyLines = 0L;
    private int skipped_files = 0;
    private String dir = "";
    private static List<String> listExt = new ArrayList<>();
    private static List<String> skipDirs = new ArrayList<>(List.of(".git", "node_modules"));
    private static List<String> skipFiles = new ArrayList<>();
    private static List<String> skipExt = new ArrayList<>();
    private boolean wait;
    private boolean list;
    private boolean list_skipped;
    private Map<String, Integer> extensions;
    private static final String IS_SKIPPED = " is skipped";
    private static final String IS_NOT_SKIPPED = " is not skipped";
    private static Pattern patternForComments = Pattern.compile("//.*|/\\*((.|\\n)(?!=*/))+\\*/");

    Walker(String dir) {
        this.dir = dir;
        this.wait = Main.wait;
        this.list = Main.list;
        this.list_skipped = Main.list_skipped;
        this.extensions = new HashMap<>();
        Printer.print("scanning...");
        walk(dir);
        Printer.println();
    }

    private void walk(String dir) {
        // Local variable files hides a class field. Therefore altered the variable name
        File[] fileList = new File(dir).listFiles();

        for (final File file : fileList) {
            if (file.isDirectory()) {
                if (!skipDirs.contains(file.getName())) {
                    this.folders++;
                    walk(file.getAbsolutePath());
                } else if (list_skipped) {
                    Printer.printf("%nskipped: %s", file.getAbsolutePath());
                }
            }
            if (file.isFile()) {
                try {
                    walkInFile(file.getAbsolutePath(), file.length());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void walkInFile(String name, long size) throws IOException {
        //test comment
        long fileLines = 0;
        long fileEmptyLines = 0;
        boolean isBinary = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.isBlank())
                    fileEmptyLines++;

                Matcher matcher = patternForComments.matcher(line);
                while (matcher.find()) {
                    System.out.println("Comments: ");
                    System.out.println(matcher.group());
                }

                fileLines++;
                if (charsBinary(line)) {
                    isBinary = true;
                    break;
                }
            }
        }

        String extension = getExtension(name);

        if (isBinary || skipFiles.contains(name) || skipExt.contains(extension)) {
            this.skipped_files++;
            if (this.list_skipped) {
                Printer.printf("%nskipped: %s", name);
            }
            return;
        }

        this.lines += fileLines;
        this.emptyLines += fileEmptyLines;
        this.notEmptyLines = lines - emptyLines;
        this.files++;
        this.size += size;

        if (this.list) {
            Printer.printf("%n%s", name);
        } else if (listExt.contains(extension)) {
            Printer.printf("%n%s", name);
        } else if (this.list_skipped) {
            // don't show progressbar if 'list_skipped' is true
        } else if (this.wait) {
            Printer.print(".");
        }

        if (this.extensions.containsKey(extension)) {
            this.extensions.put(extension, this.extensions.get(extension) + 1);
        } else {
            this.extensions.put(extension, 1);
        }
    }

    private static boolean charsBinary(String line) {
        for (char c : line.toCharArray()) {
            if (c < 0x09) {
                return true;
            }
        }
        return false;
    }

    private String getExtension(String name) {
        int index = name.lastIndexOf('.');
        int p = name.lastIndexOf('/');
        String extension = "";
        if (index > p) {
            extension = name.substring(index + 1);
        } else {
            extension = "[none]";
        }
        return extension;
    }

    long getSize() {
        return this.size;
    }

    int getFiles() {
        return this.files;
    }

    int getFolders() {
        return this.folders;
    }

    long getLines() {
        return this.lines;
    }

    long getEmptyLines() {
        return this.emptyLines;
    }

    public long getNotEmptyLines() {
        return this.notEmptyLines;
    }

    int getSkippedFiles() {
        return this.skipped_files;
    }

    String getDirectory() {
        return this.dir;
    }

    List<String[]> getExtensions() {
        StringBuilder output = new StringBuilder();
        List<String[]> a = new ArrayList<>();
        this.extensions.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(e -> {
            output.append(e.getKey()).append(" -> ").append(e.getValue()).append("\n");
            a.add(new String[]{e.getKey(), String.valueOf(e.getValue())});
        });
        return a;
    }

    static void checkDir(String dir) {
        if (skipDirs.contains(dir)) {
            Printer.println(dir.concat(IS_SKIPPED));
        } else {
            Printer.println(dir.concat(IS_NOT_SKIPPED));
        }
    }

    static void checkFile(String file) {
        if (skipFiles.contains(file)) {
            Printer.println(file.concat(IS_SKIPPED));
            return;
        }

        boolean isBinary = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (charsBinary(line)) {
                    isBinary = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isBinary) {
            Printer.println(file.concat(IS_SKIPPED));
        } else {
            Printer.println(file.concat(IS_NOT_SKIPPED));
        }
    }

    static void addListExt(String ext) {
        listExt.add(ext);
    }

    static void addSkipDir(String dir) {
        skipDirs.add(dir);
    }

    static void addSkipFile(String file) {
        skipFiles.add(file);
    }

    static void addSkipExt(String ext) {
        skipExt.add(ext);
    }
}
