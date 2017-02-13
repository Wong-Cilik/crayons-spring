//package com.example.controller;
//
//import org.springframework.stereotype.Component;
//
//import com.example.Auth.SecurityUtils;
//import com.vaadin.spring.access.ViewAccessControl;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.ui.UI;
//
//@SpringComponent
//public class SampleViewAccessControl implements ViewAccessControl {
//
//    @Override
//    public boolean isAccessGranted(UI ui, String beanName) {
//        if (beanName.equals("adminView")) {
//            return SecurityUtils.hasRole("admin");
//        } else {
//            return SecurityUtils.hasRole("user");
//        }
//    }
//}
