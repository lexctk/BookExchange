package com.bookexchange.servlets.events;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.bookexchange.mongodb.model.Event;
import com.bookexchange.mongodb.model.User;
import com.bookexchange.mongodb.util.MongoConnection;
import com.bookexchange.mongodb.util.MongoUtil;
import com.bookexchange.util.JsonParser;
import com.bookexchange.util.MiscUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


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
		
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		HttpSession session = request.getSession(false);
		User currentUser = MongoUtil.getCurrentUser(session, database);
		
		MongoCollection<Document> collection = database.getCollection("events");
		
		for (Event event: events) {
			Document findEvent = collection.find(eq("eventId", event.getUid())).first();
			if (findEvent != null) {
				// found event, get users attending it
				ArrayList<User> users = new ArrayList<User>();
				@SuppressWarnings("unchecked")
				List<String> basicDBList = (List<String>) findEvent.get("userIds");
				for(String userId: basicDBList) {
					users.add(MongoUtil.getOneUser(userId, database));
				}
				event.setUsers(users);
			}
		}

        request.setAttribute("events", events);
        request.setAttribute("currentUser", currentUser);
        request.getRequestDispatcher("/events/events.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String eventId = request.getParameter("eventId");
		
		HttpSession session = request.getSession(false);
		MongoConnection mongo = MongoConnection.getInstance();
		MongoDatabase database = mongo.database;
		
		User currentUser = MongoUtil.getCurrentUser(session, database);
		
		if (currentUser.getEventList() == null || !currentUser.getEventList().contains(eventId)) {
			// Adding event to user's list
			MongoUtil.addEventToUser(eventId, currentUser, database);
			
			// Add user to event collection, and create if doesn't exist
			MongoCollection<Document> collection = database.getCollection("events");
			Document findEvent = collection.find(eq("eventId", eventId)).first();
			
			if (findEvent == null) {
				// Event not in database, insert it
				Document doc = new Document("eventId", eventId)
						.append("userIds", Arrays.asList(currentUser.getIdString()));
				collection.insertOne(doc);
			} else {
				// Event already in database, update the user list
	        	collection.updateOne(
	        			eq("eventId", eventId),
	        			new Document("$push", new Document("userIds", currentUser.getIdString())));
			}
		}
		
		doGet(request, response);
	}
}
