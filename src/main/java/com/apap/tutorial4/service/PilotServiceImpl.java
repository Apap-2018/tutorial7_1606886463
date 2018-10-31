package com.apap.tutorial4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.repository.PilotDB;

@Service
@Transactional
public class PilotServiceImpl implements PilotService {

	@Autowired
	private PilotDB pilotDb;
	
	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		return pilotDb.findByLicenseNumber(licenseNumber);
	}
	
	@Override
	public PilotModel getPilotDetailById(long id) {
		return pilotDb.findById(id);
	}
	
	@Override
	public PilotModel addPilot (PilotModel pilot) {
		pilotDb.save(pilot);
		return pilot;
	}
	
	@Override
	public void removePilot (long pilot) {
		pilotDb.deleteById(pilot);
	}
	
	@Override
	public void updatePilot(PilotModel pilot) {
	    PilotModel pilotUpdate = pilotDb.getOne(pilot.getId());
	    pilotUpdate.setFlyHour(pilot.getFlyHour());
	    pilotUpdate.setName(pilot.getName());
	    pilotDb.save(pilotUpdate);
	}


}
