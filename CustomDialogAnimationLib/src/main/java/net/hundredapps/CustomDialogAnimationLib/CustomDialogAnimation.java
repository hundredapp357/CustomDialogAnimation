package net.hundredapps.CustomDialogAnimationLib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * common DialogFragment. call from activity.
 * this class use DialogListener(interface).
 * implements DialogListener(is positive button and negative button event) in activity.
 * Created by yamashu on 2016/11/19.
 */
public class CustomDialogAnimation extends android.support.v4.app.DialogFragment {
    public DialogListener mDialogListener = new DefaultDialogListener();

    public static class Builder {

        final FragmentActivity mActivity; // required

        String mTitle; // required

        String mMessage; // required

        int mLayoutResourceId;

        DialogListener mDialogListener;

        String mPositiveLabel;  // required

        String mNegativeLabel; // required

        // prevent double startup
        String mTag = "default";

        boolean mCancelable = true;

        boolean mAnimationFlg = false;

        public Builder(@NonNull final FragmentActivity activity, @NonNull final String title, @NonNull final String message) {
            mActivity = activity;
            mTitle = title;
            mMessage = message;
        }

        public Builder listener(@NonNull final DialogListener listener) {
            mDialogListener = listener;
            return this;
        }

        public Builder layout(final int layout) {
            mLayoutResourceId = layout;
            return this;
        }
        public Builder positive(@NonNull final String positiveLabel) {
            mPositiveLabel = positiveLabel;
            return this;
        }

        public Builder negative(@NonNull final String negativeLabel) {
            mNegativeLabel = negativeLabel;
            return this;
        }

        public Builder tag(final String tag) {
            mTag = tag;
            return this;
        }

        public Builder animation(final boolean animationFlg) {
            mAnimationFlg = animationFlg;
            return this;
        }

        public Builder cancelable(final boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public void show() throws IllegalStateException {
            final Bundle args = new Bundle();
            args.putString("title", mTitle);
            args.putString("message", mMessage);
            args.putInt("layout_resource_id", mLayoutResourceId);
            args.putParcelable("dialog_listener", mDialogListener);
            args.putString("positive_label", mPositiveLabel);
            args.putString("negative_label", mNegativeLabel);
            args.putBoolean("animation_flg", mAnimationFlg);
            args.putBoolean("cancelable", mCancelable);

            final CustomDialogAnimation f = new CustomDialogAnimation();
            f.setArguments(args);
            f.show(mActivity.getSupportFragmentManager(), mTag);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        removeListener();
    }

    private boolean mAnimationFlg;
    private int mLayoutResourceId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String title = getArguments().getString("title");
        final String message = getArguments().getString("message");
        mLayoutResourceId = getArguments().getInt("layout_resource_id", 0);
        mDialogListener = getArguments().getParcelable("dialog_listener");
        mAnimationFlg = getArguments().getBoolean("animation_flg", false);
        final String positiveLabel = getArguments().getString("positive_label");
        final String negativeLabel = getArguments().getString("negative_label");
        setCancelable(getArguments().getBoolean("cancelable"));
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE); // don't display title
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN); // full screen
        if (mLayoutResourceId == 0) {
            dialog.setContentView(R.layout.common_dialog);
        } else {
            dialog.setContentView(mLayoutResourceId);
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // make the background transparent.

        // title
        TextView titleView = (TextView) dialog.findViewById(R.id.title);
        titleView.setText(title);

        // message
        TextView messageView = (TextView) dialog.findViewById(R.id.message);
        messageView.setText(message);

        // positive button
        if (positiveLabel != null) {
            Button positiveButton = (Button) dialog.findViewById(R.id.positive_button);
            positiveButton.setText(positiveLabel);
        }

        // negative button
        Button negativeButton = (Button) dialog.findViewById(R.id.negative_button);
        if (negativeLabel != null) {
            negativeButton.setText(negativeLabel);
        } else {
            negativeButton.setVisibility(View.GONE);
        }

        setDialogEvent(dialog);

        return dialog;
    }

    private void setDialogEvent(Dialog dialog) {
        dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogListener.onDialogButtonTap(DialogListener.DialogButtonAction.POSITIVE_BUTTON);
                dismiss();
            }
        });

        dialog.findViewById(R.id.negative_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogListener.onDialogButtonTap(DialogListener.DialogButtonAction.CANCEL_BUTTON);
                dismiss();
            }
        });

        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogListener.onDialogButtonTap(DialogListener.DialogButtonAction.CANCEL_BUTTON);
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null) {
            return;
        }

        // show and hide the animation dialog
        if (mAnimationFlg) {
            getDialog().getWindow().setWindowAnimations(
                    R.style.dialog_animation_fade);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mDialogListener.onDialogButtonTap(DialogListener.DialogButtonAction.CANCEL_BUTTON);
    }

    /**
     * remove mDialogListener
     */
    private void removeListener(){
        this.mDialogListener = null;
    }

}