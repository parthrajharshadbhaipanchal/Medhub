package org.asdc.medhub.Service;

import jakarta.mail.Multipart;
import org.asdc.medhub.Configuration.CustomConfigurations;
import org.asdc.medhub.Repository.AppointmentRepository;
import org.asdc.medhub.Repository.PharmacistRepository;
import org.asdc.medhub.Repository.SpecializationOfDoctorRepository;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.*;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    public AppointmentRepository appointmentRepositoryMock;
    public ICommonService commonServiceMock;
    public CustomConfigurations customConfigurationsMock;
    public UserRepository userRepositoryMock;
    public SpecializationOfDoctorRepository specOfDoctorRepoMock;
    public PharmacistRepository pharmacistRepository;

    @Before
    public void InitializeTest(){
        this.appointmentRepositoryMock= Mockito.mock(AppointmentRepository.class);
        this.commonServiceMock=Mockito.mock(ICommonService.class);
        this.customConfigurationsMock=new CustomConfigurations();
        this.userRepositoryMock=Mockito.mock(UserRepository.class);
        this.specOfDoctorRepoMock =Mockito.mock(SpecializationOfDoctorRepository.class);
        this.pharmacistRepository=Mockito.mock(PharmacistRepository.class);

        this.doctorService=new DoctorService(
                this.userRepositoryMock,
                this.specOfDoctorRepoMock,
                this.customConfigurationsMock,
                this.commonServiceMock,
                this.appointmentRepositoryMock,
                this.pharmacistRepository
        );
    }

    @Test
    public void  getAppointmentsTestSuccess(){
        //Arrange
        Mockito.when(appointmentRepositoryMock.getAppointmentsByDoctorIdAndStatusIsIn(Mockito.anyInt(),Mockito.anyList()))
                .thenReturn(List.of(new Appointment()));

        //Action
        var result=this.doctorService.getAppointments(true,1);

        //Assert
        Assert.assertTrue(result.isSuccess);
    }

    @Test
    public void  getAppointmentsTestFail(){
        //Arrange
        Mockito.when(appointmentRepositoryMock.getAppointmentsByDoctorIdAndStatusIsIn(Mockito.anyInt(),Mockito.anyList()))
                .thenReturn(null);

        //Action
        var result=this.doctorService.getAppointments(true,1);

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void getFilteredAndVerifiedPharmacistListTestSuccess(){
        //Arrange
        List<Pharmacist> pharmacistList=new ArrayList<>();
        pharmacistList.add(new Pharmacist());
        pharmacistList.add(new Pharmacist());
        Mockito.when(this.pharmacistRepository.getPharmacistsByPharmacyNameContainingAndUser_AdminVerificationStatus(Mockito.anyString(),Mockito.any()))
                .thenReturn(pharmacistList);

        //Action
        var result=this.doctorService.getFilteredAndVerifiedPharmacistList("search string");

        //Assert
        Assert.assertTrue(result.isSuccess);
        Assert.assertEquals(result.responseData.size(),2);
    }

    @Test
    public void getFilteredAndVerifiedPharmacistListTestFail(){
        //Arrange
        Mockito.when(this.pharmacistRepository.getPharmacistsByPharmacyNameContainingAndUser_AdminVerificationStatus(Mockito.anyString(),Mockito.any()))
                .thenReturn(null);

        //Action
        var result=this.doctorService.getFilteredAndVerifiedPharmacistList("search string");

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void updateAppointmentDetailTestSuccess(){
        //Arrange
        Appointment appointment=new Appointment();
        Mockito.when(this.appointmentRepositoryMock.getAppointmentById(Mockito.anyInt()))
                .thenReturn(appointment);

        Pharmacist pharmacist=new Pharmacist();
        Mockito.when(this.pharmacistRepository.getPharmacistById(Mockito.anyInt()))
                .thenReturn(pharmacist);

        AppointmentDetail appointmentDetail=new AppointmentDetail();
        appointmentDetail.setId(1);

        //Action
        var result=this.doctorService.updateAppointmentDetail(appointmentDetail);

        //Assert
        Assert.assertTrue(result.isSuccess);
    }



    @Test
    public void updateAppointmentDetailTestFail(){
        //Arrange
        Appointment appointment=new Appointment();
        Mockito.when(this.appointmentRepositoryMock.getAppointmentById(Mockito.anyInt()))
                .thenReturn(null);

        Pharmacist pharmacist=new Pharmacist();
        Mockito.when(this.pharmacistRepository.getPharmacistById(Mockito.anyInt()))
                .thenReturn(null);

        AppointmentDetail appointmentDetail=new AppointmentDetail();
        //Action
        var result=this.doctorService.updateAppointmentDetail(appointmentDetail);

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void getDoctorFeedbackDetailsTestSuccess() {
        // Arrange

        Doctor doctor = new Doctor();
        doctor.setId(1);

        // Create the Appointment object and set its Doctor
        List<Appointment> appointments= new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setDoctor(doctor);
        appointment1.setFeedbackMessage("Feedback 1");
        appointment1.setRating(4);
        appointments.add(appointment1);

        // Create another Appointment object and set its Doctor
        Appointment appointment2 = new Appointment();
        appointment2.setDoctor(doctor);
        appointment2.setFeedbackMessage("Feedback 2");
        appointment2.setRating(5);
        appointments.add(appointment2);


        Mockito.when(this.appointmentRepositoryMock.getAppointmentsByDoctorId(Mockito.anyInt())).thenReturn(appointments);

        // Action
        ResponseModel<Map<String, Object>> result = doctorService.getDoctorFeedbackDetails(1);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(4.5, result.getResponseData().get("averageRating"));

    }

    @Test
    public void changePasswordTestSuccess(){
        //Arrange
        String username = "xyz@gmail.com";
        String newUsername = "abc@gmail.com";

        User user = new User();
        user.setUserRole(UserRole.DOCTOR);
        user.setId(1);
        user.setUsername(username);

        Doctor doctor = new Doctor();
        user.setDoctor(doctor);
        doctor.setUser(user);

        // Mock the findUserByUsername method to return the user
        when(userRepositoryMock.findUserByUsername(username)).thenReturn(user);

        // Call the method
        ResponseModel<String> response = doctorService.changePassword(username,newUsername);

        // Check assertions
        assertTrue(response.isSuccess);
    }

    @Test
    public void changePasswordTestFail(){

        //Arrange
        String username = "xyz@gmail.com";
        String newUsername = "abc@gmail.com";

        // Mock the findUserByUsername method to not find the user
        when(userRepositoryMock.findUserByUsername(username)).thenReturn(null);

        // Call the method
        ResponseModel<String> response = doctorService.changePassword(username,newUsername);

        // Check assertions
        assertFalse(response.isSuccess);
    }

    @Test
    public void getDoctorProfileTestSuccess(){
        //Arrange
        String username = "xyz@gmail.com";

        User user = new User();
        user.setUserRole(UserRole.DOCTOR);
        user.setId(1);
        user.setUsername(username);

        Doctor doctor = new Doctor();
        user.setDoctor(doctor);
        doctor.setUser(user);

        // Mock the findDoctorByUsername method to return the user
        when(userRepositoryMock.findDoctorByUsername(username)).thenReturn(user);

        // Call the method
        ResponseModel<DoctorDetail> response = doctorService.getDoctorProfile(username);

        // Check assertions
        assertTrue(response.isSuccess);
    }

    @Test
    public void getDoctorProfileTestFail(){
        //Arrange
        String username = "xyz@gmail.com";

        // Mock the findDoctorByUsername method to return null
        when(userRepositoryMock.findDoctorByUsername(username)).thenReturn(null);

        // Call the method
        ResponseModel<DoctorDetail> response = doctorService.getDoctorProfile(username);

        // Check assertions
        assertFalse(response.isSuccess);
    }
/*
    @Test
    public void uploadProfilePictureTest_Success() throws Exception{
        //Arrange
        MultipartFile multipartFile=Mockito.mock(MultipartFile.class);
        when(multipartFile.isEmpty()).thenReturn(false);

        User user=new User();
        Doctor doctor=new Doctor();
        doctor.setProfilePictureFileName(System.getProperty("user.dir")+"test.jpg");
        user.setDoctor(doctor);

        when(userRepositoryMock.findUserByDoctorId(Mockito.anyInt())).thenReturn(user);
        when(userRepositoryMock.save(Mockito.any(User.class))).thenReturn(user);

        Field privateField = CustomConfigurations.class.getDeclaredField("windowsPath");
        privateField.setAccessible(true);
        privateField.set(this.customConfigurationsMock,System.getProperty("user.dir"));

        //Action
        var result=this.doctorService.uploadProfilePicture(multipartFile,3);

        //Assert
        assertTrue(result.isSuccess);
    }*/

    @Test
    public void uploadProfilePictureTest_Fail() throws Exception{
        //Arrange
        MultipartFile multipartFile=Mockito.mock(MultipartFile.class);
        when(multipartFile.isEmpty()).thenReturn(true);
        User user=new User();

        when(userRepositoryMock.findUserByDoctorId(Mockito.anyInt())).thenReturn(user);
        when(userRepositoryMock.save(Mockito.any(User.class))).thenReturn(user);

        Field privateField = CustomConfigurations.class.getDeclaredField("windowsPath");
        privateField.setAccessible(true);
        privateField.set(this.customConfigurationsMock,System.getProperty("user.dir"));

        //Action
        var result=this.doctorService.uploadProfilePicture(multipartFile,3);

        //Assert
        assertFalse(result.isSuccess);
    }

    @Test
    public void editDoctorProfileTestSuccess(){
        //Arrange
        User user=new User();
        DoctorDetail doctorDetail=new DoctorDetail();
        Doctor doctor=new Doctor();
        doctor.setSpecializationsOfDoctor(new ArrayList<>());
        user.setDoctor(doctor);

        when(this.userRepositoryMock.findUserByDoctorId(Mockito.anyInt())).thenReturn(user);
        when(this.commonServiceMock.updateDoctorFromDoctorDetailModel(Mockito.any(Doctor.class),Mockito.any(DoctorDetail.class)))
                .thenReturn(doctor);
        when(this.specOfDoctorRepoMock.saveAll(Mockito.anyList())).thenReturn(List.of(new SpecializationOfDoctor()));
        when(this.userRepositoryMock.save(Mockito.any(User.class))).thenReturn(user);

        //Action
        var result=this.doctorService.editDoctorProfile(doctorDetail);

        //Assert
        assertTrue(result.isSuccess);
    }

    @Test
    public void editDoctorProfileTestWhenNoDoctorFound(){
        //Arrange
        User user=new User();
        DoctorDetail doctorDetail=new DoctorDetail();
        Doctor doctor=new Doctor();
        doctor.setSpecializationsOfDoctor(new ArrayList<>());
        user.setDoctor(doctor);

        when(this.userRepositoryMock.findUserByDoctorId(Mockito.anyInt())).thenReturn(null);
        when(this.commonServiceMock.updateDoctorFromDoctorDetailModel(Mockito.any(Doctor.class),Mockito.any(DoctorDetail.class)))
                .thenReturn(doctor);
        when(this.specOfDoctorRepoMock.saveAll(Mockito.anyList())).thenReturn(List.of(new SpecializationOfDoctor()));
        when(this.userRepositoryMock.save(Mockito.any(User.class))).thenReturn(user);

        //Action
        var result=this.doctorService.editDoctorProfile(doctorDetail);

        //Assert
        assertFalse(result.isSuccess);
    }
    @Test
    public void editDoctorProfileTestWhenExceptionThrow(){
        //Arrange
        User user=new User();
        DoctorDetail doctorDetail=new DoctorDetail();
        Doctor doctor=new Doctor();
        doctor.setSpecializationsOfDoctor(new ArrayList<>());
        user.setDoctor(doctor);

        when(this.userRepositoryMock.findUserByDoctorId(Mockito.anyInt())).thenReturn(user);
        when(this.commonServiceMock.updateDoctorFromDoctorDetailModel(Mockito.any(Doctor.class),Mockito.any(DoctorDetail.class)))
                .thenReturn(doctor);
        when(this.specOfDoctorRepoMock.saveAll(Mockito.anyList())).thenReturn(List.of(new SpecializationOfDoctor()));
        when(this.userRepositoryMock.save(Mockito.any(User.class))).thenReturn(user);

        //Action
        var result=this.doctorService.editDoctorProfile(null);

        //Assert
        assertFalse(result.isSuccess);
    }

    @Test
    public void setEmailNotificationPreferenceForDoctorTestSuccess(){
        //Arrange
        User user=new User();
        Doctor doctor=new Doctor();
        doctor.setSpecializationsOfDoctor(new ArrayList<>());
        user.setDoctor(doctor);

        when(this.userRepositoryMock.findUserByUsername(Mockito.anyString())).thenReturn(user);
        when(this.userRepositoryMock.saveAndFlush(Mockito.any(User.class))).thenReturn(user);

        //Action
        var result=this.doctorService.setEmailNotificationPreferenceForDoctor("email@email.com",true);

        //Assert
        assertTrue(result.isSuccess);
    }


}

