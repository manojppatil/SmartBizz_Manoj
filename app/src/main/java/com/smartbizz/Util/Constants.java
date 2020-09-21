package com.smartbizz.Util;

public class Constants {
    public static final String EMPTY = "";
    public interface Environment{
        String STAGING = "staging";
        String PRODUCTION = "prod";
    }
    public interface RequestCode {
        int CHECK_ALL_PERMISSIONS = 0x001;
        int CHECK_SETTINGS = 0x0102;
        int WRITE_EXTERNAL_STORAGE = 0x0103;
        int REQUEST_CODE_PLAY_SERVICES_RESOLUTION = 0x0104;
        int GET_CURRENT_ADDRESS = 0x0105;
        int READ_EXTERNAL_STORAGE_PERMISSION = 0x106;
        int CAMERA = 0x107;
        int GALLERY = 0x108;
        int VI_KYC_SDK_REQ_CODE =7879;
    }

    public interface Extras {
        String URL = "url";
        String MOBILE_NUMBER = "mobileNumber";
        String ADDRESS = "address";
        String LOCATION = "location";
        String APPLICATIONS = "applications";
        String APP_STATUS = "appStatus";
        String BeatId = "beat_plan_id";
    }

    public interface PrefKeys {
        String AUTH_TOKEN = "authToken";
        String STUDENT_ID = "student_id";
        String AUTH_TOKEN_DATE = "authTokenDate";
//        String MOBILE_NUMBER = "mobileNumber";
        String MOBILE = "mobile";
        String EMAIL = "email";
        String Address = "address";
        String SENDERID = "sender_id";
        String API_KEY = "api_key";
        String BRANDNAME = "BrandName";
        String COMPANYTYPE = "company_type";
        String WHATSAPPLINK = "Whatsapplink";
        String PASSWORD = "password";
        String LOGGED_ID = "logged_id";
        String LOGGED_IN = "userlogin";
        String NAME = "name";
        String LOGO = "logo";
        String FIRST_NAME = "first_name";
        String LAST_LOGIN = "last_login";
        String APPLICANT_ID = "applicantId";
        String LEAD_ID = "leadId";
        String SKIP_UPDATE = "skipUpdate";
        String SANCTIONED_AMOUNT = "sanctionedAmount";
        String COURSE_FEE = "courseFee";
        String UPLOADS_TUTORIAL = "uploadsTutorial";
        String HIDE_PERSONAL_DETAILS = "hidePersonalDetails";
        String PROFESSION = "profession";
        String HOME_SCREEN_LAUNCHED = "homeScreenLaunched";
    }

    public interface AppStage {
        String INTRO_COMPLETED = "intro_completed";
        String PERMISSIONS_COMPLETED = "permissions_completed";
        String MOBILE_VERIFIED = "mobile_verified";
        String BASIC_DETAILS_COMPLETED = "basic_details_completed";
        String PERSONAL_DETAILS_COMPLETED = "personal_details_completed";
        String COURSE_DETAIL_COMPLETED = "course_detail_completed";
        String HALF_OFFER_APPROVED_PENDING = "half_offer_approved_pending";
        String HALF_OFFER_UN_APPROVED_PENDING = "half_offer_un_approved_pending";

        String BANKING_BANK_COMPLETED = "banking_bank_completed";
        String BANKING_AGREEMENT_COMPLETED = "banking_agreement_completed";
        String BANKING_DETAILS_COMPLETED = "banking_details_completed";
    }

}
