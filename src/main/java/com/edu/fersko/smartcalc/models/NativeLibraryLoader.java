package com.edu.fersko.smartcalc.models;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NativeLibraryLoader {
    public static String getLibraryPath(boolean isMain) {
        String libraryName = isMain ? "SmartCore" : "CreditCore";
        String libraryExtension = System.getProperty("os.name").toLowerCase().contains("win") ? ".dll" : ".so";
        String libraryRelativePath = "src/main/java/com/edu/fersko/smartcalc/models/lib/" + libraryName + libraryExtension;

        Path absolutePath = Paths.get("").toAbsolutePath();
        return absolutePath.resolve(libraryRelativePath).toString();
    }
}