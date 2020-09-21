package com.smartbizz.Util;

import android.content.Context;
import android.view.View;

public class ResourceUtil {

   /* public static int getLoanStatusSelector(int status) {
        switch (status) {

        }
    }

    public int getLoanStatusTextColor(int status) {
        switch (status)
    }*/

   public static int getErrorView(Context context, View view){
       int id = context.getResources().getIdentifier(view.getTag().toString(), "id", context.getPackageName());
       return id;
   }
}
