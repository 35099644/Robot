package com.dev.irobot.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.dev.irobot.R;
import com.dev.irobot.config.Config;
import com.dev.irobot.support.SupportFragment;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends SupportFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Button importToContacts;
    private Button importToWeixin;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_home, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        importToContacts = (Button) view.findViewById(R.id.importToContacts);
        importToContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importMobileNumberToMobile();
            }
        });

        importToWeixin = (Button) view.findViewById(R.id.importToWeixin);
        importToWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void importMobileNumberToMobile() {
        final File path = new File(Config.getConfig().getDir(), "contacts.xlsx");
        if (!path.exists()) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("警告")
                    .setMessage("请确保contacts.xlsx文件已经放置到:" + Config.getConfig().getDir().getPath())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        Workbook workbook = WorkbookFactory.create(path);
                        Sheet sheet = workbook.getSheetAt(0);
                        Map<String, String> temp = new HashMap<String, String>();
                        for(Row row : sheet){
                            String name = row.getCell(0).getStringCellValue();
                            String mobile = row.getCell(1).getStringCellValue();
                            temp.put(mobile, name);
                            Log.d(TAG, "name:"+name+", mobile:"+mobile+", row:"+row.getRowNum());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}


