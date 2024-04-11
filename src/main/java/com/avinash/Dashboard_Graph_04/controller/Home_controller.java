package com.avinash.Dashboard_Graph_04.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avinash.Dashboard_Graph_04.dto.DoctorCountByDate;
import com.avinash.Dashboard_Graph_04.dto.PatientCountByDate;
import com.avinash.Dashboard_Graph_04.model.Hospital_model;
import com.avinash.Dashboard_Graph_04.service.Doctor_service;
import com.avinash.Dashboard_Graph_04.service.Hospital_service;
import com.avinash.Dashboard_Graph_04.service.Patient_service;

@Controller
public class Home_controller {

	@Autowired
	private Patient_service patientService;

	@Autowired
	private Doctor_service doctorService;

	@Autowired
	private Hospital_service hospitalService;

	@GetMapping("/")
	public String index(Model model) {
		// Fetch and add hospitals to the model
		List<Hospital_model> hospitals = hospitalService.getAllHospitals();
		model.addAttribute("hospitals", hospitals);

//        // Fetch and add patients to the model
//        List<Patient_model> patients = patientService.getAllPatients();
//        model.addAttribute("patients", patients);
//
//        // Fetch and add doctors to the model
//        List<Doctor_model> doctors = doctorService.getAllDoctors();
//        model.addAttribute("doctors", doctors);

		return "index";
	}

	@GetMapping("/api/patients/count-by-date")
	public ResponseEntity<List<PatientCountByDate>> getPatientCountByDateFiltered(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<PatientCountByDate> data;
		if (startDate != null && endDate != null) {
			data = patientService.getPatientCountByDateFiltered(startDate, endDate);
		} else {
			data = patientService.getPatientCountByDate();
		}
		return ResponseEntity.ok(data);
	}

	// Similar endpoint for doctor data (replace Patient with Doctor)
	@GetMapping("/api/doctors/count-by-date")
	public ResponseEntity<List<DoctorCountByDate>> getDoctorCountByDateFiltered(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<DoctorCountByDate> data;
		if (startDate != null && endDate != null) {
			data = doctorService.getDoctorCountByDateFiltered(startDate, endDate);
		} else {
			data = doctorService.getDoctorCountByDate();
		}
		return ResponseEntity.ok(data);
	}

	@PostMapping("/process-data")
	public String processData(Model model,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
			@RequestParam(required = true) String mode) {
		// Fetch and filter data based on mode, date range, and hospital
		if ("Patient".equals(mode)) {
			List<PatientCountByDate> patientData = patientService.getPatientCountByDateFiltered(startDate, endDate);
			model.addAttribute("patientData", patientData);
		} else if ("Doctor".equals(mode)) {
			List<DoctorCountByDate> doctorData = doctorService.getDoctorCountByDateFiltered(startDate, endDate);
			model.addAttribute("doctorData", doctorData);
		}
		List<Hospital_model> hospitals = hospitalService.getAllHospitals();
		model.addAttribute("hospitals", hospitals);

		// Add the selected mode, dates, and hospital to the model (to maintain form
		// state)
		model.addAttribute("selectedStartDate", startDate);
		model.addAttribute("selectedEndDate", endDate);
		model.addAttribute("selectedMode", mode);

		return "index";

	}

}
