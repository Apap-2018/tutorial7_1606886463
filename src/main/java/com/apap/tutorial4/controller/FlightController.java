package com.apap.tutorial4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value="licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilotFlight(pilot);
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
    private String updateFlight(@PathVariable(value = "id") Long id, Model model) {
        FlightModel flight = flightService.flightById(id);
        model.addAttribute("flight", flight);
        return "updateFlight";
    }

    @RequestMapping(value = "/flight/update/{id}", method = RequestMethod.POST)
    private String updateSubmitFlight(@PathVariable(value = "id") Long id, @ModelAttribute FlightModel flight) {
        flight.setId(id);
        flightService.updateFlight(flight);
        return "updated";
    }
    
    @RequestMapping(value= "/flight/delete/{id}")
    private String deletePilotSubmit(@PathVariable(value="id") long id) {
    	flightService.removeFlight(id);
    	return "deleted";
    }
    
    @RequestMapping(value= "/flight/view/all")
    private String viewAll(Model model) {
    	List<FlightModel> flightList = flightService.getAllFlight();
    	model.addAttribute("flight",flightList);
    	return "viewall";
    }
	
	
}
