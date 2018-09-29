package com.eduvanzapplication.newUI.newViews;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.webviews.WebViewPrivacyPolicy;
import com.eduvanzapplication.newUI.webviews.WebViewTermsNCondition;

/**HOW TO DETECT BOTTOM OF SCROLL VIEW
 *  https://stackoverflow.com/questions/10316743/detect-end-of-scrollview */

public class TermsAndCondition extends AppCompatActivity {

    TextView below3, below5;
    Button button;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissionscreen);
//        button = findViewById(R.id.button_agreetnc);
        button = findViewById(R.id.button_permission);
        below3 = findViewById(R.id.below3);
        below5 = findViewById(R.id.below5);
        scrollView = findViewById(R.id.scrollView_tnc);

        String mystring = " " +getResources().getString(R.string.terms_of_service) + " ";
//        String mystring=new String(" terms of service");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        below3.setText(content);

        String mystring1 = " " +getResources().getString(R.string.privacy_policy);
//        String mystring1=new String(" privacy policy.");
        SpannableString content1 = new SpannableString(mystring1);
        content1.setSpan(new UnderlineSpan(), 0, mystring1.length(), 0);
        below5.setText(content1);

//        textView = findViewById(R.id.textView_pp);
//        textView1 = findViewById(R.id.textView_terms);
//
//        String pp = "Introduction\n" +
//                "Eduvanz Financing Private Limited, a company incorporated under the Companies Act, 2013 and having\n" +
//                "its registered office at Office No. 801, Jai Antriksh, Makwana Road, Marol, Andheri East, Mumbai-400059\n" +
//                "(“Eduvanz”) is committed to protection your privacy. The contents of this privacy policy (“Privacy\n" +
//                "Policy”) along with the Terms and Conditions (“Terms and Conditions”) available at\n" +
//                "www.eduvanz.com and the Eduvanz mobile application (collectively referred to as “Application”) shall\n" +
//                "be applicable to all users of the Website. By clicking on the “I accept” button, you accept this Privacy\n" +
//                "Policy and agree to be legally bound by the same. Please note that we do not collect personal or sensitive\n" +
//                "data collection prior to obtaining your consent of this Privacy Policy, in the manner specified above.\n" +
//                "This Privacy Policy states the following:\n" +
//                "i. The type of information collected from the users, including personal information and sensitive\n" +
//                "personal data or information relating to an individual;\n" +
//                "ii. The purpose, means and modes of collection, usage, processing, retention and destruction of such\n" +
//                "information; and\n" +
//                "iii. How and to whom Eduvanz will disclose such information.\n" +
//                "We respect your privacy and recognize the need to protect the personal information which you share\n" +
//                "with us. We would like to assure you that we follow appropriate and necessary standards when it comes\n" +
//                "to protecting your privacy on the Application. For the purposes of this Privacy Policy, inventory of\n" +
//                "installed applications and your phone or contact book data shall be deemed to be personal or sensitive\n" +
//                "user data.\n" +
//                "Consent\n" +
//                "By signifying Your acceptance to the provisions of the Privacy Policy and by providing your personal\n" +
//                "information, You consent to the collection and use of such personal information in accordance with this\n" +
//                "Privacy Policy, including but not limited to your consent for sharing your personal information as per\n" +
//                "this Privacy Policy. You specifically agree and consent to us collecting, storing, processing, transferring\n" +
//                "and sharing information (including personal information) related to you with third parties as set out in\n" +
//                "this Privacy Policy.\n" +
//                "All information, including personal information, disclosed by you shall be deemed to be disclosed\n" +
//                "willingly and without any coercion. No liability pertaining to the authenticity/ genuineness/\n" +
//                "misrepresentation/ fraud/ negligence, etc. of the information disclosed shall lie on Eduvanz nor will\n" +
//                "Eduvanz in any way be responsible to verify any information obtained from you.\n" +
//                "You represent and warrant that any and all information, including but not limited to your personal\n" +
//                "information is absolutely correct and complete in all aspects. You further undertake to immediately\n" +
//                "update Eduvanz of any change or variation of your personal information.\n" +
//                "You can also anytime withdraw the consent you provided by sending an email at [\uF097] and making a\n" +
//                "request for termination of services provided by us.\n" +
//                "In case you do not provide your consent or later withdraw your consent, we request you not to access the\n" +
//                "Application and use the Services and also reserve the right to not provide you any Services on the\n" +
//                "Application. In such a scenario, we may delete your information (personal information or otherwise) or\n" +
//                "de-identify it so that it is anonymous and not attributable to you.\n" +
//                "What information we collect\n" +
//                "In order to provide the Services as well as improve our Services, we collect information about you from\n" +
//                "primarily two sources: (i) information you affirmatively give to us; and (ii) information automatically\n" +
//                "collected when you visit our Application or use any of our Services. Third parties may also collect Your\n" +
//                "information in connection with Your use of the Services.\n" +
//                "a. In general, you can use this Application without telling us about yourself or revealing your personal\n" +
//                "information.\n" +
//                "b. In order to provide the Services, we may collect the following types of information from you (as\n" +
//                "applicable) including but not limited to your name, email address, age, mobile and/or telephone\n" +
//                "numbers, company name or affiliation, etc. We may ask you to provide additional information about\n" +
//                "yourself, if required for the purpose of providing Services.\n" +
//                "c. We track the internet protocol address from which users/individuals access our Application and\n" +
//                "analyze this data for trends and statistics independently or through third party service provider, but\n" +
//                "the individual user remains anonymous. We may collect information sent to us by your computer,\n" +
//                "mobile phone or any other device which may include, but is not limited to, your internet protocol\n" +
//                "address, device information including but not limited to identifier, name and type, operating system,\n" +
//                "location, mobile network information, call log data, contacts on your device, device application\n" +
//                "usage, short messaging service messages and standard web log information such as your browser\n" +
//                "type, traffic to and from our Application, the pages you accessed on our Application, websites you\n" +
//                "visited before opening the Application, and the webpages you access after you leave the Application\n" +
//                "and any other available information. If you have activated the location service parameters on your\n" +
//                "devices, these can also send us information respecting your geographical location.\n" +
//                "d. We may also collect information about your use and interaction with our Application and the\n" +
//                "services provided by Eduvanz pursuant to the Application (the “Services”). For example, we may\n" +
//                "evaluate your computer, mobile phone or other access device to identify any malicious software or\n" +
//                "activity that may affect the availability of the Services. When you use the Services, we may also\n" +
//                "collect information about you from any contact you have with any of our services or employees, such\n" +
//                "as, with our customer support team, in surveys, or though interactions with our affiliates.\n" +
//                "Why we collect information\n" +
//                "We primarily collect your information to provide you the Services and develop and improve our Services\n" +
//                "to enhance and customize your experience of using the Application. We develop statistics that are helpful\n" +
//                "to understand how our visitors can use this Application. We use this information in the aggregate to\n" +
//                "measure the use of the Application and to administer and improve the same. This statistical data is\n" +
//                "interpreted by us in its continuing effort to present the Application content that visitors are seeking in a\n" +
//                "format they find most helpful.\n" +
//                "You agree that we or our third party service providers collect your internet protocol address or other\n" +
//                "unique identifier. At times, we also use these identifiers to collect information regarding the frequency\n" +
//                "with which our guests visit various parts of the websites. We, either through ourselves or our affiliates or\n" +
//                "group companies may combine your internet protocol address with other personal information for\n" +
//                "Service related activities, marketing, for market research purposes, including internal demographic\n" +
//                "studies, so we can optimize our products and services and customize the Application for you. Further, we\n" +
//                "use the same information or provide the same to third party contractors which are bound by the\n" +
//                "restrictions set out in this Privacy Policy to provide various value added services, for market research,\n" +
//                "project planning, troubleshooting problems, detecting and protecting against error, fraud or other\n" +
//                "criminal activity. \n" +
//                "We may combine your visitor session information or other information collected through cookies, web\n" +
//                "beacons and other technical methods with any personal information in order to understand and improve\n" +
//                "your online experiences and to determine what products, promotions and services you prefer or are\n" +
//                "likely to be of interest to you.\n" +
//                "We never publicly disclose any personal information related to financial or payment activities or any\n" +
//                "government identification numbers.\n" +
//                "We also use these technical methods in Hypertext Markup Language (“HTML”) e-mails that we send you\n" +
//                "for a number of purposes including to determine whether you have opened or forwarded those e-mails\n" +
//                "and/or clicked on links in those e-mails; to customise the display of the banner advertisements and other\n" +
//                "messages after you have closed an email; and to determine whether you have made an enquiry or\n" +
//                "purchase in response to a particular email.\n" +
//                "We shall be entitled to retain your personal information only for such duration as may be required for the\n" +
//                "purposes specified hereunder and shall be used us only in consonance with this Privacy Policy.\n" +
//                "Cookies\n" +
//                "Some of our web pages including our third party service providers store and use \"cookies\" so that we can\n" +
//                "better serve you with customized information when you return to the Application. Cookies are identifiers\n" +
//                "that are stored on your computer’s browser directory. They are created when you use your browser to\n" +
//                "visit our Application. They keep track of your movements within our Application, help you resume\n" +
//                "where you left off, remember your registered login and other functions. You can set your browser to\n" +
//                "notify you when you are sent a cookie, giving you the option to decide whether or not to accept it. You\n" +
//                "may get cookies from our advertisers. We do not control these cookies, and once you have clicked on the\n" +
//                "advertisement and left our Application, our Privacy Policy no longer applies.\n" +
//                "Sharing of Information\n" +
//                "We may use the personal information that you have shared during the process of registration or\n" +
//                "otherwise for verification purposes and the user/ customer agrees and allows Eduvanz to share such\n" +
//                "information with third parties solely for verification purposes, credit reference companies and with\n" +
//                "government agencies, including but not limited to the Reserve Bank of India (“RBI”) for the cause of an\n" +
//                "inspection or any other legal diligences which may be conducted by it. We may also use the information\n" +
//                "you provide to update our consumer credit database.\n" +
//                "When you visit the Application, you agree that we or any of our partners/affiliates/group\n" +
//                "companies/subsidiaries/holding company/collection agents may contact you from time to time to\n" +
//                "provide the offers/information of such products/Services that Eduvanz believes may benefit you or to\n" +
//                "get information about any repayments due or any other promotional or transactional information\n" +
//                "Eduvanz wishes to receive from you. You agree that we may provide information collected from you to\n" +
//                "government authorities or credit bureaus as per the rules laid down by RBI or any court of law as per the\n" +
//                "applicable laws of India. When you visit this Application, you agree that we or any of our partners/\n" +
//                "affiliate/ group companies may contact you from time to time to provide the offers/ information of such\n" +
//                "products/ services that we believe may benefit you.\n" +
//                "It is possible that we may need to disclose personal information when required by law, such as responses\n" +
//                "to civil or criminal subpoenas, or other requests by law enforcement personnel. We will disclose such\n" +
//                "information wherein we have a good faith/ belief that it is necessary to comply with a court order,\n" +
//                "ongoing judicial proceeding, subpoena, or other legal process, or to exercise our legal rights or defend\n" +
//                "against legal claims.\n" +
//                "Disclosure to Third Parties\n" +
//                "We may disclose your personal information to third parties in the following events:\n" +
//                "i. If Eduvanz sells or buys any business or assets, in which case Eduvanz may disclose your\n" +
//                "personal data to the prospective seller or buyer of such business or assets;\n" +
//                "ii. If Eduvanz or substantially all of Eduvanz’s assets are acquired by a third party, in which case\n" +
//                "personal data held by it about its customers will be one of the transferred assets;\n" +
//                "iii. If Eduvanz is under a duty to disclose or share your personal data in order to comply with any\n" +
//                "legal or regulatory obligation or request; and/or\n" +
//                "iv. In order to report defaulters to any credit bureau as require under applicable laws; or for the\n" +
//                "purpose of publishing statistics relating to the use of the Application, in which case all\n" +
//                "information will be aggregated and made anonymous.\n" +
//                "Link to Third Party Websites\n" +
//                "We may link to third-party websites. These third-party websites may collect information about you on\n" +
//                "those websites, and our Privacy Policy does not extend to such third party websites. We are not\n" +
//                "responsible for the content of these third-party websites or for the security of your information when you\n" +
//                "use the third party websites. Please refer directly to the websites of such third parties and their privacy\n" +
//                "policies. You agree and acknowledge that we are not be liable for the information published in search\n" +
//                "results or by any third-party website.\n" +
//                "Security Measures and Procedures\n" +
//                "We will be only using the information provided by you for the purpose(s) which you have consented to\n" +
//                "do under this Privacy Policy. We will not sell, trade or disclose to third parties any personal information\n" +
//                "derived from the registration for, or use of, any online service (including names and addresses) without\n" +
//                "your consent save and except to any of our affiliates or group companies for helping you in receiving\n" +
//                "loans or other purposes in relation to the businesses of our affiliates or group companies and as may be\n" +
//                "required by legal process or in the case of imminent physical harm to any person). Further, Eduvanz will\n" +
//                "allow its affiliates to access the information for purposes of confirming your registration and providing\n" +
//                "you with benefits you are entitled to.\n" +
//                "We will use reasonable security measures and safeguards and take appropriate steps to protect the\n" +
//                "information you share with us. We have implemented technology and security features and strict policy\n" +
//                "guidelines to safeguard the privacy of your personal information from unauthorized access and improper\n" +
//                "use or disclosure, in compliance with applicable laws. For this purpose, we undertake internal reviews of\n" +
//                "the data collection, storage and processing practices and security measures, including appropriate\n" +
//                "encryption and physical security measures to guard against unauthorized access to systems where we\n" +
//                "store your personal information. We will continue to enhance our security procedures as new technology\n" +
//                "becomes available. We cannot however ensure or warrant the security of any information you transmit to\n" +
//                "the Eduvanz or guarantee that your personal information and/or information provided for availing the\n" +
//                "Services or Application may not be accessed, disclosed, altered or destroyed by a breach of any of our\n" +
//                "security measures and safeguards.\n" +
//                "We may transmit the information collected from you through the internet to another country, but among\n" +
//                "our affiliates and group companies that ensures same level of data protection that is adhered to by\n" +
//                "Eduvanz, for purposes such as for storage, or for carrying out the processing detailed above, or because\n" +
//                "of where our servers are located.\n" +
//                "It is further clarified that you have and so long as you access and/or use the Application (directly or\n" +
//                "indirectly) the obligation to ensure that you shall at all times, take adequate physical, and technical\n" +
//                "safeguards, at your end, to preserve the integrity and security of your data which shall include and not be\n" +
//                "limited to your personal information.\n" +
//                "Changes to Privacy Policy\n" +
//                "We may change this Privacy Policy from time to time without requiring any consent from anyone. If our\n" +
//                "privacy policy changes in the future, it will be posted here and a new effective date will be shown. You\n" +
//                "should access our Privacy Policy regularly to ensure you understand our current policies. Your\n" +
//                "acceptance of the amended Privacy Policy shall signify your consent to such changes and agreement to be\n" +
//                "legally bound by the same.\n" +
//                "Please reference the privacy policy in your subject line. Eduvanz will attempt to respond to all reasonable\n" +
//                "concerns or inquiries on a best efforts basis within a reasonable period of time.\n" +
//                "Grievance Officer\n" +
//                "Details of the Grievance Officer of the Company are provided below:\n" +
//                "Mr. Atul Sashittal\n" +
//                "Eduvanz Financing Private Limited\n" +
//                "Office No. 801, Jai Antriksh,\n" +
//                "Makwana Road, Marol, Andheri East,\n" +
//                "Mumbai 400059\n" +
//                "Phone: 91-22-49733674\n" +
//                "Email: atul.sashittal@eduvanz.com\n" +
//                "You may contact the Grievance Officer in relation to any grievances that you may have in relation to this\n" +
//                "Privacy Policy.";
//

//        String first = "Tap Agree and Continue to accept the ";
//        String next = "<font color='#EE415E'>Eduvanz Terms and Conditions</font>";
//        textView.setText(Html.fromHtml(first + next));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("userpolicyAgreement", 1);
                    editor.apply();
                    editor.commit();

                Intent intent = new Intent(TermsAndCondition.this,
                        SingInWithTruecaller.class);
//                Intent intent = new Intent(TermsAndCondition.this,
//                        GetMobileNo.class);
                startActivity(intent);

            }
        });

        below3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("userpolicyAgreement", 1);
//                editor.apply();
//                editor.commit();

                Intent intent = new Intent(TermsAndCondition.this,
                        WebViewTermsNCondition.class);
                startActivity(intent);

            }
        });

        below5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("userpolicyAgreement", 1);
//                editor.apply();
//                editor.commit();

                Intent intent = new Intent(TermsAndCondition.this,
                        WebViewPrivacyPolicy.class);
                startActivity(intent);
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView != null) {
                    if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollView.getScrollY())) {
                        //scroll view is at bottom
                        button.setVisibility(View.VISIBLE);
                    } else {
                        //scroll view is not at bottom
                    }
                }
            }
        });

//        SpannableString tnc = new SpannableString("Terms and Conditions");
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//                String url = "https://eduvanz.com/demo/terms_condition";
//
//                try {
//                    Intent i = new Intent("android.intent.action.MAIN");
//                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
//                    i.addCategory("android.intent.category.LAUNCHER");
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
//                }
//                catch(ActivityNotFoundException e) {
//                    // Chrome is not installed
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(i);
//                }
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//        };
//        tnc.setSpan(clickableSpan, 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        textView.setText(pp + "\n");
//        textView1.setText(tnc);
//        textView1.setMovementMethod(LinkMovementMethod.getInstance());
//        textView1.setHighlightColor(Color.TRANSPARENT);


//        SpannableString tnc = new SpannableString("Tap Agree and Continue to accept the Eduvanz Terms and Conditions and Privacy Policy");
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//                String url = "https://eduvanz.com/demo/terms_condition";
//
//                try {
//                    Intent i = new Intent("android.intent.action.MAIN");
//                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
//                    i.addCategory("android.intent.category.LAUNCHER");
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
//                }
//                catch(ActivityNotFoundException e) {
//                    // Chrome is not installed
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(i);
//                }
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//        };
//
//        ClickableSpan clickableSpan2 = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//                String url = "https://eduvanz.com/demo/privacy_policy";
//                try {
//                    Intent i = new Intent("android.intent.action.MAIN");
//                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
//                    i.addCategory("android.intent.category.LAUNCHER");
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
//                }
//                catch(ActivityNotFoundException e) {
//                    // Chrome is not installed
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(i);
//                }
//            }
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//        };
//        tnc.setSpan(clickableSpan, 45, 65, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tnc.setSpan(clickableSpan2, 70, 84, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



//        textView.setText(tnc);
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setHighlightColor(Color.TRANSPARENT);

    }
}
