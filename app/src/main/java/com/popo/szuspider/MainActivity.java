package com.popo.szuspider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button)findViewById(R.id.send);
        final EditText usn=(EditText)findViewById(R.id.userName);
        final EditText psw=(EditText)findViewById(R.id.psw);
        button.setOnClickListener(new View.OnClickListener() {
            ProgressDialog progressDialog;
            @Override
            public void onClick(View view) {
                if(!usn.getText().toString().equals("")&&!psw.getText().toString().equals("")){
                    GetTask task=new GetTask(new GetTaskListener() {
                        @Override
                        public void onFinished(String s) {
                            progressDialog.dismiss();
                            Intent intent=new Intent(MainActivity.this,Show.class);
                            intent.putExtra("web",s);
                            startActivity(intent);
                        }
                    },usn.getText().toString(),psw.getText().toString());
                    task.execute();
                    progressDialog=ProgressDialog.show(MainActivity.this,"提示","正在登陆中");
                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("输入错误")
                            .setMessage("请输入有效的用户名和密码")
                            .setPositiveButton("确定",null)
                            .show();
                }
            }
        });
    }
}
