package com.svedprint.main.repositories;

import com.svedprint.main.models.SchoolClass;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.models.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectOrientationRepository extends JpaRepository<SubjectOrientation, String> {
    List<SubjectOrientation> findAllByShortNameAndYear(String shortName, Year year);

    SubjectOrientation findByShortNameAndClasses(String shortName, SchoolClass schoolClass);

    List<SubjectOrientation> findAllByClasses(SchoolClass classes);
}
