package com.smartbizz.faqexpandablelistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vijay on 17/7/17.
 */

class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

//        List<String> cricket = new ArrayList<String>();
//        cricket.add("India");
//        cricket.add("Pakistan");
//        cricket.add("Australia");
//        cricket.add("England");
//        cricket.add("South Africa");
//
//        List<String> football = new ArrayList<String>();
//        football.add("Brazil");
//        football.add("Spain");
//        football.add("Germany");
//        football.add("Netherlands");
//        football.add("Italy");
//
//        List<String> basketball = new ArrayList<String>();
//        basketball.add("United States");
//        basketball.add("Spain");
//        basketball.add("Argentina");
//        basketball.add("France");
//        basketball.add("Russia");
//
//        expandableListDetail.put("CRICKET TEAMS", cricket);
//        expandableListDetail.put("FOOTBALL TEAMS", football);
//        expandableListDetail.put("BASKETBALL TEAMS", basketball);


        List<String> cricket = new ArrayList<String>();
        cricket.add("Any Indian national between the age of 18-45, wishing to pursue any vocational degree or skill development course at any Institute either recognized or partnered with Eduvanz is eligible to apply for a losan from Eduvanz.");
        cricket.add("A Co-Applicant is mandatory if the applicant is not working or has no prior work experience.");
//        cricket.add("Australia");
//        cricket.add("England");
//        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Eduvanz provides such loans to the deserving and needy candidates at ZERO PERCENT interest so that they can easily pursue their dream career without any interest burden.");
//        football.add("Spain");
//        football.add("Germany");
//        football.add("Netherlands");
//        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("An education loan is needed to provide that much needed financial support to enable any deserving student in pursuing any sort of Educational or Vocational courses to enhance his/her career prospects");
//        basketball.add("Spain");
//        basketball.add("Argentina");
//        basketball.add("France");
//        basketball.add("Russia");

        List<String> test = new ArrayList<String>();
        test.add("We firmly believe that the right support provided to the right individual at the right time ensures success. No deserving student should be stopped from pursuing their dreams due to a lack of money!");
        test.add("Education loans are classified as priority sector today by the Indian Government and are to be encouraged. These loans are to be treated as investment for future economic development and prosperity. Most of the developed and developing countries have student loan schemes to benefit deserving students for the progress of the Individual, Society and thereby the Nation as a whole. In India only 1% students receive education loans. Today, most of the students are denied an education loan.");
        test.add("To enable this Vision of Growth and Progress Eduvanz gives deserving applicants access to such loans so that they can fly towards their dreams without any interest burden on their wings.");

        expandableListDetail.put("What is the eligibility criteria to avail an education loan? Am I eligible for one?", cricket);
        expandableListDetail.put("What does Eduvanz do?", football);
        expandableListDetail.put("Why do I need an education loan?", basketball);
        expandableListDetail.put("Why does Eduvanz give loans at 0% interest?", test);


        return expandableListDetail;
    }
}
