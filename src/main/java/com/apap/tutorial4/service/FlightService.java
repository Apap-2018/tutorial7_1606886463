package com.apap.tutorial4.service;

import java.util.List;

import com.apap.tutorial4.model.FlightModel;

public interface FlightService {
	void addFlight(FlightModel flight);
	void updateFlight(FlightModel flightModel);
	FlightModel flightById(long id);
	void removeFlight(long id);
	List<FlightModel> getAllFlight();
}
