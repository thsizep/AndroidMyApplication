package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class QueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        TextView newRecord = findViewById(R.id.tvNewRecord);
        TextView emptyView = findViewById(R.id.tvEmpty);
        ListView recordList = findViewById(R.id.listRecords);
        RadioGroup statusGroup = findViewById(R.id.radioGroupStatus);
        long newRecordId = getIntent().getLongExtra("new_record_id", -1L);
        List<Record> records;
        boolean loadError = false;

        try {
            records = RecordStorage.getAll(this);
        } catch (JSONException e) {
            records = new ArrayList<>();
            loadError = true;
        }
        List<Record> allRecords = records;

        for (Record record : allRecords) {
            if (record.getId() == newRecordId) {
                newRecord.setText("本次新增：" + record.getProjectName());
                break;
            }
        }

        List<String> recordItems = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, recordItems);
        recordList.setAdapter(adapter);
        boolean finalLoadError = loadError;
        Runnable showRecords = () -> {
            recordItems.clear();
            boolean pendingOnly = statusGroup.getCheckedRadioButtonId() == R.id.rbPending;
            for (Record record : allRecords) {
                if (!pendingOnly || "待缴纳".equals(record.getStatus())) {
                    recordItems.add("项目名称：" + record.getProjectName()
                            + "\n定金金额：" + record.getDeposit()
                            + "\n尾款金额：" + record.getBalance()
                            + "\n缴纳日期：" + record.getPaymentDate()
                            + "\n消费类型：" + record.getType()
                            + "\n状态：" + record.getStatus());
                }
            }
            String emptyText = finalLoadError ? "记录读取失败"
                    : pendingOnly ? "暂无待缴纳记录" : "暂无尾款记录";
            emptyView.setText(emptyText);
            adapter.notifyDataSetChanged();
        };
        recordList.setEmptyView(emptyView);
        statusGroup.setOnCheckedChangeListener((group, checkedId) -> showRecords.run());
        showRecords.run();

        Button back = findViewById(R.id.btnBackHome);
        back.setOnClickListener(v -> finish());
    }
}
