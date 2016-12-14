package in.aqel.smartsilencer.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahammad on 14/12/16.
 */

public class PrefUtils {

    public static String sp = "sharedPrefs";
    public static String spPhoneState = "phoneState";

    public static void savePrefInt(Context context, String key, int value){
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getPref(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getInt(key, -1);
    }

}
