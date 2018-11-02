package com.bookexchange.servlets.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MockHttpServletResponse extends HttpServletResponseWrapper {
	
	private final StringWriter stringWriter = new StringWriter();
	
	public MockHttpServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(stringWriter);
	}
	
	public String getOutput() {
	    return stringWriter.toString();
	}
}
