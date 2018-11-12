package com.bookexchange.servlets.events;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookexchange.mongodb.model.Event;
import com.bookexchange.util.JsonParser;
import com.bookexchange.util.MiscUtil;


/**
 * Servlet implementation class Events
 */
@WebServlet("/app/events")
public class Events extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String baseAPI = "https://data.opendatasoft.com/api/records/1.0/search/?dataset=evenements-publics-cibul%40datailedefrance";
	private static final String fieldsAPI = "&sort=-date_start&facet=tags&facet=placename&facet=department&facet=region&facet=city&facet=date_start&facet=date_end&facet=pricing_info&facet=updated_at&facet=city_district&refine.tags=lecture";
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Events() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String OpenDataAPI = System.getenv("OpenDataSoft");
		if (OpenDataAPI == null) OpenDataAPI = MiscUtil.getValue ("OpenDataSoft");
		String query = "&q=date_end>=" + MiscUtil.nowToString() + "&apikey=" + OpenDataAPI;
		
		String json = MiscUtil.getJSONString("", "", baseAPI+query, fieldsAPI, "apikey", "OpenDataSoft");
		
		ArrayList<Event> events = JsonParser.toEvents(json);

        request.setAttribute("events", events);
        request.getRequestDispatcher("/events/events.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
