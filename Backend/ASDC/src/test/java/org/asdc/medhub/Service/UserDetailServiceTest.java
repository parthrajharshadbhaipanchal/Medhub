package org.asdc.medhub.Service;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Patient;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {UserDetailServiceImp.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DisabledInAotMode
public class UserDetailServiceTest {
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link UserDetailServiceImp#loadUserByUsername(String)}
     */
    @Test
    public void testLoadUserByUsername() throws UsernameNotFoundException {
        // Arrange
        User user = new User();
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

        User user2 = new User();
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

        User user3 = new User();
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

        User user4 = new User();
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

        User user5 = new User();
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

        User user6 = new User();
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

        User user7 = new User();
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

        User user8 = new User();
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

        User user9 = new User();
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

        User user10 = new User();
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

        User user11 = new User();
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

        User user12 = new User();
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

        User user13 = new User();
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
        when(userRepository.findUserByUsername(Mockito.<String>any())).thenReturn(user13);

        // Act
        UserDetails actualLoadUserByUsernameResult = userDetailServiceImp.loadUserByUsername("janedoe");

        // Assert
        verify(userRepository).findUserByUsername(eq("janedoe"));
        assertSame(user13, actualLoadUserByUsernameResult);
    }
}
