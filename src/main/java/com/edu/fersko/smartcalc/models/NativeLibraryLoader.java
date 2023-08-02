package com.edu.fersko.smartcalc.models;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NativeLibraryLoader {
    public static String getLibraryPath(boolean isMain) {
        String libraryRelativePath = isMain
                ? "src/main/java/com/edu/fersko/smartcalc/models/lib/SmartCore.dll"
                : "src/main/java/com/edu/fersko/smartcalc/models/lib/CreditCore.dll";

        Path absolutePath = Paths.get("").toAbsolutePath();
        return absolutePath.resolve(libraryRelativePath).toString();
    }
}
