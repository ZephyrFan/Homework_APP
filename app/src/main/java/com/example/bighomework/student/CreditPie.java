package com.example.bighomework.student;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bighomework.Entity.PieEntry;
import com.example.bighomework.R;
import com.example.bighomework.dao.AccountData;
import com.example.bighomework.dao.ExamData;
import com.example.bighomework.model.Exam;
import com.example.bighomework.model.Grade;
import com.example.bighomework.teacher.PieView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreditPie extends AppCompatActivity {
    private ImageButton returnBT;
    private PieView mPieView;
    private TextView creditTV;
    private TextView sumTV;
    private AccountData AD=new AccountData();
    private ExamData ED=new ExamData();
    private String account;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_pie);

        account = getIntent().getStringExtra("account");
        try {
            name = AD.getNameByAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }

        returnBT=(ImageButton)findViewById(R.id.return_button);
        returnBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    finish();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        });

        mPieView = findViewById(R.id.pieView);


        creditTV=(TextView)findViewById(R.id.credit);
        sumTV=(TextView)findViewById(R.id.grade_sum);

        List<Exam> ls = new ArrayList<Exam>();
        List<Double> grade = new ArrayList<Double>();
        try {
            ls=ED.getAllExams();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<Exam> t=ls.iterator();
        while(t.hasNext()) {
            Exam s=t.next();
            List<Grade> ls2;
            ls2=s.getGradeList();
            Iterator<Grade> t2=ls2.iterator();
            while(t2.hasNext()){
                Grade s2=t2.next();
                if(s2.getStuName().equals(name)){
                    grade.add(s2.getGrade());
                }
            }
        }
        float a=0;
        float b=0;
        float c=0;
        float d=0;
        float e=0;
        double sum=0.0;
        for(int i=0;i<grade.size();i++){
            sum=sum+grade.get(i);
            if(0.0<=grade.get(i)&&grade.get(i)<60.0){
                a+=1.0;
            }else if(60.0<=grade.get(i)&&grade.get(i)<70.0){
                b+=1.0;
            }else if(70.0<=grade.get(i)&&grade.get(i)<80.0){
                c+=1.0;
            }else if(80.0<=grade.get(i)&&grade.get(i)<90.0){
                d+=1.0;
            }else {
                e+=1.0;
            }
        }
        a=100*a/grade.size();
        b=100*b/grade.size();
        c=100*c/grade.size();
        d=100*d/grade.size();
        e=100*e/grade.size();

        sumTV.setText(String.valueOf(sum));
        creditTV.setText(String.valueOf(sum/grade.size()));

        initPieView(a,b,c,d,e);
    }

    private void initPieView(float a,float b,float c,float d,float e) {
        mPieView.setColors(createColors());
        mPieView.setData(createData(a,b,c,d,e));
    }

    private ArrayList<PieEntry> createData(float a,float b,float c,float d,float e) {
        ArrayList<PieEntry> pieLists = new ArrayList<>();
        pieLists.add(new PieEntry(a, "不及格"));
        pieLists.add(new PieEntry(b, "及格"));
        pieLists.add(new PieEntry(c, "良"));
        pieLists.add(new PieEntry(d, "优秀"));
        pieLists.add(new PieEntry(e, "完美"));
        return pieLists;
    }

    private ArrayList<Integer> createColors() {
        ArrayList<Integer> colorLists = new ArrayList<>();
        colorLists.add(Color.parseColor("#EBBF03"));
        colorLists.add(Color.parseColor("#ff4d4d"));
        colorLists.add(Color.parseColor("#8d5ff5"));
        colorLists.add(Color.parseColor("#41D230"));
        colorLists.add(Color.parseColor("#4FAAFF"));
        return colorLists;
    }
}