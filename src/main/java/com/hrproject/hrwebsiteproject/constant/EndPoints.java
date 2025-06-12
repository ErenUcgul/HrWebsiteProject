package com.hrproject.hrwebsiteproject.constant;

public class
EndPoints {


    private static final String VERSION = "/v1";
    private static final String API = "/api";
    private static final String DEVELOPER = "/dev";
    private static final String TEST = "/test";
    private static final String PROD = "/prod";

    private static final String ROOT = VERSION + DEVELOPER;
    //Controller API Endpoints
    public static final String USER = ROOT + "/user";
    public static final String AUTH = ROOT + "/auth";
    public static final String PUBLIC_API = ROOT + "/publicapi";
    public static final String ADMIN = ROOT + "/admin";
    public static final String COMPANY = ROOT + "/company";
    public static final String EMPLOYEE = ROOT + "/employee";

    //Authentication API Controller Method  Endpoints
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String VERIFY_EMAIL = "/verify-email";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String LOGOUT = "/logout";
    public static final String REFRESH_ACCESS_TOKEN = "/refresh-access-token";

    //Admin API Controller Method Endpoints
    public static final String APPROVE_USER_AND_COMPANY = "/approve-user-and-company";
    public static final String UPDATE_CONTENT = "/update-content";
    public static final String ADMIN_DASHBOARD = "/dashboard";
    //Public API Controller Method API Endpoints
    public static final String HOMEPAGE_CONTENT = "/homepage-content";
    public static final String PLATFORM_FEATURES = "/platform-features";
    public static final String HOW_IT_WORKS = "/how-it-works";
    public static final String HOLIDAYS = "/holidays";

    //Kullanıcı Profil API’leri
    public static final String PROFILE = "/profile";
    public static final String CHANGE_EMAIL = "/change-email";
    public static final String CONFIRM_CHANGE_EMAIL = "/confirm-change-email";
    public static final String CHANGE_PASSWORD = "/change-password";
    public static final String DEACTIVATE_ACCOUNT = "/deactivate-account";

    //Company API Controller Method Endpoints
    public static final String COMPANY_DASHBOARD = "/dashboard";

    //Employee API Controller Method Endpoints
    public static final String EMPLOYEE_DASHBOARD = "/dashboard";
}
