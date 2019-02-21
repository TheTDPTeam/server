package com.tdpteam.service.interf;

import com.tdpteam.repo.api.response.CalendarResponse;
import com.tdpteam.repo.dto.attendance.AttendanceOfClassDTO;
import com.tdpteam.repo.entity.Attendance;
import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.user.Student;
import org.springframework.scheduling.annotation.Async;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface AttendanceService {
    Set<Attendance> generateAttendanceSetForClass(String calendar, Date startDate, int numberOfLessons);
    List<Integer> convertCalendarStringArrToIntArr(String[] calendarArr);
    Date moveToNextAvailableIndex(Date currentCheckingDate, int currentCheckingDateIndex, List<Integer> calendarIntArr);
    Set<Attendance> updateAttendancesWithStudentsAndBClass(Set<Student> studentSet, Set<Attendance> attendances, BClass bClass);

    Set<Attendance> createAttendanceList(Set<Attendance> attendances);

    List<AttendanceOfClassDTO> getAttendancesByClassId(Long id);

    @Async
    void updateAttendanceStatus(Long id, String value);

    List<CalendarResponse> getAttendancesByStudentId(Long studentId);

    void skipAttendance(Long id);

    @Async
    void sendMailToNotifySkippingAttendance(String studentEmail, String studentName, String skippedDate, String subjectName, String className);
}
