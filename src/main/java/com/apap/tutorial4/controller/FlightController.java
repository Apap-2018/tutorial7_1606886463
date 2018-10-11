package com.apap.tutorial4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		
		List<FlightModel> list =  new ArrayList<FlightModel>();
		
		list.add(flight);
		pilot.setPilotFlight(list);
		flight.setPilotFlight(pilot);
		
		model.addAttribute("pilot", pilot);
		model.addAttribute("flight", flight);
		model.addAttribute("title","Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST,params= {"flightSubmit"})
	private String addFlightSubmit(@PathVariable(value="licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot,Model model) {
		PilotModel pilotFlight = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		for (FlightModel flight: pilot.getPilotFlight()) {
			flight.setPilotFlight(pilotFlight);
			flightService.addFlight(flight);
		}
		model.addAttribute("title","Add Flight");
		return "add";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST,params= {"addRow"})
	private String addFlightAdd(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
		if (pilot.getPilotFlight()==null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		model.addAttribute("title","Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST,params= {"removeRow"})
	private String addFlightRemove(@ModelAttribute PilotModel pilot,BindingResult bindingResult, Model model, HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	    pilot.getPilotFlight().remove(rowId.intValue());
		model.addAttribute("pilot" , pilot);
		model.addAttribute("title","Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
    private String updateFlight(@PathVariable(value = "id") Long id, Model model) {
        FlightModel flight = flightService.flightById(id);
        model.addAttribute("flight", flight);
		model.addAttribute("title","Update Flight");
        return "updateFlight";
    }

    @RequestMapping(value = "/flight/update/{id}", method = RequestMethod.POST)
    private String updateSubmitFlight(@PathVariable(value = "id") Long id, @ModelAttribute FlightModel flight,Model model) {
        flight.setId(id);
        flightService.updateFlight(flight);
		model.addAttribute("title","Update Flight");
        return "updated";
    }
    
    @RequestMapping(value= "/flight/delete/{id}")
    private String deletePilotSubmit(@PathVariable(value="id") long id,Model model) {
    	flightService.removeFlight(id);
		model.addAttribute("title","Remove Flight");
    	return "deleted";
    }
    
    @RequestMapping(value= "/flight/view/all")
    private String viewAll(Model model) {
    	List<FlightModel> flightList = flightService.getAllFlight();
    	model.addAttribute("flight",flightList);
		model.addAttribute("title","Flights");
    	return "viewall";
    }
	
	
}
