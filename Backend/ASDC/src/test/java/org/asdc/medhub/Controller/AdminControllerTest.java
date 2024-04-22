package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IAdminService;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class AdminControllerTest {
    @Mock
    IAdminService adminService;
    @InjectMocks
    AdminController adminController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAdminUnverifiedDoctors() throws Exception {
        // Arrange
        List<DoctorDetail> doctorDetails = List.of(new DoctorDetail(0, "username", "firstName", "lastName", "contactNumber", "addressLine1", "addressLine2", "postalCode", "licenseNumber", "jobExpTitle", "jobDescription", new Timestamp(0, 0, 0, 0, 0, 0, 0), new Timestamp(0, 0, 0, 0, 0, 0, 0), true, true, true, true, true, true, true, "profilePictureBase64String", true, List.of("specializationsOfDoctor")));
        ResponseModel<List<DoctorDetail>> responseModel = new ResponseModel<>(doctorDetails, "message", true);

        when(adminService.getAdminUnverifiedDoctors()).thenReturn(responseModel);

        // Act
        ResponseEntity<ResponseModel<List<DoctorDetail>>> result = adminController.getAdminUnverifiedDoctors();

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(responseModel, result.getBody());
    }


    @Test
    public void testGetAdminUnverifiedDoctorDetails() throws Exception {
        // Arrange
        DoctorDetail doctorDetail = new DoctorDetail(0, "username", "firstName", "lastName", "contactNumber", "addressLine1", "addressLine2", "postalCode", "licenseNumber", "jobExpTitle", "jobDescription", new Timestamp(0, 0, 0, 0, 0, 0, 0), new Timestamp(0, 0, 0, 0, 0, 0, 0), true, true, true, true, true, true, true, "profilePictureBase64String", true, List.of("specializationsOfDoctor"));
        ResponseModel<DoctorDetail> responseModel = new ResponseModel<>(doctorDetail, "message", true);

        when(adminService.getAdminUnverifiedDoctorIndividual(anyString())).thenReturn(responseModel);

        Map<String, String> requestBody = Map.of("email", "doctor@example.com");

        // Act
        ResponseEntity<ResponseModel<DoctorDetail>> result = adminController.getAdminUnverifiedDoctorDetails(requestBody);

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(responseModel, result.getBody());
    }


    @Test
    public void testGetAdminUnverifiedPharmacists() throws Exception {
        // Arrange
        List<PharmacistDetail> pharmacistDetails = List.of(new PharmacistDetail(0, "username", "firstName", "lastName", "pharmacyName", "contactNumber", "addressLine1", "addressLine2", "postalCode", "licenseNumber"));
        ResponseModel<List<PharmacistDetail>> responseModel = new ResponseModel<>(pharmacistDetails, "message", true);

        when(adminService.getAdminUnverifiedPharmacists()).thenReturn(responseModel);

        // Act
        ResponseEntity<ResponseModel<List<PharmacistDetail>>> result = adminController.getAdminUnverifiedPharmacists();

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(responseModel, result.getBody());
    }


    @Test
    public void testGetAdminUnverifiedPharmacistDetails() throws Exception {
        // Arrange
        PharmacistDetail pharmacistDetail = new PharmacistDetail(0, "username", "firstName", "lastName", "pharmacyName", "contactNumber", "addressLine1", "addressLine2", "postalCode", "licenseNumber");
        ResponseModel<PharmacistDetail> responseModel = new ResponseModel<>(pharmacistDetail, "message", true);

        when(adminService.getAdminUnverifiedPharmacistIndividual(anyString())).thenReturn(responseModel);

        Map<String, String> requestBody = Map.of("email", "pharmacist@example.com");

        // Act
        ResponseEntity<ResponseModel<PharmacistDetail>> result = adminController.getAdminUnverifiedPharmacistDetails(requestBody);

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(responseModel, result.getBody());
    }


    @Test
    public void testApproveUserByAdmin() throws Exception {
        // Arrange
        ResponseModel<String> responseModel = new ResponseModel<>("responseData", "message", true);

        when(adminService.approveUserByAdmin(anyString())).thenReturn(responseModel);

        Map<String, String> requestBody = Map.of("email", "user@example.com");

        // Act
        ResponseEntity<ResponseModel<String>> result = adminController.approveUserByAdmin(requestBody);

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(responseModel, result.getBody());
    }


    @Test
    public void testRejectUserByAdmin() throws Exception {
        // Arrange
        ResponseModel<String> responseModel = new ResponseModel<>("responseData", "message", true);

        when(adminService.rejectUserByAdmin(anyString())).thenReturn(responseModel);

        Map<String, String> requestBody = Map.of("email", "user@example.com");

        // Act
        ResponseEntity<ResponseModel<String>> result = adminController.rejectUserByAdmin(requestBody);

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(responseModel, result.getBody());
    }
}
