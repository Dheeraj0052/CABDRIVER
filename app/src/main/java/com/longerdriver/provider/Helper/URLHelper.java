package com.longerdriver.provider.Helper;

/**
 * Created by jayakumar on 26/12/16.
 */

public class URLHelper {
//    public static final String base = "http://www.wepositivity.com/uber/";
    public static final String base = "https://www.xcrino.com/longer/";
    //public static final String base = "http://6586b14b.ngrok.io/";
    public static final String HELP_URL = base+"";
    public static final String CALL_PHONE = "1";
    public static final String APP_URL = "https://play.google.com/store/apps/details?id=com.tranxitpro.provider&hl=en";
    public static final String login = base + "api/provider/oauth/token";
    public static final String register = base + "api/provider/register";
    public static final String CHECK_MAIL_ALREADY_REGISTERED = base+"api/provider/verify";
    public static final String USER_PROFILE_API = base + "api/provider/profile";
    public static final String UPDATE_AVAILABILITY_API = base + "api/provider/profile/available";
    public static final String GET_HISTORY_API = base + "api/provider/requests/history";
    public static final String GET_HISTORY_DETAILS_API = base + "api/provider/requests/history/details";
    public static final String CHANGE_PASSWORD_API = base + "api/provider/profile/password";
    public static final String UPCOMING_TRIP_DETAILS = base + "api/provider/requests/upcoming/details";
    public static final String UPCOMING_TRIPS = base + "api/provider/requests/upcoming";
    public static final String CANCEL_REQUEST_API = base + "api/provider/cancel";
    public static final String TARGET_API = base + "api/provider/target";
    public static final String RESET_PASSWORD = base + "api/provider/reset/password";
    public static final String FORGET_PASSWORD = base + "api/provider/forgot/password";
    public static final String FACEBOOK_LOGIN = base + "api/provider/auth/facebook";
    public static final String GOOGLE_LOGIN = base + "api/provider/auth/google";
    public static final String LOGOUT = base + "api/provider/logout";
    public static final String SUMMARY = base + "api/provider/summary";
    public static final String HELP = base + "api/provider/help";
}
