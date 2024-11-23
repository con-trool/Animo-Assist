// Generated by view binder compiler. Do not edit!
package com.mobdeve.s13.grp50.mc02_mobdeve.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.mobdeve.s13.grp50.mc02_mobdeve.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnSubmit;

  @NonNull
  public final TextView createAnAccount;

  @NonNull
  public final ImageView downbanner;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final ImageView mainlogo;

  @NonNull
  public final ImageView upbanner;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnSubmit,
      @NonNull TextView createAnAccount, @NonNull ImageView downbanner,
      @NonNull ConstraintLayout main, @NonNull ImageView mainlogo, @NonNull ImageView upbanner) {
    this.rootView = rootView;
    this.btnSubmit = btnSubmit;
    this.createAnAccount = createAnAccount;
    this.downbanner = downbanner;
    this.main = main;
    this.mainlogo = mainlogo;
    this.upbanner = upbanner;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSubmit;
      Button btnSubmit = ViewBindings.findChildViewById(rootView, id);
      if (btnSubmit == null) {
        break missingId;
      }

      id = R.id.create_an_account;
      TextView createAnAccount = ViewBindings.findChildViewById(rootView, id);
      if (createAnAccount == null) {
        break missingId;
      }

      id = R.id.downbanner;
      ImageView downbanner = ViewBindings.findChildViewById(rootView, id);
      if (downbanner == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.mainlogo;
      ImageView mainlogo = ViewBindings.findChildViewById(rootView, id);
      if (mainlogo == null) {
        break missingId;
      }

      id = R.id.upbanner;
      ImageView upbanner = ViewBindings.findChildViewById(rootView, id);
      if (upbanner == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, btnSubmit, createAnAccount,
          downbanner, main, mainlogo, upbanner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
