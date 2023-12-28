package com.edu.fersko.smartcalc.models;


import com.edu.fersko.smartcalc.exceptions.NativeCalculationException;
import com.edu.fersko.smartcalc.models.dataType.GraphData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.edu.fersko.smartcalc.models.NativeLibraryLoader.getLibraryPath;

@Component
@Slf4j
public class SmartCalcJNIWrapper {

	static {
		try {
			String libraryPath = getLibraryPath(true);
			System.load(libraryPath);
		} catch (
				UnsatisfiedLinkError e) {
			log.error(e.getMessage());
		}
	}

	public native double getResult(String str, double x) throws NativeCalculationException;

	public native void clean();

	public native GraphData graphBuilder(double[] data, String expression);
}