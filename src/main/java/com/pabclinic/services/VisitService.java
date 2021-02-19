package com.pabclinic.services;
import com.pabclinic.model.daos.PatientDAO;
import com.pabclinic.repositories.VisitRepository;
import com.pabclinic.model.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
public class VisitService {

    private VisitRepository visitRepository;

    private UserLoginDTO userLoginDTO;

    @Autowired
    public VisitService(VisitRepository visitRepository, UserLoginDTO userLoginDTO) {
        this.visitRepository = visitRepository;
        this.userLoginDTO = userLoginDTO;
    }

    public void addVisit(PatientDAO patientDAO, DoctorDTO doctor){

        visitRepository.addVisitHistory(LocalDate.now().toString(), doctor.getFirstName(), doctor.getLastName(),
                doctor.getLogin(), patientDAO.getFirstName(), patientDAO.getLastName(),
                patientDAO.getOpis());

    }

    public void showDoctorVisitsByDay(Model model) {

        model.addAttribute("visitList", visitRepository.findDoctorVisits());
        model.addAttribute("patient", new PatientDTO());
    }

    public List<VisitTimeDTO> getVisitsTime() {

        return visitRepository.getVisitsTime();
    }

    public DoctorDTO findDoctorFromDb(UserLoginDTO userLoginDTO) {

        return visitRepository.findDoctorFromDb(userLoginDTO);

    }

    public List<VisitDTO> findPatientVisits(){

        return visitRepository.findVisitHistory();
    }

}