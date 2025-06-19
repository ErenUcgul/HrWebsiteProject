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
    public static final String LEAVE_TYPE = ROOT + "/leave-type";
    public static final String COMPANY_LEAVE_TYPE = ROOT + "/company-leave-type";
    public static final String EMPLOYEE_LEAVE_CONTROLLER = ROOT + "/employee-leave-controller";
    public static final String FILE = ROOT + "/file";

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
    public static final String REJECT_COMPANY = "/reject-company";
    public static final String LIST_COMPANIES_BY_STATES = "/list-companies-by-states";
    public static final String USER_STATE_LIST = "/user-state-list";
    public static final String USER_STATE_PENDING = "/user-state-pending";
    public static final String USER_STATE_ACTIVE = "/user-state-active";
    public static final String USER_STATE_INACTIVE = "/user-state-inactive";
    public static final String USER_STATE_REJECTED = "/user-state-rejected";
    public static final String USER_STATE_IN_REVIEW = "/user-state-in-review";
    public static final String COMPANY_STATE_IN_REVIEW = "/company-state-in-review";
    public static final String COMPANY_STATE_ACTIVE = "/company-state-active";
    public static final String COMPANY_STATE_INACTIVE = "/company-state-inactive";
    public static final String COMPANY_STATE_PENDING = "/company-state-pending";
    public static final String COMPANY_STATE_REJECTED = "/company-state-rejected";

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
    public static final String CREATE_EMPLOYEE = "/create-employee";
    public static final String UPDATE_EMPLOYEE = "/update-employee";
    public static final String DELETE_EMPLOYEE = "/delete-employee";
    public static final String ACTIVATE_EMPLOYEE = "/activate-employee";
    public static final String DEACTIVATE_EMPLOYEE = "/deactivate-employee";
    public static final String LIST_ALL_EMPLOYEES = "/list-all-employees";
    public static final String GET_EMPLOYEE_DETAILS = "/get-employee-details";

    //LeaveType API Controller Method  Endpoints
    public static final String CREATE_LEAVE_TYPE = "/create-leave-type";
    public static final String UPDATE_LEAVE_TYPE = "/update-leave-type";
    public static final String DELETE_LEAVE_TYPE = "/delete-leave-type";
    public static final String LIST_LEAVE_TYPE = "/list-leave-type";

    //LeaveType API Controller Method  Endpoints
    public static final String ASSIGN_COMPANY_LEAVE_TYPE = "/assign-leave-type";
    public static final String LIST_COMPANY_LEAVE_TYPE = "/list-company-leave-type";
    public static final String APPROVE_OR_REJECT_LEAVE = "/approve-or-reject-leave";

    //EmployeeLeave API Controller Method  Endpoints
    public static final String EMPLOYEE_LEAVE_REQUEST = "/employee-leave-request";

    //File Upload API Controller Method  Endpoints
    public static final String UPLOAD_PDF_JPG = "/upload-pdf-jpg";
    public static final String LIST_FILES = "/files/list";
    public static final String DOWNLOAD_FILE = "/files/download";
    public static final String DELETE_FILE = "/files/delete";
}
