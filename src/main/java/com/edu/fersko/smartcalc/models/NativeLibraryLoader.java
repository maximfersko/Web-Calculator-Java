package com.edu.fersko.smartcalc.models;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NativeLibraryLoader {

	private static final String LIB_PATH = "src/main/java/com/edu/fersko/smartcalc/models/lib/";

    private NativeLibraryLoader() {
    }

    public static String getLibraryPath(boolean isMain) {
        String libraryName = isMain ? "SmartCore" : "CreditCore";
        String libraryExtension = System.getProperty("os.name").toLowerCase().contains("win") ? ".dll" : ".so";
	    String libraryRelativePath = LIB_PATH + libraryName + libraryExtension;

        Path absolutePath = Paths.get("").toAbsolutePath();
        return absolutePath.resolve(libraryRelativePath).toString();
    }

}