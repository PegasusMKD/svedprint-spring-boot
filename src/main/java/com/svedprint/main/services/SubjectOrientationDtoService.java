package com.svedprint.main.services;

import com.svedprint.main.dtos.SubjectOrientationDto;
import com.svedprint.main.dtos.YearDto;
import com.svedprint.main.exceptions.SvedPrintException;
import com.svedprint.main.exceptions.SvedPrintExceptionType;
import com.svedprint.main.mappers.SubjectOrientationMapper;
import com.svedprint.main.models.SubjectOrientation;
import com.svedprint.main.models.Teacher;
import com.svedprint.main.models.Year;
import com.svedprint.main.repositories.StudentRepository;
import com.svedprint.main.repositories.SubjectOrientationRepository;
import com.svedprint.main.repositories.YearRepository;
import com.svedprint.main.services.decorators.SubjectOrientationDtoDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class SubjectOrientationDtoService {

    @Autowired
    private SubjectOrientationRepository subjectOrientationRepository;

    @Autowired
    private SubjectOrientationMapper subjectOrientationMapper;

    @Autowired
    private TeacherDtoService teacherDtoService;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public SubjectOrientationDto save(SubjectOrientationDto subjectOrientationDto, String token, boolean update) {
        if (subjectOrientationDto == null) {
            return null;
        }
        if (!update && subjectOrientationDto.getShortName() == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
        }

        final Teacher teacher = teacherDtoService.findEntityByToken(token);
        List<SubjectOrientation> subjectOrientations = subjectOrientationRepository.findAllByShortNameAndYear(subjectOrientationDto.getShortName(), teacher.getSchoolClass().getYear());
        SubjectOrientation subjectOrientation = !update ? !subjectOrientations.isEmpty() ? subjectOrientations.get(0) : new SubjectOrientation() :
                subjectOrientationRepository.findByShortNameAndClasses(subjectOrientationDto.getShortName(), teacher.getSchoolClass());

        if (subjectOrientation == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
        } else if (!update && teacher.getSchoolClass().getSubjectOrientations().stream().map(SubjectOrientation::getShortName).collect(Collectors.toList()).contains(subjectOrientation.getShortName())) {
            throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCIONALITY);
        }

        if (subjectOrientation.getId() != null && !teacher.getSchoolClass().getSubjectOrientations().stream().map(SubjectOrientation::getShortName).collect(Collectors.toList()).contains(subjectOrientation.getShortName())) {
            subjectOrientation = clone(subjectOrientation);
            if (subjectOrientationDto.getSubjects() == null || subjectOrientationDto.getSubjects().isEmpty()) {
                subjectOrientationDto.setSubjects(subjectOrientation.getSubjects());
            }
        }
        subjectOrientation.setYear(teacher.getSchoolClass().getYear());

        subjectOrientation.setClasses(teacher.getSchoolClass());
        subjectOrientationDto.setClasses(null);
        if (subjectOrientation.getId() == null) {
            subjectOrientation.setStudents(new ArrayList<>());
        }


        SubjectOrientationDtoDecorator decorator = SubjectOrientationDtoDecorator.builder().build();
        subjectOrientationMapper.decorate(subjectOrientationDto, decorator);
        subjectOrientationMapper.updateEntity(decorator.init(subjectOrientation, update), subjectOrientation);
        return subjectOrientationMapper.toDto(subjectOrientationRepository.save(subjectOrientation));
    }

    private SubjectOrientation clone(SubjectOrientation source) {
        SubjectOrientationDto dto = subjectOrientationMapper.toDto(source);
        dto.setId(null);
        return subjectOrientationMapper.toEntity(dto);
    }

    @Transactional
    public SubjectOrientationDto oldSave(SubjectOrientationDto subjectOrientationDto, boolean update) {
        if (subjectOrientationDto == null) {
            return null;
        }

        final SubjectOrientation subjectOrientation = subjectOrientationDto.isIdSet() ? subjectOrientationRepository.getOne(subjectOrientationDto.getId()) : new SubjectOrientation();

        String yearId = ofNullable(subjectOrientationDto.getYear()).map(YearDto::getId).orElse(ofNullable(subjectOrientation.getYear()).map(Year::getId).orElse(null));
        if (yearId == null) {
            throw new SvedPrintException(SvedPrintExceptionType.NO_YEAR_PROVIDED);
        } else {
            subjectOrientation.setYear(yearRepository.getOne(yearId));
        }

        if (subjectOrientation.getId() == null) {
            subjectOrientation.setStudents(new ArrayList<>());
        } else {
            subjectOrientation.setStudents(null);
            subjectOrientation.setClasses(null);
        }

        SubjectOrientationDtoDecorator decorator = SubjectOrientationDtoDecorator.builder().build();
        subjectOrientationMapper.decorate(subjectOrientationDto, decorator);
        subjectOrientationMapper.updateEntity(decorator.init(subjectOrientation, update), subjectOrientation);
        return subjectOrientationMapper.toDto(subjectOrientationRepository.save(subjectOrientation));
    }


    @Transactional(readOnly = true)
    public List<SubjectOrientationDto> get(String token) {
        return teacherDtoService.findEntityByToken(token).getSchoolClass().getSubjectOrientations().stream()
                .map(subjectOrientation -> subjectOrientationMapper.toDto(subjectOrientation)).collect(Collectors.toList());
    }


    @Transactional
    public boolean delete(SubjectOrientationDto subjectOrientationDto, String token) {
        try {
            Teacher teacher = teacherDtoService.findEntityByToken(token);
            if (!subjectOrientationDto.isIdSet()) {
                throw new SvedPrintException(SvedPrintExceptionType.NO_ORIENTATION_PROVIDED);
            }
            SubjectOrientation subjectOrientation = subjectOrientationRepository.getOne(subjectOrientationDto.getId());
            if (teacher.getSchoolClass().getSubjectOrientations().contains(subjectOrientation)) {
                subjectOrientationRepository.delete(subjectOrientation);
            } else {
                throw new SvedPrintException(SvedPrintExceptionType.UNSUPPORTED_FUNCIONALITY);
            }
            return true;
        } catch (Exception e) {
            // TODO: Add logger here
            return false;
        }
    }
}
