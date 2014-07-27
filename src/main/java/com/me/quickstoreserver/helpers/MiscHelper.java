package com.me.quickstoreserver.helpers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MiscHelper {
	
	private final static Logger logger = Logger.getLogger(MiscHelper.class
			.getName());

	public String requestDeFlater(HttpServletRequest request) {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = request.getReader();
			while ((body = bufferedReader.readLine()) != null) {
				stringBuilder.append(body);
			}
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		 return null;
		} 
	finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					logger.error(ex.getMessage());
				}
			}
		}

		body = stringBuilder.toString();
		logger.info("Printing out the request body");
		logger.info(body);
		return body;
	}
}
