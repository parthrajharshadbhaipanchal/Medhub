package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.AuthService;
import org.asdc.medhub.Service.Interface.IAuthService;
import org.asdc.medhub.Service.Interface.IPatientService;
import org.asdc.medhub.Service.PatientService;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorRegistrationModel;
import org.asdc.medhub.Utility.Model.RequestModels.PatientRegistrationModal;
import org.asdc.medhub.Utility.Model.RequestModels.PharmacistRegistrationModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    @Mock
    public IPatientService patientService;

    @Mock
    public IAuthService authService;

    @InjectMocks
    public AuthController authController;

    @Before
    public void setUp() {
        this.patientService = mock(PatientService.class);
        this.authService = mock(AuthService.class);
        this.authController = new AuthController(authService, patientService);
    }

    private DoctorRegistrationModel makeDoctorRegistraionModelObject() {

        DoctorRegistrationModel doctorRegistrationModel = new DoctorRegistrationModel();
        doctorRegistrationModel.setFirstName("Steve");
        doctorRegistrationModel.setLastName("Smith");
        doctorRegistrationModel.setContactNumber("1234567890");
        doctorRegistrationModel.setAddressLine1("123 Main St");
        doctorRegistrationModel.setAddressLine2("Apt 101");
        doctorRegistrationModel.setPostalCode("12345");
        doctorRegistrationModel.setLicenseNumber("ABCDE12345");
        doctorRegistrationModel.setSpecializationsOfDoctor(Arrays.asList("Cardiology", "Neurology"));

        return doctorRegistrationModel;
    }

    private PatientRegistrationModal makePatientRegistrationModelObject() {
        PatientRegistrationModal patientRegistrationModal = new PatientRegistrationModal();

        patientRegistrationModal.setFirstName("Steve");
        patientRegistrationModal.setLastName("Smith");

        return patientRegistrationModal;
    }

    private PharmacistRegistrationModel makePharmacistRegistrationModelObject() {
        PharmacistRegistrationModel pharmacistRegistrationModel = new PharmacistRegistrationModel();

        pharmacistRegistrationModel.setFirstName("steve");
        pharmacistRegistrationModel.setLastName("smith");
        pharmacistRegistrationModel.setPharmacyName("ABC Pharmacy");
        pharmacistRegistrationModel.setContactNumber("1234567890");
        pharmacistRegistrationModel.setAddressLine1("123 Main St");
        pharmacistRegistrationModel.setAddressLine2("Apt 101");
        pharmacistRegistrationModel.setPostalCode("12345");
        pharmacistRegistrationModel.setLicenseNumber("ABCDE12345");

        return pharmacistRegistrationModel;
    }

    private User makeUserObject() {

        User user = new User();

        user.setId(1);
        user.setUsername("user@user.com");
        user.setPassword("password123");
        user.setUserRole(UserRole.PATIENT);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        user.setIsEmailVerified(true);
        user.setAdminVerificationStatus(AdminVerificationStatus.VERIFIED);
        user.setEmailVerifyToken("token");
        user.setResetToken("resetToken");
        user.setReceiveEmailNotification(true);

        return user;

    }

    @Test
    public void testRegisterDoctor() {

        // Arrange
        DoctorRegistrationModel doctorRegistrationModel = makeDoctorRegistraionModelObject();

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(authService.registerUser(doctorRegistrationModel))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = authController.registerDoctor(doctorRegistrationModel);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }

    @Test
    public void testRegisterPatient() {

        // Arrange
        PatientRegistrationModal patientRegistrationModal = makePatientRegistrationModelObject();

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(authService.registerUser(patientRegistrationModal))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = authController.registerPatient(patientRegistrationModal);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }

    @Test
    public void testRegisterPharmacist() {

        // Arrange
        PharmacistRegistrationModel pharmacistRegistrationModel = makePharmacistRegistrationModelObject();

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(authService.registerUser(pharmacistRegistrationModel))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = authController.registerPharmacist(pharmacistRegistrationModel);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }

    @Test
    public void testSignInUser() {

        // Arrange
        User user = makeUserObject();
        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(authService.signInUser(user))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = authController.signInUser(user);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());

    }

    @Test
    public void testForgotPassword() {

        // Arrange
        String email = "user@user.com";

        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("email", email);
        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(authService.addForgotPasswordRequestAndSendEmail(Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = authController.forgotPassword(emailMap);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }

    @Test
    public void testResetPassword() {

        // Arrange
        String email = "user@user.com";
        String token = "token";
        String newPassword = "new_password";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("token", token);
        requestBody.put("newPassword", newPassword);

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(authService.resetPassword(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = authController.resetPassword(requestBody);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
    }

    @Test
    public void testVerifyEmail() {

        // Arrange
        String email = "user@user.com";
        String token = "token";

        ResponseModel<String> expectedResponse = new ResponseModel<>();
        expectedResponse.setSuccess(true);

        when(authService.verifyEmail(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<ResponseModel<String>> responseEntity = authController.verifyEmail(email, token);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.isSuccess(), responseEntity.getBody().isSuccess());
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
        ResponseModel<List<String>> responseEntity = authController.getMedicalSpecializationsByName(searchString);

        // Assert
        assertEquals(expectedResponse.isSuccess(), responseEntity.isSuccess());
    }

}
