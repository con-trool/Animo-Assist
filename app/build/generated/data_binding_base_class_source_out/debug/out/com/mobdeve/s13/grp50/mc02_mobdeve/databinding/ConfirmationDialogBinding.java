// Generated by view binder compiler. Do not edit!
package com.mobdeve.s13.grp50.mc02_mobdeve.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public final class ConfirmationDialogBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnCancel;

  @NonNull
  public final Button btnConfirm;

  @NonNull
  public final TextView txtConfirmationMessage;

  @NonNull
  public final TextView txtConfirmationTitle;

  private ConfirmationDialogBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnCancel,
      @NonNull Button btnConfirm, @NonNull TextView txtConfirmationMessage,
      @NonNull TextView txtConfirmationTitle) {
    this.rootView = rootView;
    this.btnCancel = btnCancel;
    this.btnConfirm = btnConfirm;
    this.txtConfirmationMessage = txtConfirmationMessage;
    this.txtConfirmationTitle = txtConfirmationTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ConfirmationDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ConfirmationDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.confirmation_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ConfirmationDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCancel;
      Button btnCancel = ViewBindings.findChildViewById(rootView, id);
      if (btnCancel == null) {
        break missingId;
      }

      id = R.id.btnConfirm;
      Button btnConfirm = ViewBindings.findChildViewById(rootView, id);
      if (btnConfirm == null) {
        break missingId;
      }

      id = R.id.txtConfirmationMessage;
      TextView txtConfirmationMessage = ViewBindings.findChildViewById(rootView, id);
      if (txtConfirmationMessage == null) {
        break missingId;
      }

      id = R.id.txtConfirmationTitle;
      TextView txtConfirmationTitle = ViewBindings.findChildViewById(rootView, id);
      if (txtConfirmationTitle == null) {
        break missingId;
      }

      return new ConfirmationDialogBinding((ConstraintLayout) rootView, btnCancel, btnConfirm,
          txtConfirmationMessage, txtConfirmationTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}