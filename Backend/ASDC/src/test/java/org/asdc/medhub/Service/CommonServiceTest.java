package org.asdc.medhub.Service;

import static org.mockito.Mockito.*;

import org.asdc.medhub.Configuration.CustomConfigurations;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import org.asdc.medhub.Utility.Model.DatabaseModels.*;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CommonServiceTest {

    @Mock
    private CustomConfigurations customConfigurations;

    @InjectMocks
    private CommonService commonService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(customConfigurations.getProfilePictureFolderPath()).thenReturn("/path/to/profile/pictures/");
    }

    @Test
    public void testConvertDoctorToDoctorDetail() {
        // Given
        Doctor doctor = mockDoctor(false);

        // When
        DoctorDetail result = commonService.convertDoctorToDoctorDetail(doctor);

        // Then
        assertNotNull(result);
        assertEquals("doctorUser", result.getUsername());
        assertEquals("", result.getProfilePictureBase64String());
    }

    @Test
    public void testSetSpecializationOfDoctorToDoctorModel() throws Exception {
        // Setup
        CommonService service = new CommonService(new CustomConfigurations());
        Doctor doctor = new Doctor();
        doctor.setSpecializationsOfDoctor(new ArrayList<>()); // Ensure the list is initialized
        List<String> specializations = Arrays.asList("Cardiology", "Neurology");

        // Use reflection to access the private method
        Method method = CommonService.class.getDeclaredMethod("setSpecializationOfDoctorToDoctorModel", List.class, Doctor.class);
        method.setAccessible(true);

        // Invoke the private method
        method.invoke(service, specializations, doctor);

        // Verify the results
        assertEquals(2, doctor.getSpecializationsOfDoctor().size());
        assertEquals("Cardiology", doctor.getSpecializationsOfDoctor().get(0).getSpecialization());
        assertEquals("Neurology", doctor.getSpecializationsOfDoctor().get(1).getSpecialization());
    }

    @Test
    public void testUpdateDoctorFromDoctorDetailModel() {
        CommonService service = new CommonService(new CustomConfigurations());
        Doctor doctor = new Doctor();
        doctor.setUser(new User()); // Initialize User to avoid NullPointerException
        DoctorDetail doctorDetail = new DoctorDetail();
        doctorDetail.setFirstName("New First Name");
        doctorDetail.setLastName("New Last Name");
        doctorDetail.setAddressLine1("New Address Line 1");
        doctorDetail.setAddressLine2("New Address Line 2");
        doctorDetail.setPostalCode("New Postal Code");
        doctorDetail.setSpecializationsOfDoctor(Arrays.asList("New Specialization"));
        doctorDetail.setStartTime(Timestamp.from(Instant.now()));
        doctorDetail.setEndTime(Timestamp.from(Instant.now()));
        doctorDetail.setJobDescription("New Job Description");
        doctorDetail.setSunday(true);
        doctorDetail.setMonday(false);
        doctorDetail.setTuesday(true);
        doctorDetail.setWednesday(false);
        doctorDetail.setThursday(true);
        doctorDetail.setFriday(false);
        doctorDetail.setSaturday(true);
        doctorDetail.setReceiveEmailNotification(true);

        Doctor updatedDoctor = service.updateDoctorFromDoctorDetailModel(doctor, doctorDetail);

        assertEquals(doctorDetail.getFirstName(), updatedDoctor.getFirstName());
        assertEquals(doctorDetail.getLastName(), updatedDoctor.getLastName());
        assertEquals(doctorDetail.getAddressLine1(), updatedDoctor.getAddressLine1());
        assertEquals(doctorDetail.getAddressLine2(), updatedDoctor.getAddressLine2());
        assertEquals(doctorDetail.getPostalCode(), updatedDoctor.getPostalCode());
        assertEquals(1, updatedDoctor.getSpecializationsOfDoctor().size());
        assertEquals("New Specialization", updatedDoctor.getSpecializationsOfDoctor().get(0).getSpecialization());
        assertEquals(doctorDetail.getStartTime(), updatedDoctor.getStartTime());
        assertEquals(doctorDetail.getEndTime(), updatedDoctor.getEndTime());
        assertEquals(doctorDetail.getJobDescription(), updatedDoctor.getJobDescription());
        assertEquals(doctorDetail.isSunday(), updatedDoctor.isSunday());
        assertEquals(doctorDetail.isMonday(), updatedDoctor.isMonday());
        assertEquals(doctorDetail.isTuesday(), updatedDoctor.isTuesday());
        assertEquals(doctorDetail.isWednesday(), updatedDoctor.isWednesday());
        assertEquals(doctorDetail.isThursday(), updatedDoctor.isThursday());
        assertEquals(doctorDetail.isFriday(), updatedDoctor.isFriday());
        assertEquals(doctorDetail.isSaturday(), updatedDoctor.isSaturday());
        assertEquals(doctorDetail.isReceiveEmailNotification(), updatedDoctor.getUser().isReceiveEmailNotification());
    }

    @Test
    public void testConvertPharmacistToPharmacistDetail() {
        Pharmacist pharmacist = new Pharmacist();
        User user = new User();

        int pharmacistId = 1;
        String firstName = "John";
        String lastName = "Doe";
        String pharmacyName = "Doe Pharmacy";
        String contactNumber = "1234567890";
        String addressLine1 = "123 Main St";
        String addressLine2 = "Suite 1";
        String postalCode = "12345";
        String licenseNumber = "LIC123456";
        String username = "johndoe@example.com";

        user.setUsername(username);
        pharmacist.setId(pharmacistId);
        pharmacist.setFirstName(firstName);
        pharmacist.setLastName(lastName);
        pharmacist.setPharmacyName(pharmacyName);
        pharmacist.setContactNumber(contactNumber);
        pharmacist.setAddressLine1(addressLine1);
        pharmacist.setAddressLine2(addressLine2);
        pharmacist.setPostalCode(postalCode);
        pharmacist.setLicenseNumber(licenseNumber);
        pharmacist.setUser(user);

        CommonService service = new CommonService(new CustomConfigurations());
        PharmacistDetail pharmacistDetail = service.convertPharmacistToPharmacistDetail(pharmacist);

        assertEquals(pharmacistId, pharmacistDetail.getId());
        assertEquals(firstName, pharmacistDetail.getFirstName());
        assertEquals(lastName, pharmacistDetail.getLastName());
        assertEquals(pharmacyName, pharmacistDetail.getPharmacyName());
        assertEquals(contactNumber, pharmacistDetail.getContactNumber());
        assertEquals(addressLine1, pharmacistDetail.getAddressLine1());
        assertEquals(addressLine2, pharmacistDetail.getAddressLine2());
        assertEquals(postalCode, pharmacistDetail.getPostalCode());
        assertEquals(licenseNumber, pharmacistDetail.getLicenseNumber());
        assertEquals(username, pharmacistDetail.getUsername());
    }




    private Doctor mockDoctor(boolean withProfilePicture) {
        Doctor doctor = new Doctor();
        User user = new User();
        user.setUsername("doctorUser");
        user.setReceiveEmailNotification(true);

        doctor.setUser(user);
        doctor.setId(1);
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setContactNumber("123456789");
        doctor.setAddressLine1("123 Main St");
        doctor.setAddressLine2("Suite 100");
        doctor.setPostalCode("12345");
        doctor.setLicenseNumber("LIC12345");
        doctor.setStartTime(Timestamp.valueOf("2022-01-01 08:00:00"));
        doctor.setEndTime(Timestamp.valueOf("2022-01-01 17:00:00"));
        doctor.setJobDescription("General Practitioner");
        doctor.setSunday(true);
        doctor.setMonday(true);
        doctor.setTuesday(true);
        doctor.setWednesday(true);
        doctor.setThursday(true);
        doctor.setFriday(true);
        doctor.setSaturday(true);

        List<SpecializationOfDoctor> specializations = new ArrayList<>();
        SpecializationOfDoctor specialization = new SpecializationOfDoctor();
        specialization.setSpecialization("General Medicine");
        specializations.add(specialization);
        doctor.setSpecializationsOfDoctor(specializations);

        if (withProfilePicture) {
            doctor.setProfilePictureFileName("doctor.jpg");
            doReturn("ProfilePictureBase64String").when(customConfigurations).getProfilePictureFolderPath();
        } else {
            doctor.setProfilePictureFileName(" ");
        }

        return doctor;
    }

    private Appointment getMockAppointment(){
        Appointment appointment=new Appointment();
        appointment.setId(1);
        appointment.setDoctor(new Doctor());
        appointment.setPatient(new Patient());
        appointment.setPharmacist(new Pharmacist());
        appointment.setStartTime(Timestamp.from(Instant.now()));
        appointment.setEndTime(Timestamp.from(Instant.now()));
        appointment.setAppointmentDate(new Date(321321132));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setRemarksFromPatient("dasfsad");
        appointment.setWeekDay(DayOfWeek.MONDAY);
        appointment.setFeedbackMessage("feedback");
        appointment.setRating(2);
        appointment.setPrescription("fdadfads");
        appointment.setCreatedAt(Timestamp.from(Instant.now()));
        appointment.setUpdatedAt(Timestamp.from(Instant.now()));
        return appointment;
    }

    @Test
    public void testGetAppointmentDetailModelFromAppointment() {
        //Arrange
        Appointment appointment = new Appointment();
        appointment.setId(1);
        appointment.setAppointmentDate(new Date(2024,12,12));
        appointment.setStartTime(Timestamp.from(Instant.now()));
        appointment.setEndTime(Timestamp.from(Instant.now()));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setRemarksFromPatient("No remarks");
        appointment.setWeekDay(DayOfWeek.MONDAY);

        Doctor doctor = new Doctor();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");

        User doctorUser = new User();
        doctorUser.setUsername("johndoe@example.com");
        doctor.setUser(doctorUser);
        appointment.setDoctor(doctor);

        Patient patient = new Patient();
        patient.setFirstName("Alice");
        patient.setLastName("Smith");

        User patientUser = new User();
        patientUser.setUsername("alicesmith@example.com");
        patient.setUser(patientUser);
        appointment.setPatient(patient);
        appointment.setCreatedAt(Timestamp.from(Instant.now()));
        appointment.setPrescription("prescription");

        Pharmacist pharmacist=new Pharmacist();
        pharmacist.setPharmacyName("pharmacy name");
        pharmacist.setId(32);

        appointment.setPharmacist(pharmacist);

        //Action
        var result=commonService.getAppointmentDetailModelFromAppointment(appointment);


        //Assert
        assertNotNull(result);
    }
}