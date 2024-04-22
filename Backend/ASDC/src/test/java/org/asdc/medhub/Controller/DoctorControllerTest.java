package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.DoctorService;
import org.asdc.medhub.Service.Interface.IDoctorService;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DoctorControllerTest {

    @Mock
    public IDoctorService doctorService;

    @InjectMocks
    public DoctorController doctorController;

    public Authentication authenticationImp;

    @Before
    public void setUp() {
        this.doctorService = mock(DoctorService.class);
        this.doctorController = new DoctorController(doctorService);
        this.authenticationImp = new AuthenticationImp();
    }

    private DoctorDetail makeDoctorObject() {
        DoctorDetail doctor = new DoctorDetail();
        doctor.setId(1);
        doctor.setUsername("user@user.com");
        doctor.setFirstName("Steve");
        doctor.setLastName("Smith");
        doctor.setContactNumber("+1234567890");
        doctor.setAddressLine1("456 doctor St");
        doctor.setAddressLine2("Apt 817");
        doctor.setPostalCode("54321");
        doctor.setLicenseNumber("ABC12345");
        doctor.setJobExpTitle("General Practitioner");
        doctor.setJobDescription("Primary care physician");
        doctor.setStartTime(new Timestamp(System.currentTimeMillis())); // Setting current time
        doctor.setEndTime(new Timestamp(System.currentTimeMillis())); // Setting current time
        doctor.setSunday(true);
        doctor.setMonday(true);
        doctor.setTuesday(false);
        doctor.setWednesday(true);
        doctor.setThursday(false);
        doctor.setFriday(true);
        doctor.setSaturday(false);
        doctor.setProfilePictureBase64String("base64EncodedString");
        doctor.setReceiveEmailNotification(true);
        doctor.getSpecializationsOfDoctor().add("Cardiology");
        doctor.getSpecializationsOfDoctor().add("Neurology");

        return doctor;
    }

    private AppointmentDetail makeAppointmentObject(int id) {
        AppointmentDetail appointment = new AppointmentDetail();

        appointment.setId(id);
        appointment.setAppointmentDate(new Date(System.currentTimeMillis()));
        appointment.setTimeSlot("10:00 to 11:00");
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setRemarksFromPatient("Some remarks from the patient");
        appointment.setDayOfWeek(DayOfWeek.MONDAY);
        appointment.setPatientName("John Doe");
        appointment.setPatientEmail("john@example.com");
        appointment.setDoctorName("Dr. Smith");
        appointment.setFeedbackMessage("Excellent service!");
        appointment.setRating(5);
        appointment.setDoctorContactNumber("+1234567890");
        appointment.setDoctorEmail("doctor@example.com");
        appointment.setPrescription("Prescription details");
        appointment.setPharmacistId(123);
        appointment.setPharmacyName("ABC Pharmacy");
        appointment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return appointment;
    }

    private PharmacistDetail makePharmacistObject(int id) {

        PharmacistDetail pharmacist = new PharmacistDetail();
        pharmacist.setId(id);
        pharmacist.setUsername("user@user.com");
        pharmacist.setFirstName("Steve");
        pharmacist.setLastName("Smith");
        pharmacist.setPharmacyName("ABC Pharmacy");
        pharmacist.setContactNumber("+1234567890");
        pharmacist.setAddressLine1("456 Elm St");
        pharmacist.setAddressLine2("Suite 202");
        pharmacist.setPostalCode("54321");
        pharmacist.setLicenseNumber("P12345");

        return pharmacist;
    }


    @Test
    public void testGetDoctorProfile() {
        // Arrange
        DoctorDetail doctor = makeDoctorObject();

        ResponseModel<DoctorDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(doctor);
        expectedResponse.setMessage("Doctor details fetched successfully.");
        expectedResponse.setSuccess(true);

        when(doctorService.getDoctorProfile(Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<DoctorDetail>> responseEntity = doctorController.getDoctorProfile(authenticationImp);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.responseData.getFirstName(), responseEntity.getBody().responseData.getFirstName());
    }

    @Test
    public void testEditDoctorProfile() {
        // Arrange
        DoctorDetail doctor = makeDoctorObject();
        ResponseModel<DoctorDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(doctor);
        expectedResponse.setMessage("Doctor details edited successfully.");
        expectedResponse.setSuccess(true);

        when(doctorService.editDoctorProfile(doctor))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<DoctorDetail>> responseEntity = doctorController.editDoctorProfile(doctor);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.responseData.getFirstName(), responseEntity.getBody().responseData.getFirstName());
    }

    @Test
    public void testSetEmailNotificationPreferenceToTrue() {

        // Arrange
        boolean emailNotificationPreference = true;

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("Notification preference set to : true successfully.");

        when(doctorService.setEmailNotificationPreferenceForDoctor(Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = doctorController.setEmailNotificationPreference(emailNotificationPreference, authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

    }

    @Test
    public void testSetEmailNotificationPreferenceToFalse() {

        // Arrange
        boolean emailNotificationPreference = false;

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("Notification preference set to : false successfully.");

        when(doctorService.setEmailNotificationPreferenceForDoctor(Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = doctorController.setEmailNotificationPreference(emailNotificationPreference, authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

    }

    @Test
    public void testChangePassword() {

        // Arrange
        String newPassword = "abc@123";

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);
        expectedResponse.setMessage("Password changed successfully.");

        when(doctorService.changePassword(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = doctorController.changePassword(newPassword, authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

    }

    @Test
    public void testGetAppointments() {

        // Arrange
        AppointmentDetail appointmentDetail1 = makeAppointmentObject(1);
        AppointmentDetail appointmentDetail2 = makeAppointmentObject(2);

        List<AppointmentDetail> appointmentDetailList = new ArrayList<>();
        appointmentDetailList.add(appointmentDetail1);
        appointmentDetailList.add(appointmentDetail2);

        ResponseModel<List<AppointmentDetail>> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(appointmentDetailList);
        expectedResponse.setSuccess(true);

        boolean activeAppointment = true;

        when(doctorService.getAppointments(Mockito.anyBoolean(), Mockito.anyInt()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<List<AppointmentDetail>>> responseEntity = doctorController.getAppointments(activeAppointment, authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appointmentDetailList, responseEntity.getBody().responseData);

    }

    @Test
    public void testGetVerifiedFilteredPharmacists() {

        // Arrange
        PharmacistDetail pharmacistDetail1 = makePharmacistObject(1);
        PharmacistDetail pharmacistDetail2 = makePharmacistObject(2);

        List<PharmacistDetail> pharmacistDetailList = new ArrayList<>();
        pharmacistDetailList.add(pharmacistDetail1);
        pharmacistDetailList.add(pharmacistDetail2);

        ResponseModel<List<PharmacistDetail>> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(pharmacistDetailList);
        expectedResponse.setSuccess(true);

        String searchString = "ABC Pharmacy";

        when(doctorService.getFilteredAndVerifiedPharmacistList(Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<List<PharmacistDetail>>> responseEntity = doctorController.getVerifiedFilteredPharmacists(searchString);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pharmacistDetailList, responseEntity.getBody().responseData);
    }

    @Test
    public void testUpdateAppointment() {

        // Arrange
        AppointmentDetail appointmentDetail = makeAppointmentObject(1);

        ResponseModel<AppointmentDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(appointmentDetail);

        when(doctorService.updateAppointmentDetail(appointmentDetail)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<AppointmentDetail>> responseEntity = doctorController.updateAppointment(appointmentDetail);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getResponseData(), responseEntity.getBody().responseData);
    }

    @Test
    public void testGetDoctorFeedbackDetails() {
        Map<String, Object> feedback = new HashMap<>();
        feedback.put("averageRating", "5");
        feedback.put("feedbackDetails", "Good feedback");

        ResponseModel<Map<String, Object>> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);
        expectedResponse.setResponseData(feedback);

        when(doctorService.getDoctorFeedbackDetails(Mockito.anyInt()))
                .thenReturn(expectedResponse);

        ResponseEntity<ResponseModel<Map<String, Object>>> responseEntity = doctorController.getDoctorFeedbackDetails(authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getResponseData(), responseEntity.getBody().responseData);
    }

    @Test
    public void testUploadProfilePicture() {
        // Arrange
        MultipartFile multipartFile = mock(MultipartFile.class);
        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        int doctorId = 1;

        when(doctorService.uploadProfilePicture(multipartFile, doctorId))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = doctorController.uploadProfilePicture(multipartFile, doctorId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }
}