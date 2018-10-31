package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.rest.PilotDetail;
import com.apap.tutorial4.rest.Setting;
import com.apap.tutorial4.service.PilotService;

@RestController
@RequestMapping("/pilot")
public class PilotController {
	
@Autowired
private PilotService pilotService;

@Autowired
RestTemplate restTemplate;

@Bean
public RestTemplate rest() {
	return new RestTemplate();
}

@GetMapping(value= "/status/{licenseNumber}")
public String getStatus(@PathVariable("licenseNumber") String licenseNumber) {
	String path = Setting.pilotUrl+"/pilot?licenseNumber="+ licenseNumber;
	return restTemplate.getForEntity(path, String.class).getBody();
}

@GetMapping(value= "/full/{licenseNumber}")
public PilotDetail postStatus(@PathVariable("licenseNumber") String licenseNumber) {
	String path = Setting.pilotUrl+"/pilot";
	PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
	PilotDetail detail = restTemplate.postForObject(path, pilot, PilotDetail.class);
	return detail;
}


@PostMapping(value= "/add")
public PilotModel addPilotSubmit(@RequestBody PilotModel pilot) {
	return pilotService.addPilot(pilot);
}

@PutMapping(value= "/update/{pilotId}")
private String updatePilotSubmit(@PathVariable(value="pilotId") long pilotId,@RequestParam("name") String name, @RequestParam("flyHour") int flyHour) {
		PilotModel pilot = pilotService.getPilotDetailById(pilotId);
		if (pilot.equals(null)) {
			return "Couldn't find your pilot";
		}
		pilot.setName(name);
		pilot.setFlyHour(flyHour);
		pilotService.updatePilot(pilot);

	return "update";
}

@DeleteMapping(value= "/delete")
public String deletePilotSubmit(@RequestParam("pilotId") long id) {
	pilotService.removePilot(id);
	return "success";
}

@GetMapping(value= "/view/{licenseNumber}")
public PilotModel addPilotSubmit(@PathVariable("licenseNumber") String licenseNumber) {
	PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
	return pilot;
}

}