package com.sansan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.sansan.myapplication.bean.User;
import com.sansan.myapplication.util.LogUtils;
import com.sansan.myapplication.widget.MyDatePickerDialog;
import com.sansan.myapplication.widget.MyTimePickerDialog;
import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =MainActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        Gson gson = new Gson();
        String jsonString = "{\"Name\":\"怪盗kidou\",\"age\":24}";
        User user = gson.fromJson(jsonString, User.class);
        System.out.println(user);
        System.out.println("111 name="+user.name+" age="+user.age+" email="+user.emailAddress);
        LogUtils.d(TAG,"111 name="+user.name+" age="+user.age+" email="+user.emailAddress);

        User user1 = new User("怪盗kidou",24,true,"emalal");
        Gson gson4 = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .excludeFieldsWithModifiers( Modifier.STATIC)
                .create();
        LogUtils.d(TAG,"444 gson="+gson4.toJson(user1));

        Gson gson1 = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson1.fromJson(jsonArray, String[].class);
        LogUtils.d(TAG,"3String[] gson="+ Arrays.toString(strings));

        Gson gson2 = new Gson();
        String jsonArray1 = "[\"Android\",\"Java\",\"PHP\"]";
//        String[] strings = gson.fromJson(jsonArray1, String[].class);
        List<String> stringList = gson.fromJson(jsonArray1, new TypeToken<List<String>>() {}.getType());
        LogUtils.d(TAG,"list<String> gson="+stringList.toString());

        JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
        try {
            writer.beginObject() // throws IOException
                    .name("name").value("怪盗kidou")
                    .name("age").value(24)
                    .name("email").nullValue() //演示null
                    .endObject(); // throws IOException
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.flush(); // throws IOException
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson3 = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();
        LogUtils.d(TAG,"gsonbuild ="+gson3.toJson(user1));

        Gson gson5 = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        // 这里作判断，决定要不要排除该字段,return true为排除
                        if ("name".equals(f.getName())) return true; //按字段名排除
                        Expose expose = f.getAnnotation(Expose.class);
                        if (expose != null && expose.deserialize() == false) return true; //按注解排除
                        return false;
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 直接排除某个类 ，return true为排除
                        return (clazz == int.class || clazz == Integer.class);
                    }
                })
                .create();
        LogUtils.d(TAG,"ExclusionStrategy gson="+gson5.toJson(user1));

        Gson gson6 = new GsonBuilder()
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field f) {
                        //实现自己的规则
                        return null;
                    }
                })
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();

    }

    public void date(View v) {
        new MyDatePickerDialog(this, "日期", new MyDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(View view, String dateStr, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(MainActivity.this, dateStr, Toast.LENGTH_LONG).show();
                    }
                }, null).show();
    }

    public void time(View v) {

        new MyTimePickerDialog(this, "时间", new MyTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(View view, String time, int hourOfDay, int minute) {
                Toast.makeText(MainActivity.this, time, Toast.LENGTH_LONG).show();
            }
        }, null).show();
    }
}
