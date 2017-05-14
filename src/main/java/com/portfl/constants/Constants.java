package com.portfl.constants;

public class Constants {
    public static final class Views {
        public static final String HOME_PAGE = "home";
        public static final String LOGIN_PAGE = "login";
        public static final String REGISTRATION_PAGE = "registration";
        public static final String PROFILE_PAGE="profile";
        public static final String PHOTO_PAGE="photo";
    }

    public static final class Redirect {
        public static final String TO_HOME = "redirect:/";
    }

    public static final class Packages {
        public static final String CONTROLLERS = "com.portfl.controller";
        public static final String SERVICES = "com.portfl.service";
        public static final String DAO = "com.portfl.repository";
        public static final String LISTENERS = "com.portfl.listeners";
    }

    public static final class Regex {
        public static final String FIRST_NAME = "([A-Z]{1}[a-z]{1,10})|([А-Я]{1}[а-я]{1,10})";
        public static final String LAST_NAME = "([A-Z]{1}[a-z]{1,10})|([А-Я]{1}[а-я]{1,10})";
        public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        public static final String USERNAME = "([a-z]{4,10})";
    }

    public static final class Common {
        public static final int ONE = 1;
        public static final String EMPTY = "";
    }

}
