package pwr.billsmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ScanResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_result);

        String strScanData = getIntent().getExtras().getString("scan_data");

        ((TextView)findViewById(R.id.txtScanResult)).setText(strScanData);
    }
}
