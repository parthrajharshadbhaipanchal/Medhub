package org.asdc.medhub.Service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.asdc.medhub.Configuration.CustomConfigurations;
import org.asdc.medhub.Configuration.JwtService;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.IJwtService;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Patient;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorRegistrationModel;
import org.asdc.medhub.Utility.Model.RequestModels.PatientRegistrationModal;
import org.asdc.medhub.Utility.Model.RequestModels.PharmacistRegistrationModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {AuthService.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class AuthServiceTest {
  static User user;
  static User user2;
  static User user3;
  static User user4;
  static User user5;
  static User user6;
  static User user7;
  static User user8;
  static User user9;
  static User user10;
  static User user11;
  static User user12;
  static User user13;


  @Autowired
  private AuthService authService;

  @MockBean
  private EmailService emailService;

  @MockBean
  private IJwtService iJwtService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private CustomConfigurations customConfigurations;


  @BeforeEach
  public void setUp() {
    user = new User();
    user.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user.setCreatedAt(mock(Timestamp.class));
    user.setDoctor(new Doctor());
    user.setEmailVerifyToken("ABC123");
    user.setId(1);
    user.setIsEmailVerified(true);
    user.setPassword("iloveyou");
    user.setPatient(new Patient());
    user.setPharmacist(new Pharmacist());
    user.setReceiveEmailNotification(true);
    user.setResetToken("ABC123");
    user.setUpdatedAt(mock(Timestamp.class));
    user.setUserRole(UserRole.DOCTOR);
    user.setUsername("janedoe");

    Doctor doctor = new Doctor();
    doctor.setAddressLine1("42 Main St");
    doctor.setAddressLine2("42 Main St");
    doctor.setAppointments(new ArrayList<>());
    doctor.setContactNumber("42");
    doctor.setCreatedAt(mock(Timestamp.class));
    doctor.setEndTime(mock(Timestamp.class));
    doctor.setFirstName("Jane");
    doctor.setFriday(true);
    doctor.setId(1);
    doctor.setJobDescription("Job Description");
    doctor.setJobExpTitle("Dr");
    doctor.setLastName("Doe");
    doctor.setLicenseNumber("42");
    doctor.setListOfBlogs(new ArrayList<>());
    doctor.setMonday(true);
    doctor.setPostalCode("Postal Code");
    doctor.setProfilePictureFileName("foo.txt");
    doctor.setSaturday(true);
    doctor.setSpecializationsOfDoctor(new ArrayList<>());
    doctor.setStartTime(mock(Timestamp.class));
    doctor.setSunday(true);
    doctor.setThursday(true);
    doctor.setTuesday(true);
    doctor.setUpdatedAt(mock(Timestamp.class));
    doctor.setUser(user);
    doctor.setWednesday(true);

    user2 = new User();
    user2.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user2.setCreatedAt(mock(Timestamp.class));
    user2.setDoctor(new Doctor());
    user2.setEmailVerifyToken("ABC123");
    user2.setId(1);
    user2.setIsEmailVerified(true);
    user2.setPassword("iloveyou");
    user2.setPatient(new Patient());
    user2.setPharmacist(new Pharmacist());
    user2.setReceiveEmailNotification(true);
    user2.setResetToken("ABC123");
    user2.setUpdatedAt(mock(Timestamp.class));
    user2.setUserRole(UserRole.DOCTOR);
    user2.setUsername("janedoe");

    Patient patient = new Patient();
    patient.setAppointments(new ArrayList<>());
    patient.setBookingHistory("Booking History");
    patient.setCreatedAt(mock(Timestamp.class));
    patient.setFirstName("Jane");
    patient.setId(1);
    patient.setLastName("Doe");
    patient.setMedicalHistory("Medical History");
    patient.setUpdatedAt(mock(Timestamp.class));
    patient.setUser(user2);

    user3 = new User();
    user3.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user3.setCreatedAt(mock(Timestamp.class));
    user3.setDoctor(new Doctor());
    user3.setEmailVerifyToken("ABC123");
    user3.setId(1);
    user3.setIsEmailVerified(true);
    user3.setPassword("iloveyou");
    user3.setPatient(new Patient());
    user3.setPharmacist(new Pharmacist());
    user3.setReceiveEmailNotification(true);
    user3.setResetToken("ABC123");
    user3.setUpdatedAt(mock(Timestamp.class));
    user3.setUserRole(UserRole.DOCTOR);
    user3.setUsername("janedoe");

    Pharmacist pharmacist = new Pharmacist();
    pharmacist.setAddressLine1("42 Main St");
    pharmacist.setAddressLine2("42 Main St");
    pharmacist.setAppointments(new ArrayList<>());
    pharmacist.setContactNumber("42");
    pharmacist.setCreatedAt(mock(Timestamp.class));
    pharmacist.setFirstName("Jane");
    pharmacist.setId(1);
    pharmacist.setLastName("Doe");
    pharmacist.setLicenseNumber("42");
    pharmacist.setPharmacyName("Pharmacy Name");
    pharmacist.setPostalCode("Postal Code");
    pharmacist.setUpdatedAt(mock(Timestamp.class));
    pharmacist.setUser(user3);

    user4 = new User();
    user4.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user4.setCreatedAt(mock(Timestamp.class));
    user4.setDoctor(doctor);
    user4.setEmailVerifyToken("ABC123");
    user4.setId(1);
    user4.setIsEmailVerified(true);
    user4.setPassword("iloveyou");
    user4.setPatient(patient);
    user4.setPharmacist(pharmacist);
    user4.setReceiveEmailNotification(true);
    user4.setResetToken("ABC123");
    user4.setUpdatedAt(mock(Timestamp.class));
    user4.setUserRole(UserRole.DOCTOR);
    user4.setUsername("janedoe");

    Doctor doctor2 = new Doctor();
    doctor2.setAddressLine1("42 Main St");
    doctor2.setAddressLine2("42 Main St");
    doctor2.setAppointments(new ArrayList<>());
    doctor2.setContactNumber("42");
    doctor2.setCreatedAt(mock(Timestamp.class));
    doctor2.setEndTime(mock(Timestamp.class));
    doctor2.setFirstName("Jane");
    doctor2.setFriday(true);
    doctor2.setId(1);
    doctor2.setJobDescription("Job Description");
    doctor2.setJobExpTitle("Dr");
    doctor2.setLastName("Doe");
    doctor2.setLicenseNumber("42");
    doctor2.setListOfBlogs(new ArrayList<>());
    doctor2.setMonday(true);
    doctor2.setPostalCode("Postal Code");
    doctor2.setProfilePictureFileName("foo.txt");
    doctor2.setSaturday(true);
    doctor2.setSpecializationsOfDoctor(new ArrayList<>());
    doctor2.setStartTime(mock(Timestamp.class));
    doctor2.setSunday(true);
    doctor2.setThursday(true);
    doctor2.setTuesday(true);
    doctor2.setUpdatedAt(mock(Timestamp.class));
    doctor2.setUser(user4);
    doctor2.setWednesday(true);

    user5 = new User();
    user5.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user5.setCreatedAt(mock(Timestamp.class));
    user5.setDoctor(new Doctor());
    user5.setEmailVerifyToken("ABC123");
    user5.setId(1);
    user5.setIsEmailVerified(true);
    user5.setPassword("iloveyou");
    user5.setPatient(new Patient());
    user5.setPharmacist(new Pharmacist());
    user5.setReceiveEmailNotification(true);
    user5.setResetToken("ABC123");
    user5.setUpdatedAt(mock(Timestamp.class));
    user5.setUserRole(UserRole.DOCTOR);
    user5.setUsername("janedoe");

    Doctor doctor3 = new Doctor();
    doctor3.setAddressLine1("42 Main St");
    doctor3.setAddressLine2("42 Main St");
    doctor3.setAppointments(new ArrayList<>());
    doctor3.setContactNumber("42");
    doctor3.setCreatedAt(mock(Timestamp.class));
    doctor3.setEndTime(mock(Timestamp.class));
    doctor3.setFirstName("Jane");
    doctor3.setFriday(true);
    doctor3.setId(1);
    doctor3.setJobDescription("Job Description");
    doctor3.setJobExpTitle("Dr");
    doctor3.setLastName("Doe");
    doctor3.setLicenseNumber("42");
    doctor3.setListOfBlogs(new ArrayList<>());
    doctor3.setMonday(true);
    doctor3.setPostalCode("Postal Code");
    doctor3.setProfilePictureFileName("foo.txt");
    doctor3.setSaturday(true);
    doctor3.setSpecializationsOfDoctor(new ArrayList<>());
    doctor3.setStartTime(mock(Timestamp.class));
    doctor3.setSunday(true);
    doctor3.setThursday(true);
    doctor3.setTuesday(true);
    doctor3.setUpdatedAt(mock(Timestamp.class));
    doctor3.setUser(user5);
    doctor3.setWednesday(true);

    user6 = new User();
    user6.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user6.setCreatedAt(mock(Timestamp.class));
    user6.setDoctor(new Doctor());
    user6.setEmailVerifyToken("ABC123");
    user6.setId(1);
    user6.setIsEmailVerified(true);
    user6.setPassword("iloveyou");
    user6.setPatient(new Patient());
    user6.setPharmacist(new Pharmacist());
    user6.setReceiveEmailNotification(true);
    user6.setResetToken("ABC123");
    user6.setUpdatedAt(mock(Timestamp.class));
    user6.setUserRole(UserRole.DOCTOR);
    user6.setUsername("janedoe");

    Patient patient2 = new Patient();
    patient2.setAppointments(new ArrayList<>());
    patient2.setBookingHistory("Booking History");
    patient2.setCreatedAt(mock(Timestamp.class));
    patient2.setFirstName("Jane");
    patient2.setId(1);
    patient2.setLastName("Doe");
    patient2.setMedicalHistory("Medical History");
    patient2.setUpdatedAt(mock(Timestamp.class));
    patient2.setUser(user6);

    user7 = new User();
    user7.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user7.setCreatedAt(mock(Timestamp.class));
    user7.setDoctor(new Doctor());
    user7.setEmailVerifyToken("ABC123");
    user7.setId(1);
    user7.setIsEmailVerified(true);
    user7.setPassword("iloveyou");
    user7.setPatient(new Patient());
    user7.setPharmacist(new Pharmacist());
    user7.setReceiveEmailNotification(true);
    user7.setResetToken("ABC123");
    user7.setUpdatedAt(mock(Timestamp.class));
    user7.setUserRole(UserRole.DOCTOR);
    user7.setUsername("janedoe");

    Pharmacist pharmacist2 = new Pharmacist();
    pharmacist2.setAddressLine1("42 Main St");
    pharmacist2.setAddressLine2("42 Main St");
    pharmacist2.setAppointments(new ArrayList<>());
    pharmacist2.setContactNumber("42");
    pharmacist2.setCreatedAt(mock(Timestamp.class));
    pharmacist2.setFirstName("Jane");
    pharmacist2.setId(1);
    pharmacist2.setLastName("Doe");
    pharmacist2.setLicenseNumber("42");
    pharmacist2.setPharmacyName("Pharmacy Name");
    pharmacist2.setPostalCode("Postal Code");
    pharmacist2.setUpdatedAt(mock(Timestamp.class));
    pharmacist2.setUser(user7);

    user8 = new User();
    user8.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user8.setCreatedAt(mock(Timestamp.class));
    user8.setDoctor(doctor3);
    user8.setEmailVerifyToken("ABC123");
    user8.setId(1);
    user8.setIsEmailVerified(true);
    user8.setPassword("iloveyou");
    user8.setPatient(patient2);
    user8.setPharmacist(pharmacist2);
    user8.setReceiveEmailNotification(true);
    user8.setResetToken("ABC123");
    user8.setUpdatedAt(mock(Timestamp.class));
    user8.setUserRole(UserRole.DOCTOR);
    user8.setUsername("janedoe");

    Patient patient3 = new Patient();
    patient3.setAppointments(new ArrayList<>());
    patient3.setBookingHistory("Booking History");
    patient3.setCreatedAt(mock(Timestamp.class));
    patient3.setFirstName("Jane");
    patient3.setId(1);
    patient3.setLastName("Doe");
    patient3.setMedicalHistory("Medical History");
    patient3.setUpdatedAt(mock(Timestamp.class));
    patient3.setUser(user8);

    user9 = new User();
    user9.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user9.setCreatedAt(mock(Timestamp.class));
    user9.setDoctor(new Doctor());
    user9.setEmailVerifyToken("ABC123");
    user9.setId(1);
    user9.setIsEmailVerified(true);
    user9.setPassword("iloveyou");
    user9.setPatient(new Patient());
    user9.setPharmacist(new Pharmacist());
    user9.setReceiveEmailNotification(true);
    user9.setResetToken("ABC123");
    user9.setUpdatedAt(mock(Timestamp.class));
    user9.setUserRole(UserRole.DOCTOR);
    user9.setUsername("janedoe");

    Doctor doctor4 = new Doctor();
    doctor4.setAddressLine1("42 Main St");
    doctor4.setAddressLine2("42 Main St");
    doctor4.setAppointments(new ArrayList<>());
    doctor4.setContactNumber("42");
    doctor4.setCreatedAt(mock(Timestamp.class));
    doctor4.setEndTime(mock(Timestamp.class));
    doctor4.setFirstName("Jane");
    doctor4.setFriday(true);
    doctor4.setId(1);
    doctor4.setJobDescription("Job Description");
    doctor4.setJobExpTitle("Dr");
    doctor4.setLastName("Doe");
    doctor4.setLicenseNumber("42");
    doctor4.setListOfBlogs(new ArrayList<>());
    doctor4.setMonday(true);
    doctor4.setPostalCode("Postal Code");
    doctor4.setProfilePictureFileName("foo.txt");
    doctor4.setSaturday(true);
    doctor4.setSpecializationsOfDoctor(new ArrayList<>());
    doctor4.setStartTime(mock(Timestamp.class));
    doctor4.setSunday(true);
    doctor4.setThursday(true);
    doctor4.setTuesday(true);
    doctor4.setUpdatedAt(mock(Timestamp.class));
    doctor4.setUser(user9);
    doctor4.setWednesday(true);

    user10 = new User();
    user10.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user10.setCreatedAt(mock(Timestamp.class));
    user10.setDoctor(new Doctor());
    user10.setEmailVerifyToken("ABC123");
    user10.setId(1);
    user10.setIsEmailVerified(true);
    user10.setPassword("iloveyou");
    user10.setPatient(new Patient());
    user10.setPharmacist(new Pharmacist());
    user10.setReceiveEmailNotification(true);
    user10.setResetToken("ABC123");
    user10.setUpdatedAt(mock(Timestamp.class));
    user10.setUserRole(UserRole.DOCTOR);
    user10.setUsername("janedoe");

    Patient patient4 = new Patient();
    patient4.setAppointments(new ArrayList<>());
    patient4.setBookingHistory("Booking History");
    patient4.setCreatedAt(mock(Timestamp.class));
    patient4.setFirstName("Jane");
    patient4.setId(1);
    patient4.setLastName("Doe");
    patient4.setMedicalHistory("Medical History");
    patient4.setUpdatedAt(mock(Timestamp.class));
    patient4.setUser(user10);

    user11 = new User();
    user11.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user11.setCreatedAt(mock(Timestamp.class));
    user11.setDoctor(new Doctor());
    user11.setEmailVerifyToken("ABC123");
    user11.setId(1);
    user11.setIsEmailVerified(true);
    user11.setPassword("iloveyou");
    user11.setPatient(new Patient());
    user11.setPharmacist(new Pharmacist());
    user11.setReceiveEmailNotification(true);
    user11.setResetToken("ABC123");
    user11.setUpdatedAt(mock(Timestamp.class));
    user11.setUserRole(UserRole.DOCTOR);
    user11.setUsername("janedoe");

    Pharmacist pharmacist3 = new Pharmacist();
    pharmacist3.setAddressLine1("42 Main St");
    pharmacist3.setAddressLine2("42 Main St");
    pharmacist3.setAppointments(new ArrayList<>());
    pharmacist3.setContactNumber("42");
    pharmacist3.setCreatedAt(mock(Timestamp.class));
    pharmacist3.setFirstName("Jane");
    pharmacist3.setId(1);
    pharmacist3.setLastName("Doe");
    pharmacist3.setLicenseNumber("42");
    pharmacist3.setPharmacyName("Pharmacy Name");
    pharmacist3.setPostalCode("Postal Code");
    pharmacist3.setUpdatedAt(mock(Timestamp.class));
    pharmacist3.setUser(user11);

    user12 = new User();
    user12.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user12.setCreatedAt(mock(Timestamp.class));
    user12.setDoctor(doctor4);
    user12.setEmailVerifyToken("ABC123");
    user12.setId(1);
    user12.setIsEmailVerified(true);
    user12.setPassword("iloveyou");
    user12.setPatient(patient4);
    user12.setPharmacist(pharmacist3);
    user12.setReceiveEmailNotification(true);
    user12.setResetToken("ABC123");
    user12.setUpdatedAt(mock(Timestamp.class));
    user12.setUserRole(UserRole.DOCTOR);
    user12.setUsername("janedoe");

    Pharmacist pharmacist4 = new Pharmacist();
    pharmacist4.setAddressLine1("42 Main St");
    pharmacist4.setAddressLine2("42 Main St");
    pharmacist4.setAppointments(new ArrayList<>());
    pharmacist4.setContactNumber("42");
    pharmacist4.setCreatedAt(mock(Timestamp.class));
    pharmacist4.setFirstName("Jane");
    pharmacist4.setId(1);
    pharmacist4.setLastName("Doe");
    pharmacist4.setLicenseNumber("42");
    pharmacist4.setPharmacyName("Pharmacy Name");
    pharmacist4.setPostalCode("Postal Code");
    pharmacist4.setUpdatedAt(mock(Timestamp.class));
    pharmacist4.setUser(user12);

    user13 = new User();
    user13.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
    user13.setCreatedAt(mock(Timestamp.class));
    user13.setDoctor(doctor2);
    user13.setEmailVerifyToken("ABC123");
    user13.setId(1);
    user13.setIsEmailVerified(true);
    user13.setPassword("iloveyou");
    user13.setPatient(patient3);
    user13.setPharmacist(pharmacist4);
    user13.setReceiveEmailNotification(true);
    user13.setResetToken("ABC123");
    user13.setUpdatedAt(mock(Timestamp.class));
    user13.setUserRole(UserRole.DOCTOR);
    user13.setUsername("janedoe");

  }

  /**
   * Method under test: {@link AuthService#registerUser(DoctorRegistrationModel)}
   */
  @Test
  public void testRegisterUser() {
    // Arrange
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(true);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(new DoctorRegistrationModel());

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(isNull(), isNull());
    assertEquals("", actualRegisterUserResult.getResponseData());
    assertEquals("Doctor already registered.", actualRegisterUserResult.getMessage());
  }

  /**
   * Method under test: {@link AuthService#registerUser(DoctorRegistrationModel)}
   */
  @Test
  public void testRegisterUser2() {
    // Arrange
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(true);
    DoctorRegistrationModel doctorForRegistration = mock(DoctorRegistrationModel.class);
    when(doctorForRegistration.getUsername()).thenReturn("janedoe");
    when(doctorForRegistration.getUserRole()).thenReturn(UserRole.DOCTOR);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(doctorForRegistration);

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(eq("janedoe"), eq(UserRole.DOCTOR));
    verify(doctorForRegistration).getUserRole();
    verify(doctorForRegistration).getUsername();
    assertEquals("", actualRegisterUserResult.getResponseData());
    assertEquals("Doctor already registered.", actualRegisterUserResult.getMessage());
  }

  /**
   * Method under test: {@link AuthService#registerUser(DoctorRegistrationModel)}
   */
  @Test
  public void testRegisterUser3() {
    // Arrange

    when(userRepository.save(Mockito.<User>any())).thenReturn(user13);
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(false);
    doNothing().when(emailService).sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    DoctorRegistrationModel doctorForRegistration = mock(DoctorRegistrationModel.class);
    when(doctorForRegistration.getAddressLine1()).thenReturn("42 Main St");
    when(doctorForRegistration.getAddressLine2()).thenReturn("42 Main St");
    when(doctorForRegistration.getContactNumber()).thenReturn("42");
    when(doctorForRegistration.getFirstName()).thenReturn("Jane");
    when(doctorForRegistration.getLastName()).thenReturn("Doe");
    when(doctorForRegistration.getLicenseNumber()).thenReturn("42");
    when(doctorForRegistration.getPostalCode()).thenReturn("Postal Code");
    when(doctorForRegistration.getSpecializationsOfDoctor()).thenReturn(new ArrayList<>());
    when(doctorForRegistration.getPassword()).thenReturn("iloveyou");
    when(doctorForRegistration.getUsername()).thenReturn("janedoe");
    when(doctorForRegistration.getUserRole()).thenReturn(UserRole.DOCTOR);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(doctorForRegistration);

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(eq("janedoe"), eq(UserRole.DOCTOR));
    verify(doctorForRegistration).getPassword();
    verify(doctorForRegistration, atLeast(1)).getUserRole();
    verify(doctorForRegistration, atLeast(1)).getUsername();
    verify(doctorForRegistration).getAddressLine1();
    verify(doctorForRegistration).getAddressLine2();
    verify(doctorForRegistration).getContactNumber();
    verify(doctorForRegistration).getFirstName();
    verify(doctorForRegistration).getLastName();
    verify(doctorForRegistration).getLicenseNumber();
    verify(doctorForRegistration).getPostalCode();
    verify(doctorForRegistration).getSpecializationsOfDoctor();
    verify(userRepository).save(Mockito.<User>any());
    assertEquals("Doctor registered successfully.", actualRegisterUserResult.getMessage());
    assertTrue(actualRegisterUserResult.isSuccess());
  }

  /**
   * Method under test: {@link AuthService#registerUser(PatientRegistrationModal)}
   */
  @Test
  public void testRegisterUser4() {
    // Arrange
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(true);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService
            .registerUser(new PatientRegistrationModal("Jane", "Doe"));

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(isNull(), isNull());
    assertEquals("", actualRegisterUserResult.getResponseData());
    assertEquals("Patient already registered.", actualRegisterUserResult.getMessage());
  }

  /**
   * Method under test: {@link AuthService#registerUser(PatientRegistrationModal)}
   */
  @Test
  public void testRegisterUser5() {
    // Arrange
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(true);
    PatientRegistrationModal patientForRegistration = mock(PatientRegistrationModal.class);
    when(patientForRegistration.getUsername()).thenReturn("janedoe");
    when(patientForRegistration.getUserRole()).thenReturn(UserRole.DOCTOR);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(patientForRegistration);

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(eq("janedoe"), eq(UserRole.DOCTOR));
    verify(patientForRegistration).getUserRole();
    verify(patientForRegistration).getUsername();
    assertEquals("", actualRegisterUserResult.getResponseData());
    assertEquals("Patient already registered.", actualRegisterUserResult.getMessage());
  }

  /**
   * Method under test: {@link AuthService#registerUser(PatientRegistrationModal)}
   */
  @Test
  public void testRegisterUser6() {
    // Arrange
    when(userRepository.save(Mockito.<User>any())).thenReturn(user13);
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(false);
    doNothing().when(emailService).sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    PatientRegistrationModal patientForRegistration = mock(PatientRegistrationModal.class);
    when(patientForRegistration.getFirstName()).thenReturn("Jane");
    when(patientForRegistration.getLastName()).thenReturn("Doe");
    when(patientForRegistration.getPassword()).thenReturn("iloveyou");
    when(patientForRegistration.getUsername()).thenReturn("janedoe");
    when(patientForRegistration.getUserRole()).thenReturn(UserRole.DOCTOR);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(patientForRegistration);

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(eq("janedoe"), eq(UserRole.DOCTOR));
    verify(patientForRegistration).getPassword();
    verify(patientForRegistration, atLeast(1)).getUserRole();
    verify(patientForRegistration, atLeast(1)).getUsername();
    verify(patientForRegistration).getFirstName();
    verify(patientForRegistration).getLastName();
    verify(userRepository).save(Mockito.<User>any());
    assertEquals("Patient registered successfully.", actualRegisterUserResult.getMessage());
    assertTrue(actualRegisterUserResult.isSuccess());
  }

  /**
   * Method under test:
   * {@link AuthService#registerUser(PharmacistRegistrationModel)}
   */
  @Test
  public void testRegisterUser7() {
    // Arrange
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(true);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(new PharmacistRegistrationModel("Jane",
            "Doe", "Pharmacy Name", "42", "42 Main St", "42 Main St", "Postal Code", "42"));

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(isNull(), isNull());
    assertEquals("", actualRegisterUserResult.getResponseData());
    assertEquals("Pharmacist already registered.", actualRegisterUserResult.getMessage());
  }

  /**
   * Method under test:
   * {@link AuthService#registerUser(PharmacistRegistrationModel)}
   */
  @Test
  public void testRegisterUser8() {
    // Arrange
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(true);
    PharmacistRegistrationModel pharmacistRegistrationModel = mock(PharmacistRegistrationModel.class);
    when(pharmacistRegistrationModel.getUsername()).thenReturn("janedoe");
    when(pharmacistRegistrationModel.getUserRole()).thenReturn(UserRole.DOCTOR);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(pharmacistRegistrationModel);

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(eq("janedoe"), eq(UserRole.DOCTOR));
    verify(pharmacistRegistrationModel).getUserRole();
    verify(pharmacistRegistrationModel).getUsername();
    assertEquals("", actualRegisterUserResult.getResponseData());
    assertEquals("Pharmacist already registered.", actualRegisterUserResult.getMessage());
  }

  /**
   * Method under test:
   * {@link AuthService#registerUser(PharmacistRegistrationModel)}
   */
  @Test
  public void testRegisterUser9() {
    // Arrange
    when(userRepository.save(Mockito.<User>any())).thenReturn(user13);
    when(userRepository.existsUserByUsernameAndUserRole(Mockito.<String>any(), Mockito.<UserRole>any()))
            .thenReturn(false);
    PharmacistRegistrationModel pharmacistRegistrationModel = mock(PharmacistRegistrationModel.class);
    when(pharmacistRegistrationModel.getAddressLine1()).thenReturn("42 Main St");
    when(pharmacistRegistrationModel.getAddressLine2()).thenReturn("42 Main St");
    when(pharmacistRegistrationModel.getContactNumber()).thenReturn("42");
    when(pharmacistRegistrationModel.getFirstName()).thenReturn("Jane");
    when(pharmacistRegistrationModel.getLastName()).thenReturn("Doe");
    when(pharmacistRegistrationModel.getLicenseNumber()).thenReturn("42");
    when(pharmacistRegistrationModel.getPharmacyName()).thenReturn("Pharmacy Name");
    when(pharmacistRegistrationModel.getPostalCode()).thenReturn("Postal Code");
    when(pharmacistRegistrationModel.getPassword()).thenReturn("iloveyou");
    when(pharmacistRegistrationModel.getUsername()).thenReturn("janedoe");
    when(pharmacistRegistrationModel.getUserRole()).thenReturn(UserRole.DOCTOR);

    // Act
    ResponseModel<String> actualRegisterUserResult = authService.registerUser(pharmacistRegistrationModel);

    // Assert
    verify(userRepository).existsUserByUsernameAndUserRole(eq("janedoe"), eq(UserRole.DOCTOR));
    verify(pharmacistRegistrationModel).getPassword();
    verify(pharmacistRegistrationModel, atLeast(1)).getUserRole();
    verify(pharmacistRegistrationModel, atLeast(1)).getUsername();
    verify(pharmacistRegistrationModel).getAddressLine1();
    verify(pharmacistRegistrationModel).getAddressLine2();
    verify(pharmacistRegistrationModel).getContactNumber();
    verify(pharmacistRegistrationModel).getFirstName();
    verify(pharmacistRegistrationModel).getLastName();
    verify(pharmacistRegistrationModel).getLicenseNumber();
    verify(pharmacistRegistrationModel).getPharmacyName();
    verify(pharmacistRegistrationModel).getPostalCode();
    verify(userRepository).save(Mockito.<User>any());
    assertEquals("Pharmacist registered successfully.", actualRegisterUserResult.getMessage());
    assertTrue(actualRegisterUserResult.isSuccess());
  }


  /**
   * Method under test: {@link AuthService#resetPassword(String, String, String)}
   */
  @Test
  public void testResetPassword() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualResetPasswordResult = (new AuthService(jwtService, userRepository,
            new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).resetPassword("", null, null);

    // Assert
    assertEquals(null, actualResetPasswordResult.getMessage());
    assertEquals("Invalid input fields. Please check it.", actualResetPasswordResult.getResponseData());
  }

  /**
   * Method under test: {@link AuthService#resetPassword(String, String, String)}
   */
  @Test
  public void testResetPassword2() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualResetPasswordResult = (new AuthService(jwtService, userRepository,
            new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).resetPassword("", null, null);

    // Assert
    assertEquals(null, actualResetPasswordResult.getMessage());
    assertEquals("Invalid input fields. Please check it.", actualResetPasswordResult.getResponseData());
  }

  /**
   * Method under test: {@link AuthService#resetPassword(String, String, String)}
   */
  @Test
  public void testResetPassword3() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualResetPasswordResult = (new AuthService(jwtService, userRepository,
            new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).resetPassword("foo", "", null);

    // Assert
    assertEquals(null, actualResetPasswordResult.getMessage());
    assertEquals("Invalid input fields. Please check it.", actualResetPasswordResult.getResponseData());
  }

  /**
   * Method under test: {@link AuthService#resetPassword(String, String, String)}
   */
  @Test
  public void testResetPassword4() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualResetPasswordResult = (new AuthService(jwtService, userRepository,
            new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).resetPassword("foo", "", null);

    // Assert
    assertEquals(null, actualResetPasswordResult.getMessage());
    assertEquals("Invalid input fields. Please check it.", actualResetPasswordResult.getResponseData());
  }

  /**
   * Method under test: {@link AuthService#resetPassword(String, String, String)}
   */
  @Test
  public void testResetPassword5() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualResetPasswordResult = (new AuthService(jwtService, userRepository,
            new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).resetPassword("foo", "foo", "");

    // Assert
    assertEquals(null, actualResetPasswordResult.getMessage());
    assertEquals("Invalid input fields. Please check it.", actualResetPasswordResult.getResponseData());
  }

  /**
   * Method under test: {@link AuthService#resetPassword(String, String, String)}
   */
  @Test
  public void testResetPassword6() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualResetPasswordResult = (new AuthService(jwtService, userRepository,
            new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).resetPassword("foo", "foo", "");

    // Assert
    assertEquals(null, actualResetPasswordResult.getMessage());
    assertEquals("Invalid input fields. Please check it.", actualResetPasswordResult.getResponseData());
  }

  /**
   * Method under test:
   * {@link AuthService#addForgotPasswordRequestAndSendEmail(String)}
   */
  @Test
  public void testAddForgotPasswordRequestAndSendEmail() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualAddForgotPasswordRequestAndSendEmailResult = (new AuthService(jwtService,
            userRepository, new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).addForgotPasswordRequestAndSendEmail(null);

    // Assert
    assertEquals("Email is required.", actualAddForgotPasswordRequestAndSendEmailResult.getResponseData());
    assertEquals("Failure.", actualAddForgotPasswordRequestAndSendEmailResult.getMessage());
  }

  /**
   * Method under test:
   * {@link AuthService#addForgotPasswordRequestAndSendEmail(String)}
   */
  @Test
  public void testAddForgotPasswordRequestAndSendEmail2() {

    // Arrange
    JwtService jwtService = new JwtService(new CustomConfigurations());
    UserRepository userRepository = mock(UserRepository.class);

    // Act
    ResponseModel<String> actualAddForgotPasswordRequestAndSendEmailResult = (new AuthService(jwtService,
            userRepository, new EmailService(new JavaMailSenderImpl()), new CustomConfigurations())).addForgotPasswordRequestAndSendEmail("");

    // Assert
    assertEquals("Email is required.", actualAddForgotPasswordRequestAndSendEmailResult.getResponseData());
    assertEquals("Failure.", actualAddForgotPasswordRequestAndSendEmailResult.getMessage());
  }

  @Test
  public void testVerifyEmailSuccess() {
    // Arrange
    User mockUser = new User();
    mockUser.setUsername("testUser");
    mockUser.setEmailVerifyToken("validToken");
    mockUser.setIsEmailVerified(false); // Initially, the email is not verified

    // Mocking UserRepository to return our mock user when findUserByUsernameAndEmailVerifyToken is called
    when(userRepository.findUserByUsernameAndEmailVerifyToken("user@example.com", "validToken")).thenReturn(mockUser);

    // Act
    ResponseModel<String> result = authService.verifyEmail("user@example.com", "validToken");

    // Assert
    verify(userRepository).findUserByUsernameAndEmailVerifyToken("user@example.com", "validToken");
    verify(userRepository).save(mockUser); // Verifying userRepository.save() was called with our mock user
    assertTrue(mockUser.getIsEmailVerified()); // Asserting that the user is now verified
    assertEquals("Email verified!", result.getResponseData());
    assertEquals("Success.", result.getMessage());
    assertTrue(result.isSuccess());
  }


  @Test
  public void testVerifyEmailFailure() {
    // Arrange
    // Mocking UserRepository to return null when findUserByUsernameAndEmailVerifyToken is called with invalid parameters
    when(userRepository.findUserByUsernameAndEmailVerifyToken("invalidUser@example.com", "invalidToken"))
            .thenReturn(null);

    // Act
    ResponseModel<String> result = authService.verifyEmail("invalidUser@example.com", "invalidToken");

    // Assert
    verify(userRepository).findUserByUsernameAndEmailVerifyToken("invalidUser@example.com", "invalidToken");
    assertEquals("Verification failed, please try again!", result.getResponseData());
    assertEquals("Failure.", result.getMessage());
    assertFalse(result.isSuccess());
  }

}
