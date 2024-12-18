// Generated by view binder compiler. Do not edit!
package com.mobdeve.s13.grp50.mc02_mobdeve.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public final class ActivitySignupPageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView alreadyHaveAccount;

  @NonNull
  public final TextView createAccount;

  @NonNull
  public final EditText editTextTextEmailAddress;

  @NonNull
  public final EditText editTextTextPassword;

  @NonNull
  public final EditText editTextcourse;

  @NonNull
  public final EditText editTextfirstname;

  @NonNull
  public final EditText editTextidnum;

  @NonNull
  public final EditText editTextlastname;

  @NonNull
  public final ConstraintLayout linearLayout;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final Button registerbtn;

  @NonNull
  public final TextView signin;

  @NonNull
  public final TextView signup;

  @NonNull
  public final ImageView signupBanner;

  private ActivitySignupPageBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView alreadyHaveAccount, @NonNull TextView createAccount,
      @NonNull EditText editTextTextEmailAddress, @NonNull EditText editTextTextPassword,
      @NonNull EditText editTextcourse, @NonNull EditText editTextfirstname,
      @NonNull EditText editTextidnum, @NonNull EditText editTextlastname,
      @NonNull ConstraintLayout linearLayout, @NonNull ConstraintLayout main,
      @NonNull Button registerbtn, @NonNull TextView signin, @NonNull TextView signup,
      @NonNull ImageView signupBanner) {
    this.rootView = rootView;
    this.alreadyHaveAccount = alreadyHaveAccount;
    this.createAccount = createAccount;
    this.editTextTextEmailAddress = editTextTextEmailAddress;
    this.editTextTextPassword = editTextTextPassword;
    this.editTextcourse = editTextcourse;
    this.editTextfirstname = editTextfirstname;
    this.editTextidnum = editTextidnum;
    this.editTextlastname = editTextlastname;
    this.linearLayout = linearLayout;
    this.main = main;
    this.registerbtn = registerbtn;
    this.signin = signin;
    this.signup = signup;
    this.signupBanner = signupBanner;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.already_have_account;
      TextView alreadyHaveAccount = ViewBindings.findChildViewById(rootView, id);
      if (alreadyHaveAccount == null) {
        break missingId;
      }

      id = R.id.create_account;
      TextView createAccount = ViewBindings.findChildViewById(rootView, id);
      if (createAccount == null) {
        break missingId;
      }

      id = R.id.editTextTextEmailAddress;
      EditText editTextTextEmailAddress = ViewBindings.findChildViewById(rootView, id);
      if (editTextTextEmailAddress == null) {
        break missingId;
      }

      id = R.id.editTextTextPassword;
      EditText editTextTextPassword = ViewBindings.findChildViewById(rootView, id);
      if (editTextTextPassword == null) {
        break missingId;
      }

      id = R.id.editTextcourse;
      EditText editTextcourse = ViewBindings.findChildViewById(rootView, id);
      if (editTextcourse == null) {
        break missingId;
      }

      id = R.id.editTextfirstname;
      EditText editTextfirstname = ViewBindings.findChildViewById(rootView, id);
      if (editTextfirstname == null) {
        break missingId;
      }

      id = R.id.editTextidnum;
      EditText editTextidnum = ViewBindings.findChildViewById(rootView, id);
      if (editTextidnum == null) {
        break missingId;
      }

      id = R.id.editTextlastname;
      EditText editTextlastname = ViewBindings.findChildViewById(rootView, id);
      if (editTextlastname == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      ConstraintLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.registerbtn;
      Button registerbtn = ViewBindings.findChildViewById(rootView, id);
      if (registerbtn == null) {
        break missingId;
      }

      id = R.id.signin;
      TextView signin = ViewBindings.findChildViewById(rootView, id);
      if (signin == null) {
        break missingId;
      }

      id = R.id.signup;
      TextView signup = ViewBindings.findChildViewById(rootView, id);
      if (signup == null) {
        break missingId;
      }

      id = R.id.signup_banner;
      ImageView signupBanner = ViewBindings.findChildViewById(rootView, id);
      if (signupBanner == null) {
        break missingId;
      }

      return new ActivitySignupPageBinding((ConstraintLayout) rootView, alreadyHaveAccount,
          createAccount, editTextTextEmailAddress, editTextTextPassword, editTextcourse,
          editTextfirstname, editTextidnum, editTextlastname, linearLayout, main, registerbtn,
          signin, signup, signupBanner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
