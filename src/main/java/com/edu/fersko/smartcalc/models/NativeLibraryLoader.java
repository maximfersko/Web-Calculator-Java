package com.edu.fersko.smartcalc.models;

import java.nio.file.Paths;

public class NativeLibraryLoader {
    static String getLibraryPath() {
        final String libraryRelativePath = "src/main/java/com/edu/fersko/smartcalc/models/lib/core.dll";
        return  Paths.get( Paths.get("").toAbsolutePath().toString(), libraryRelativePath).toString();
    }
}
