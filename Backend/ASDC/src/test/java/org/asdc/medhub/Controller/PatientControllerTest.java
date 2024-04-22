package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IPatientService;
import org.asdc.medhub.Service.PatientService;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.RequestModels.AppointmentBookingModel;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorFilterModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PatientDetail;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientControllerTest {
    @Mock
    public IPatientService patientService;

    @InjectMocks
    public PatientController patientController;

    public Authentication authenticationImp;

    @Before
    public void setUp() {
        this.patientService = mock(PatientService.class);
        this.patientController = new PatientController(patientService);
        this.authenticationImp = new AuthenticationImp();
    }

    private PatientDetail makePatientObejct() {

        PatientDetail patient = new PatientDetail();
        patient.setFirstName("David");
        patient.setLastName("Warner");
        patient.setMedicalHistory("History of brain disease");
        return patient;
    }

    private AppointmentBookingModel makeAppointmentBookingModelObject() {
        AppointmentBookingModel appointment = new AppointmentBookingModel();
        appointment.setDoctorId(1);
        appointment.setTimeSlot("09:00 to 15:00");
        appointment.setAppointmentDate(LocalDate.of(2024, 3, 28));
        appointment.setRemarksFromPatient("Patient remarks");

        return appointment;
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

    private User makeUserObject() {

        User user = new User();

        user.setId(123);
        user.setUsername("user@user.com");
        user.setPassword("password123");
        user.setUserRole(UserRole.DOCTOR);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        user.setIsEmailVerified(true);
        user.setAdminVerificationStatus(AdminVerificationStatus.VERIFIED);
        user.setEmailVerifyToken("token");
        user.setResetToken("resetToken");
        user.setReceiveEmailNotification(true);
        return user;
    }

    private DoctorDetail makeDoctorObject(int id) {
        DoctorDetail doctor = new DoctorDetail();
        doctor.setId(id);
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

    @Test
    public void testGetPatientProfile() {
        // Arrange
        PatientDetail patient = makePatientObejct();

        ResponseModel<PatientDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(patient);
        expectedResponse.setSuccess(true);

        when(patientService.getPatientProfile(Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<PatientDetail>> responseEntity = patientController.getPatientProfile(authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.responseData.getFirstName(), responseEntity.getBody().responseData.getFirstName());
    }

    @Test
    public void testEditPatientProfile() {
        // Arrange
        PatientDetail patient = makePatientObejct();
        String email = "user@user.com";

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(patientService.editPatientProfile(email, patient))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = patientController.editPatientProfile(patient, authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }

    @Test
    public void testGetAllFilteredDoctorList() {

        // Arrange
        List<String> specializations = new ArrayList<>();
        specializations.add("Cardiology");
        specializations.add("Neurology");
        DoctorFilterModel doctorFilterModel = new DoctorFilterModel();
        doctorFilterModel.setSpecializations(specializations);

        DoctorDetail doctorDetail1 = makeDoctorObject(1);
        DoctorDetail doctorDetail2 = makeDoctorObject(2);

        List<DoctorDetail> doctorDetailList = new ArrayList<>();
        doctorDetailList.add(doctorDetail1);
        doctorDetailList.add(doctorDetail2);

        ResponseModel<List<DoctorDetail>> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(doctorDetailList);
        expectedResponse.setSuccess(true);

        when(patientService.getAllFilteredDoctorList(doctorFilterModel))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<List<DoctorDetail>>> responseEntity = patientController.getAllFilteredDoctorList(doctorFilterModel);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(doctorDetailList, responseEntity.getBody().responseData);

    }

    @Test
    public void testGetMedicalSpecializationsByName() {

        // Arrange
        String searchString = "Cardiology";
        List<String> specializations = new ArrayList<>();
        specializations.add("Cardiology");
        specializations.add("Neurology");

        ResponseModel<List<String>> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);
        expectedResponse.setResponseData(specializations);

        when(patientService.getMedicalSpecializationByName(searchString))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<List<String>>> responseEntity = patientController.getMedicalSpecializationsByName(searchString);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(specializations, responseEntity.getBody().responseData);
    }

    @Test
    public void testGetDoctorDetails() {
        // Arrange
        DoctorDetail doctor = makeDoctorObject(1);

        ResponseModel<DoctorDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(doctor);
        expectedResponse.setSuccess(true);

        when(patientService.getDoctorDetails(Mockito.anyInt())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<DoctorDetail>> responseEntity = patientController.getDoctorDetails(doctor.getId());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.responseData.getFirstName(), responseEntity.getBody().responseData.getFirstName());
    }

    @Test
    public void testGetDoctorsAvailableTimeSlots() {

        // Arrange
        int doctorId = 1;

        HashMap<String, List<String>> availabilityTimeSlots = new HashMap<>();
        List<String> mondayTimeSlots = new ArrayList<>();
        mondayTimeSlots.add("9:00 AM - 10:00 AM");
        mondayTimeSlots.add("2:00 PM - 3:00 PM");
        availabilityTimeSlots.put("Monday", mondayTimeSlots);
        List<String> tuesdayTimeSlots = new ArrayList<>();
        tuesdayTimeSlots.add("10:00 AM - 11:00 AM");
        tuesdayTimeSlots.add("3:00 PM - 4:00 PM");
        availabilityTimeSlots.put("Tuesday", tuesdayTimeSlots);

        ResponseModel<HashMap<String, List<String>>> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);
        expectedResponse.setResponseData(availabilityTimeSlots);

        when(patientService.getDoctorAvailabilityTimeSlots(doctorId))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<HashMap<String, List<String>>>> responseEntity = patientController.getDoctorsAvailableTimeSlots(doctorId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getResponseData(), responseEntity.getBody().getResponseData());
    }

    @Test
    public void testGetAppointments() {
        // Arrange
        AppointmentDetail appointmentDetail1 = makeAppointmentObject(1);
        AppointmentDetail appointmentDetail2 = makeAppointmentObject(2);
        List<AppointmentDetail> appointmentDetails = new ArrayList<>();
        appointmentDetails.add(appointmentDetail1);
        appointmentDetails.add(appointmentDetail2);

        ResponseModel<List<AppointmentDetail>> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);
        expectedResponse.setResponseData(appointmentDetails);

        when(patientService.getAppointments(Mockito.any()))
                .thenReturn(expectedResponse);

        ResponseEntity<ResponseModel<List<AppointmentDetail>>> responseEntity = patientController.getAppointments(authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appointmentDetails, responseEntity.getBody().getResponseData());
    }

    @Test
    public void testSaveFeedback() {

        // Arrange
        AppointmentDetail appointmentDetail = makeAppointmentObject(1);

        ResponseModel<AppointmentDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(patientService.updateAppointmentDetail(appointmentDetail))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<AppointmentDetail>> responseEntity = patientController.saveFeedback(appointmentDetail);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }
}
