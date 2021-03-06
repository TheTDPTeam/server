package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.attendance.AttendanceOfClassDTO;
import com.tdpteam.repo.entity.Attendance;
import com.tdpteam.repo.entity.AttendanceStatus;
import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.repository.AttendanceRepository;
import com.tdpteam.repo.repository.BClassRepository;
import com.tdpteam.repo.repository.StudentRepository;
import com.tdpteam.service.exception.attendance.AttendanceNotFoundException;
import com.tdpteam.service.exception.bClass.BClassNotFoundException;
import com.tdpteam.service.interf.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceRepository attendanceRepository;
    private BClassRepository bClassRepository;
    private StudentRepository studentRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, BClassRepository bClassRepository, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.bClassRepository = bClassRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Set<Attendance> generateAttendanceSetForClass(String calendarStr, Date startDate, int numberOfLessons) {
        String[] calendarArr = calendarStr.split(",");
        List<Integer> calendarIntArr = convertCalendarStringArrToIntArr(calendarArr);
        Set<Attendance> attendanceSet = new HashSet<>();
        int currentCheckingDateIndex = getCheckingDateIndex(startDate);
        while (attendanceSet.size() < numberOfLessons) {
            Attendance attendance = new Attendance();
            if (attendanceSet.size() == 0 && calendarIntArr.contains(currentCheckingDateIndex)) {
                attendance.setCheckingDate(startDate);
            } else {
                startDate = moveToNextAvailableIndex(startDate, currentCheckingDateIndex, calendarIntArr);
                attendance.setCheckingDate(startDate);
                currentCheckingDateIndex = getCheckingDateIndex(startDate);
            }
            attendanceSet.add(attendance);
        }
        return attendanceSet;
    }

    private int getCheckingDateIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public Date moveToNextAvailableIndex(Date currentCheckingDate, int currentCheckingDateIndex, List<Integer> calendarIntArr) {
        int calendarArrSize = calendarIntArr.size();
        for (Integer integer : calendarIntArr) {
            Integer lastCalendarArrValue = calendarIntArr.get(calendarArrSize - 1);
            if (integer <= currentCheckingDateIndex && currentCheckingDateIndex != lastCalendarArrValue) {
                continue;
            }
            int distanceBetweenCurrentIndexAndNextValue =
                    lastCalendarArrValue == currentCheckingDateIndex ?
                            7 - currentCheckingDateIndex + calendarIntArr.get(0) :
                            integer - currentCheckingDateIndex;

            Calendar c = Calendar.getInstance();
            c.setTime(currentCheckingDate);
            c.add(Calendar.DATE, distanceBetweenCurrentIndexAndNextValue);
            return c.getTime();
        }
        return null;
    }

    @Override
    public Set<Attendance> updateAttendancesWithStudentsAndBClass(Set<Student> studentSet, Set<Attendance> attendances, BClass bClass) {
        List<Attendance> attendanceSetWithStudents = new ArrayList<>();
        for (Student student : studentSet) {
            for (Attendance attendance : attendances) {
                System.out.println("Student id:" + student.getId());
                Attendance newAttendance = new Attendance();
                newAttendance.setCheckingDate(attendance.getCheckingDate());
                newAttendance.setStatus(attendance.getStatus());
                newAttendance.setStudent(student);
                newAttendance.setBClass(bClass);
                attendanceSetWithStudents.add(newAttendance);
            }
        }
        return new HashSet<>(attendanceSetWithStudents);
    }

    @Override
    public Set<Attendance> createAttendanceList(Set<Attendance> attendances) {
        return new HashSet<>(attendanceRepository.saveAll(attendances));
    }

    @Override
    public List<AttendanceOfClassDTO> getAttendancesByClassId(Long id) {
        Optional<BClass> optionalBClass = bClassRepository.findById(id);
        if(!optionalBClass.isPresent()){
            throw new BClassNotFoundException(id);
        }
        List<AttendanceOfClassDTO> attendanceOfClassDTOS = new ArrayList<>();
        List<Student> students = studentRepository.findAllByBClassId(id);
        students.forEach(student -> {
            AttendanceOfClassDTO attendanceOfClassDTO = new AttendanceOfClassDTO();
            attendanceOfClassDTO.setStudentName(student.getAccount().getUserDetail().getFullName());
            List<Attendance> attendanceList = attendanceRepository.findAllByStudent_IdAndBClass_Id(student.getId(), id);
            attendanceList.sort(Comparator.comparing(Attendance::getCheckingDate));
            attendanceOfClassDTO.setAttendanceList(attendanceList);
            attendanceOfClassDTOS.add(attendanceOfClassDTO);
        });
        return attendanceOfClassDTOS;
    }

    @Override
    public void updateAttendanceStatus(Long id, String value) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        if(!optionalAttendance.isPresent()){
            throw new AttendanceNotFoundException(id);
        }
        Attendance attendance = optionalAttendance.get();
        attendance.setStatus(AttendanceStatus.valueOf(value));
        attendanceRepository.save(attendance);
    }

    @Override
    public List<Integer> convertCalendarStringArrToIntArr(String[] calendarArr) {
        List<Integer> integers = new ArrayList<>();
        for (String s : calendarArr) {
            switch (s) {
                case "Monday":
                    integers.add(2);
                    break;
                case "Tuesday":
                    integers.add(3);
                    break;
                case "Wednesday":
                    integers.add(4);
                    break;
                case "Thursday":
                    integers.add(5);
                    break;
                case "Friday":
                    integers.add(6);
                    break;
                case "Saturday":
                    integers.add(7);
                    break;
                case "Sunday":
                    integers.add(1);
                    break;
            }
        }
        return integers;
    }
}
