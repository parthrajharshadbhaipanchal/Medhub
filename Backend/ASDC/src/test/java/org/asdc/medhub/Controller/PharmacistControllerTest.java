package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IPharmacistService;
import org.asdc.medhub.Service.PharmacistService;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PharmacistControllerTest
{
    @Mock
    public IPharmacistService pharmacistService;

    @InjectMocks
    public PharmacistController pharmacistController;
    public Authentication authenticationImp;
   @Before
    public void setUp() {
        this.pharmacistService = mock(PharmacistService.class);
        this.pharmacistController = new PharmacistController(pharmacistService);
        this.authenticationImp = new AuthenticationImp();
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
    public PharmacistDetail makePharmacistObject() {
        PharmacistDetail pharmacist = new PharmacistDetail();
        pharmacist.setId(1);
        pharmacist.setUsername("doctor@doctor.com");
        pharmacist.setFirstName("Steve");
        pharmacist.setLastName("Smith");
        pharmacist.setPharmacyName("Apollo");
        pharmacist.setContactNumber("+1234567890");
        pharmacist.setAddressLine1("456 doctor St");
        pharmacist.setAddressLine2("Apt 817");
        pharmacist.setPostalCode("54321");
        pharmacist.setLicenseNumber("ABC12345");
        return pharmacist;
    }
    @Test
    public void testGetProfile() {
        // Arrange
        PharmacistDetail pharmacistDetail = makePharmacistObject();

        ResponseModel<PharmacistDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(pharmacistDetail);
        expectedResponse.setSuccess(true);

        when(pharmacistService.getPharmacistProfileFromId(Mockito.anyInt()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<PharmacistDetail>> responseEntity = pharmacistController.getProfile(authenticationImp);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.responseData.getFirstName(), responseEntity.getBody().responseData.getFirstName());
    }

    @Test
    public void testUpdatePharmacistProfile() {
        // Arrange
        PharmacistDetail pharmacist = makePharmacistObject();
        ResponseModel<PharmacistDetail> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(pharmacist);
        expectedResponse.setSuccess(true);

        when(pharmacistService.updatePharmacistProfile(pharmacist))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<PharmacistDetail>> responseEntity = pharmacistController.updatePatientProfile(pharmacist);
        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.responseData.getFirstName(), responseEntity.getBody().responseData.getFirstName());
    }
    @Test
    public void testGetFilteredAppointments() {

        // Arrange
        AppointmentDetail appointmentDetail1 = makeAppointmentObject(1);
        AppointmentDetail appointmentDetail2 = makeAppointmentObject(2);

        List<AppointmentDetail> appointmentDetailList = new ArrayList<>();
        appointmentDetailList.add(appointmentDetail1);
        appointmentDetailList.add(appointmentDetail2);

        ResponseModel<List<AppointmentDetail>> expectedResponse = new ResponseModel<>();
        expectedResponse.setResponseData(appointmentDetailList);
        expectedResponse.setSuccess(true);

        String patientName = "Smith";

        when(pharmacistService.getAppointmentsFilteredByPatientName(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<List<AppointmentDetail>>> responseEntity = pharmacistController.getFilteredAppointments(patientName, authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appointmentDetailList, responseEntity.getBody().responseData);

    }
    @Test
    public void testChangePassword() {

        // Arrange
        String newPassword = "abc@123";

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(pharmacistService.changePassword(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = pharmacistController.changePassword(newPassword, authenticationImp);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

    }

}
