package in.aqel.smartsilencer.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.OrientationEventListener;

import in.aqel.smartsilencer.Utils.PrefUtils;

public class MotionSensorService extends Service implements SensorEventListener {
    private static final String TAG = "MotionControlService";

    boolean mStarted;
    SensorManager mSensorManager;
    private boolean isSilent = false;
    private int mEventCountSinceGZChanged = 0;
    private int mOrientation;
    private static final int MAX_COUNT_GZ_CHANGE = 10;
    OrientationEventListener mOrientationListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                mOrientation = orientation;

            }
        };

        if (mOrientationListener.canDetectOrientation() == true) {
            Log.v(TAG, "Can detect orientation");
            mOrientationListener.enable();
        } else {
            Log.v(TAG, "Cannot detect orientation");
            mOrientationListener.disable();
        }

        if (!mStarted) {
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_UI);

            mStarted = true;
        }
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            float gz = event.values[2];
            if (mEventCountSinceGZChanged == MAX_COUNT_GZ_CHANGE){
                if (gz > 0 && isSilent) {
                    Log.d(TAG, "now screen is facing up.");
                    isSilent = false;
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(PrefUtils.getPref(this, PrefUtils.spPhoneState));
                } else if (gz < 0 && mOrientation == -1 && !isSilent) {
                    Log.d(TAG, "now screen is facing down.");
                    isSilent = true;
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    PrefUtils.savePrefInt(this, PrefUtils.spPhoneState, am.getRingerMode());
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                mEventCountSinceGZChanged = 0;

            } else mEventCountSinceGZChanged++;

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

}
