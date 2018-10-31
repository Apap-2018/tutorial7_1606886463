package com.apap.tutorial4.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@RestController
@RequestMapping("/flight")
public class FlightController {
	@Autowired
	private FlightService flightService;
	@Autowired
	private PilotService pilotService;
	
	@PostMapping(value= "/add")
	public FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
		System.out.println("flight: "+flight.getPilotFlight());
		return flightService.addFlight(flight);
	}
	
	@PutMapping(value= "/update/{flightId}")
	private String updatePilotSubmit(@PathVariable(value="flightId") long id,@RequestParam("destination") String destination, @RequestParam("origin") String origin,@RequestParam("time") Date time) {
		 	FlightModel flight = flightService.flightById(id);
			if (flight.equals(null)) {
				return "Couldn't find your flight";
			}
			flight.setOrigin(origin);
			flight.setDestination(destination);
			flight.setTime(time);
			flightService.updateFlight(flight);

		return "flight update success";
	}
	
	@DeleteMapping(value= "/{flightId}")
	public String deletePilotSubmit(@PathVariable(value="flightId") long id) {
		flightService.removeFlight(id);
		return "flight has been deleted";
	}
	
	@GetMapping(value= "/view/{flightId}")
	public FlightModel addPilotSubmit(@PathVariable("flightId") long id) {
		FlightModel flight = flightService.flightById(id);
		return flight;
	}
	
	@GetMapping(value= "/all")
	public List<FlightModel> viewAll() {
		List<FlightModel> flight = flightService.getAllFlight();
		return flight;
	}
    
	
	
}
