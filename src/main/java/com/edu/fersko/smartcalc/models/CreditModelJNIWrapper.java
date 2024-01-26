package com.edu.fersko.smartcalc.models;

import com.edu.fersko.smartcalc.models.type.CreditData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.edu.fersko.smartcalc.models.NativeLibraryLoader.getLibraryPath;

@Component
@Slf4j
public class CreditModelJNIWrapper {

	static {
		try {
			System.load(getLibraryPath(false));
		} catch (
				UnsatisfiedLinkError e) {
			log.error(e.getMessage());
		}
	}

	public native void annuity(double summa, double period, double rate);

	public native void deffirentated(double summa, double rate, double period);

	public native CreditData getResult();

}