package net.hundredapps.customdialoganimation;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import net.hundredapps.CustomDialogAnimationLib.CustomDialogAnimation;
import net.hundredapps.CustomDialogAnimationLib.DialogListener;

public class MainActivity extends FragmentActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.anim_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomDialogAnimation.Builder(MainActivity.this, getString(R.string.title), getString(R.string.message))
                        .layout(R.layout.dummy_layout_1)
                        .animation(true)
                        .listener(eventListener())
                        .cancelable(false)
                        .positive(getString(R.string.positive_button_label))
                        .negative(getString(R.string.negative_button_label))
                        .show();
            }
        });
    }

    private DialogListener eventListener() {
        return new DialogListener() {
            @Override
            public void onDialogButtonTap(DialogButtonAction action) {
                Log.d(TAG, "BUTTON TAP");
                Toast.makeText(MainActivity.this, "DialogButtonAction action: " + action, Toast.LENGTH_LONG).show();
                if (DialogButtonAction.POSITIVE_BUTTON.equals(action)) {
                    Log.d(TAG, "POSITIVE_BUTTON TAP");
                } else if (DialogButtonAction.CANCEL_BUTTON.equals(action)) {
                    Log.d(TAG, "CANCEL_BUTTON TAP");
                } else if (DialogButtonAction.LATER_BUTTON.equals(action)) {
                    Log.d(TAG, "LATER_BUTTON TAP");
                }
            }
            @Override
            public int describeContents() {
                return 0;
            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {}
        };
    }
}