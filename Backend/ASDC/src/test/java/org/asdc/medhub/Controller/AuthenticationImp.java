/**
 * Author: Sameera De Silva
 * User:Admin
 * Date:26-03-2024
 * Time:18:27
 */

package org.asdc.medhub.Controller;

import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationImp implements Authentication {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        User user = new User();
        Doctor doctor = new Doctor();
        Pharmacist pharmacist=new Pharmacist();
        doctor.setId(1);
        user.setId(1);
        user.setDoctor(doctor);
        user.setPharmacist(pharmacist);
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "user@user.com";
    }
}
