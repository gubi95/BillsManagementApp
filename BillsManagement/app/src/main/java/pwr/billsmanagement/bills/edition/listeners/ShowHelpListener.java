package pwr.billsmanagement.bills.edition.listeners;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import pwr.billsmanagement.R;

/**
 * Created by Squier on 22.05.2017.
 */
public class ShowHelpListener implements View.OnClickListener {

    private View parentView;
    private LayoutInflater inflater;
    private PopupWindow popupWindow;
    private String helpMessage;
    private WindowManager windowManager;

    @Override
    public void onClick(View view) {
        View popupView = inflater.inflate(R.layout.bill_edit_help_popup, null);

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        popupWindow = new PopupWindow(
                popupView, metrics.widthPixels - 100, metrics.heightPixels - 200
        );

        popupWindow.setElevation(5.0f);

        ImageView icon = (ImageView) popupView.findViewById(R.id.helpIcon);
        icon.setImageResource(R.mipmap.ic_show_help_dark);

        Button closeButton = (Button) popupView.findViewById(R.id.closeHelp);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());

        TextView helpText = (TextView) popupView.findViewById(R.id.helpMessage);
        helpText.setText(helpMessage);

        popupWindow.showAtLocation(
                parentView, Gravity.CENTER, 0, 0
        );

    }

    public void setParentView(View parentView) {
        this.parentView = parentView;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }
}
