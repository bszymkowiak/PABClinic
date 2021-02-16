package com.PabClinic.components.controllers;
import com.PabClinic.components.repositories.DoctorRepository;
import com.PabClinic.components.services.DoctorService;
import com.PabClinic.components.services.ResearchService;
import com.PabClinic.model.daos.DoctorDAO;
import com.PabClinic.components.repositories.ResearchRepository;
import com.PabClinic.model.dtos.DoctorDTO;
import com.PabClinic.model.dtos.ResearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private DoctorDTO singleDoctor;

    private ResearchService researchService;

    private DoctorService doctorService;



    @Autowired
    public AdminController(ResearchService researchService, DoctorService doctorService) {
        this.researchService = researchService;
        this.doctorService = doctorService;
    }

    @GetMapping("/researchList")
    public String toResearchList(Model model) {

        model.addAttribute("noweBadanie", new ResearchDTO());
        model.addAttribute("researchList", researchService.getAllResearches());

        return "researchList";
    }

    @GetMapping("/doctorEdit")
    public String toDoctorEdit(Model model) {

        model.addAttribute("doctor", singleDoctor);

        return "doctorEdit";
    }

    @PostMapping(value = "/doctors", params = "addDoctor")
    public String addDoctor(@ModelAttribute DoctorDTO doctorDTO) {

        doctorService.registerDoctor(doctorDTO);

        return "redirect:/doctors";
    }

    @PostMapping(value = "/doctors", params = "deleteDoctor")
    public String removeDoctor(@ModelAttribute DoctorDTO doctorDTO) {

        doctorService.removeDoctor(doctorDTO);

        return "redirect:/doctors";
    }

    @PostMapping(value = "/doctors", params = "editDoctor")
    public String editDoctor(@ModelAttribute DoctorDTO doctorDTO) {

        singleDoctor = doctorService.findDoctor(doctorDTO);

        return "redirect:/doctorEdit";
    }

    @PostMapping(value = "/doctors", params = "saveDoctor")
    public String saveDoctor(@ModelAttribute DoctorDTO doctorDTO) {

       doctorService.editDoctor(doctorDTO);

        return "redirect:/doctors";
    }




}