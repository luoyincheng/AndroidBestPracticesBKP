package yincheng.tinytank.ui.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yincheng.tinytank.R;
import yincheng.tinytank.provider.helper.ViewHelper;

/**
 * Created by yincheng on 2018/5/31/14:40.
 * github:luoyincheng
 */
@SuppressWarnings("RestrictedApi")
public abstract class BaseBottomSheetDialogFragment extends
		BottomSheetDialogFragment {
	protected BottomSheetBehavior<View> bottomSheetBehavior;
	protected boolean isAlreadyHidden;
	private final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new
			BottomSheetBehavior.BottomSheetCallback() {
				@Override
				public void onStateChanged(@NonNull View bottomSheet, int newState) {
					if (newState == BottomSheetBehavior.STATE_HIDDEN) {
						isAlreadyHidden = true;
						onHidden();
					}
				}

				@Override
				public void onSlide(@NonNull View bottomSheet, float slideOffset) {
					if (slideOffset == -1.0) {
						isAlreadyHidden = true;
						onDismissedByScrolling();
					}
				}
			};
	@Nullable
	private Unbinder unbinder;

	@LayoutRes
	protected abstract int layoutRes();

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
//      StateSaver.saveInstanceState(this, outState);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
//         StateSaver.restoreInstanceState(this, savedInstanceState);
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
			container, @Nullable Bundle savedInstanceState) {
		final Context contextThemeWrapper = new ContextThemeWrapper(getContext(), getContext()
				.getTheme());
		LayoutInflater themeAwareInflater = inflater.cloneInContext(contextThemeWrapper);
		View view = themeAwareInflater.inflate(layoutRes(), container, false);
		unbinder = ButterKnife.bind(this, view);
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
				.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				View parent = getDialog().findViewById(R.id.design_bottom_sheet);
				if (parent != null) {
					bottomSheetBehavior = BottomSheetBehavior.from(parent);
					if (bottomSheetBehavior != null) {
						bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
						bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
					}
				}
			}
		});
		return view;
	}

	@Override
	public void setupDialog(Dialog dialog, int style) {
		super.setupDialog(dialog, style);//@SuppressWarnings("RestrictedApi")
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (unbinder != null) unbinder.unbind();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setOnShowListener(dialogInterface -> {
			if (ViewHelper.isTablet(getActivity())) {
				if (dialog.getWindow() != null) {
					dialog.getWindow().setLayout(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.MATCH_PARENT);
				}
			}
			onDialogIsShowing();
		});
		dialog.setOnKeyListener((dialog1, keyCode, event) -> {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				isAlreadyHidden = true;
				onDismissedByScrolling();
			}
			return false;
		});
		return dialog;
	}

	@Override
	public void onDetach() {
		if (!isAlreadyHidden) {
			onDismissedByScrolling();
		}
		super.onDetach();
	}

	protected void onHidden() {
		try {
			dismiss();
		} catch (IllegalStateException ignored) {
		} //FML FIXME
	}

	protected void onDismissedByScrolling() {
	}

	private void onDialogIsShowing() {
	}

}
