package com.pabclinic.services;

import com.pabclinic.model.dtos.DoctorDTO;
import com.pabclinic.repositories.ResearchRepository;
import com.pabclinic.model.dtos.ResearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResearchService {

    private ResearchRepository researchRepository;

    @Autowired
    public ResearchService(ResearchRepository researchRepository) {
        this.researchRepository = researchRepository;
    }

    public List<ResearchDTO> getAllResearches(){

        return researchRepository.getResearches();

    }

    public void removeResearch(String researchName){

        researchRepository.removeResearch(researchName);

    }

    public void addResearch(ResearchDTO researchDTO) {

        researchRepository.addResearchToDb(researchDTO);

    }

    public ResearchDTO findResearchByName(String researchName) {

        return researchRepository.findResearchByNameFromDb(researchName);
    }

    public void editResearch(ResearchDTO researchDTO) {

        researchRepository.updateResearch(researchDTO);
    }
}
