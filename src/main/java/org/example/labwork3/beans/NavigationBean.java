package org.example.labwork3.beans;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class NavigationBean implements Serializable {
    public String goToMain() {
        return "main?faces-redirect=true";
    }

    public String goToHome() {
        return "index?faces-redirect=true";
    }
}
