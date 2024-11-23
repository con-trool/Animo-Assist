// Generated by view binder compiler. Do not edit!
package com.mobdeve.s13.grp50.mc02_mobdeve.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ReportEntryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imgRate;

  @NonNull
  public final ImageView imgStatus;

  @NonNull
  public final TextView txtDate;

  @NonNull
  public final TextView txtStatus;

  @NonNull
  public final TextView txtTitle;

  private ReportEntryBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imgRate,
      @NonNull ImageView imgStatus, @NonNull TextView txtDate, @NonNull TextView txtStatus,
      @NonNull TextView txtTitle) {
    this.rootView = rootView;
    this.imgRate = imgRate;
    this.imgStatus = imgStatus;
    this.txtDate = txtDate;
    this.txtStatus = txtStatus;
    this.txtTitle = txtTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ReportEntryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ReportEntryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.report_entry, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ReportEntryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgRate;
      ImageView imgRate = ViewBindings.findChildViewById(rootView, id);
      if (imgRate == null) {
        break missingId;
      }

      id = R.id.imgStatus;
      ImageView imgStatus = ViewBindings.findChildViewById(rootView, id);
      if (imgStatus == null) {
        break missingId;
      }

      id = R.id.txtDate;
      TextView txtDate = ViewBindings.findChildViewById(rootView, id);
      if (txtDate == null) {
        break missingId;
      }

      id = R.id.txtStatus;
      TextView txtStatus = ViewBindings.findChildViewById(rootView, id);
      if (txtStatus == null) {
        break missingId;
      }

      id = R.id.txtTitle;
      TextView txtTitle = ViewBindings.findChildViewById(rootView, id);
      if (txtTitle == null) {
        break missingId;
      }

      return new ReportEntryBinding((ConstraintLayout) rootView, imgRate, imgStatus, txtDate,
          txtStatus, txtTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
