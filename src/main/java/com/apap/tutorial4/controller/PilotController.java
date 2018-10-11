package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.repository.PilotDB;
import com.apap.tutorial4.service.PilotService;

@Controller
public class PilotController {
@Autowired
private PilotService pilotService;


@RequestMapping("/")
private String home(Model model) {
	model.addAttribute("title", "Home");
	return "home";
}

@RequestMapping(value = "/pilot/add", method= RequestMethod.GET)
private String add(Model model) {
	model.addAttribute("title", "Add Pilot");
	model.addAttribute("pilot",new PilotModel());
	return "addPilot";
}

@RequestMapping(value= "/pilot/add", method= RequestMethod.POST)
private String addPilotSubmit(@ModelAttribute PilotModel pilot,Model model) {
	model.addAttribute("title", "Add Pilot");
	pilotService.addPilot(pilot);
	return "add";
}

@RequestMapping(value= "/pilot/update/{licenseNumber}", method= RequestMethod.GET)
private String updatePilot(@PathVariable(value="licenseNumber") String licenseNumber, Model model) {
	model.addAttribute("title", "Update Pilot");
	PilotModel upd = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
	model.addAttribute("pilot",upd);
	return "updatePilot";
}

@RequestMapping(value= "/pilot/update/{licenseNumber}", method= RequestMethod.POST)
private String updatePilotSubmit(@PathVariable(value="licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot,Model model) {
		model.addAttribute("title", "Update Pilot");
		pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).setName(pilot.getName());
		pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).setFlyHour(pilot.getFlyHour());
		System.out.println(pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).getFlyHour());
		System.out.println(pilot.getFlyHour());
	return "updated";
}

@RequestMapping(value= "/pilot/delete/{id}")
private String deletePilotSubmit(@PathVariable(value="id") long id,Model model) {
	model.addAttribute("title", "Delete Pilot");
	pilotService.removePilot(id);
	return "deleted";
}

@RequestMapping(value= "/pilot/view", method= RequestMethod.GET)
private String addPilotSubmit(@RequestParam(value = "licenseNumber") String licenseNumber, Model model) {
	PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
	model.addAttribute("title", "Pilot: "+pilot.getName());
	model.addAttribute("pilot",pilot);
	return "viewPilot";
}

}