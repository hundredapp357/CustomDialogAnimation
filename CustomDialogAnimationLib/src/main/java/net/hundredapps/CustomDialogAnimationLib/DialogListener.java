package net.hundredapps.CustomDialogAnimationLib;

/**
 * Created by shu on 2016/11/20.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * リスナ
 */
public interface DialogListener extends Parcelable {

    enum DialogButtonAction {
        POSITIVE_BUTTON,
        CANCEL_BUTTON,
        LATER_BUTTON
    }

    // button event
    void onDialogButtonTap(DialogButtonAction action);
}

/**
 * 指定されてなければこのクラスを使用する
 */
class DefaultDialogListener implements DialogListener {
    public static final String TAG = DefaultDialogListener.class.getSimpleName();

    @Override
    public void onDialogButtonTap(DialogButtonAction action) {
        Log.d(TAG, "DefaultDialogListener ButtonAction " + action);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {}

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DefaultDialogListener createFromParcel(Parcel in) {
            return new DefaultDialogListener();
        }

        public DefaultDialogListener[] newArray(int size) {
            return new DefaultDialogListener[size];
        }
    };
}

