package com.projectstats;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.projectstats.Walker.getConstants;

abstract class Window {
    private static final List<String> TYPE_VARIABLES = new ArrayList<>() {{
        add("int");
        add("boolean");
        add("String");
        add("long");
    }};
    private String dir;

    Window(String dir) {
        this.dir = dir;
    }

    void show(Walker walker) {
        this.dir = walker.getDirectory();
        List<String> constants = getConstants();
        add("name: ", new File(walker.getDirectory()).getName());
        add("size: ", Unit.getSize(walker.getSize()));
        add("files: ", walker.getFiles());
        add("folders: ", walker.getFolders());
        add("lines total: ", walker.getLines());
        add("empty lines: ", walker.getEmptyLines());
        add("constants: ", constants.toString());
        add("type of variables: ", TYPE_VARIABLES.toString());
        add("not empty lines: ", walker.getNotEmptyLines());
        add("skipped files: ", walker.getSkippedFiles());
        if (!Main.no_ext) {
            add("extensions: ", walker.getExtensions());
        }
    }

    abstract void add(String name, String text);

    void add(String name, int text) {
        add(name, String.valueOf(text));
    }

    void add(String name, long text) {
        add(name, String.valueOf(text));
    }

    void add(String name, List<String[]> list) {
        add(name, "");
        list.forEach(arr -> {
            add("  ", String.format("%s (%s)", arr[0], arr[1]));
        });
    }
}
