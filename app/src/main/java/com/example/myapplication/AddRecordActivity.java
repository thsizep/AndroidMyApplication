package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class AddRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        EditText projectName = findViewById(R.id.etProjectName);
        EditText deposit = findViewById(R.id.etDeposit);
        EditText balance = findViewById(R.id.etBalance);
        EditText paymentDate = findViewById(R.id.etPaymentDate);
        Spinner type = findViewById(R.id.spinnerType);
        Button save = findViewById(R.id.btnSaveRecord);

        String[] types = {"网购消费", "设计制作", "培训服务", "设备采购", "其他"};
        type.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, types));

        save.setOnClickListener(v -> {
            String name = projectName.getText().toString().trim();
            String depositText = deposit.getText().toString().trim();
            String balanceText = balance.getText().toString().trim();
            String date = paymentDate.getText().toString().trim();

            if (name.isEmpty() || depositText.isEmpty() || balanceText.isEmpty()
                    || date.isEmpty()) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double depositValue = Double.parseDouble(depositText);
                double balanceValue = Double.parseDouble(balanceText);
                if (depositValue < 0 || balanceValue < 0) {
                    throw new NumberFormatException();
                }
                Record record = new Record(System.currentTimeMillis(), name, depositValue,
                        balanceValue, date, type.getSelectedItem().toString(), "待缴纳");
                RecordStorage.add(this, record);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, QueryActivity.class);
                intent.putExtra("new_record_id", record.getId());
                startActivity(intent);
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "金额必须为非负数字", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        });

        Button back = findViewById(R.id.btnBackHome);
        back.setOnClickListener(v -> finish());
    }
}
