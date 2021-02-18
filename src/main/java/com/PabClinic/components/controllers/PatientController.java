package com.PabClinic.components.controllers;

import com.PabClinic.components.services.DoctorService;
import com.PabClinic.components.services.VisitService;
import com.PabClinic.model.dtos.*;
import com.PabClinic.model.daos.DoctorDAO;
import com.PabClinic.model.daos.PatientDAO;
import com.PabClinic.components.services.PatientService;
import com.PabClinic.model.daos.VisitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    private PatientDTO singlePatient;

    private PatientService patientService;

    private VisitDTO singleVisit;

    private DoctorDTO singleDoctor;

    private VisitService visitService;

    private DoctorService doctorService;

    private UserLoginDTO userLoginDTO;


    @Autowired
    public PatientController(PatientService patientService, VisitService visitService, DoctorService doctorService, UserLoginDTO userLoginDTO) {
        this.patientService = patientService;
        this.visitService = visitService;
        this.doctorService = doctorService;
        this.userLoginDTO = userLoginDTO;
    }

    @GetMapping("/registration")
    public String toRegistration(Model model) {

        model.addAttribute("patient", new PatientDTO());

        return "registration";
    }

    @GetMapping("/login")
    public String toLogin(Model model) {

        model.addAttribute("patientLogin", userLoginDTO);

        return "login";
    }

    @GetMapping("/contact")
    public String toContact(Model model) {

        model.addAttribute("patientContact", new ClientContactDTO());

        return "contact";
    }

    @GetMapping("/testKalendarz")
    public String toTestKalendarz(Model model) {

        model.addAttribute("visitTime", visitService.getVisitsTime());
        model.addAttribute(singleVisit);

        return "testKalendarz";
    }

    @GetMapping("/kalendarz")
    public String toKalendarz(Model model) {

        model.addAttribute("patient", singlePatient);

        model.addAttribute("visit", new VisitDTO());

        model.addAttribute("doctors", doctorService.getAllDoctors());

        model.addAttribute("doctorForm", new DoctorDTO());


        return "kalendarz";
    }



    @GetMapping("/patients")
    public String toPatients(Model model) {

        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("patient", new PatientDTO());

        return "patients";
    }

    @GetMapping("/patientEdit")
    public String toPatientEdit(Model model) {

        model.addAttribute("patient", singlePatient);

        return "patientEdit";
    }

    @GetMapping("/patientHistory")
    public String toPatientHistory(Model model) {

        model.addAttribute("patientVisits", visitService.findPatientVisits());

        return "patientHistory";
    }

    @PostMapping("/registration")
    public String afterRegistration(Model model, @ModelAttribute PatientDTO patient) {

        patientService.registerPatient(patient);

        return "redirect:/login";
    }

    @PostMapping(value = "/kalendarz", params = "saveDate")
    public String saveDate(Model model, @ModelAttribute DoctorDAO doctor,
                           @ModelAttribute VisitDAO visit) {

        // do dodania formularz rejestracji na wizyte

        return "redirect:/testKalendarz";
    }

    @PostMapping(value = "/patients", params = "addPatient")
    public String addPatient(@ModelAttribute PatientDTO patient) {

        patientService.registerPatient(patient);

        return "redirect:/patients";
    }

    @PostMapping(value = "/patients", params = "editPatient")
    public String editPatient(@ModelAttribute PatientDTO patient) {

        singlePatient = patientService.findPatientFromDb(patient);

        return "redirect:/patientEdit";
    }

    @PostMapping(value = "/patients", params = "savePatient")
    public String savePatient(@ModelAttribute PatientDTO patientDTO) {

        patientService.editPatient(patientDTO);

        return "redirect:/patients";
    }

    @PostMapping(value = "/patients", params = "deletePatient")
    public String removePatient(@ModelAttribute PatientDTO patient) {

        patientService.removePatient(patient);

        return "redirect:/patients";
    }
}
