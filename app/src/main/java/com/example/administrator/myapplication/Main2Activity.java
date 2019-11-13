package com.example.administrator.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase mydb;
    ImageView iv1;
    Bitmap imagebitmap;
    String id="2";//一开始设置id为1,然后根据时间来赋值1，2，3，分别是第一，第二，第三张图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iv1 = (ImageView) findViewById(R.id.imageView1);
        try {
            a();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //获取传过来的数组
        //Bundle b = this.getIntent().getExtras();
        //String[] s = b.getStringArray("key");
        /*for(int i=0;i<s.length;i++){
            Log.d("c",s[i]);
        }
*/
       /* int count = Integer.parseInt(s[0]);
        Log.d("c", "" + count);

        String time2[] = new String[count];
        int data2[] = new int[count];
        int k = 0;
        for (int i = 1; i <= count; i++) {
            time2[k] = s[i];
            Log.d("c", i + ":" + time2[k]);
            k++;
        }
        k = 0;
        for (int i = count + 1; i < count * 2 + 1; i++) {
            data2[k] = Integer.parseInt(s[i]);
            Log.d("c", i + ":" + data2[k]);
            k++;
        }*/

    }

    private void a() throws IOException {
        // 创建助手类的实例
        // CursorFactory的值为null,表示采用默认的工厂类
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this, "saveimage.db", null, 1);
        // 创建一个可读写的数据库
        mydb = mySQLiteOpenHelper.getWritableDatabase();

/*      //将图片转化为位图
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.d);
        //创建一个字节数组输出流,流的大小为size
        int size = bitmap1.getWidth() * bitmap1.getHeight() * 4;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
       //将字节数组输出流转化为字节数组byte[]
        byte[] imagedata1 = baos.toByteArray();
        Log.d("a","1:"+imagedata1);

        //将字节数组保存到数据库中
        ContentValues cv = new ContentValues();
            cv.put("_id", 3);
            cv.put("image", imagedata1);
            mydb.replace("imagetable", null, cv);

        //关闭字节数组输出流
        baos.close();
*/
        //创建一个指针
        Cursor cur = mydb.query("imagetable", new String[]{"_id", "image"}, "_id like ?", new String[]{id}, null, null, null);

        byte[] imagequery =null;
        while(cur.moveToNext()) {
            int id =cur.getInt(cur.getColumnIndex("_id"));
            Log.d("a","2:"+id);
            imagequery=cur.getBlob(cur.getColumnIndex("image"));//将Blob数据转化为字节数组
            Log.d("a","3:"+imagequery);
            //将字节数组转化为位图
            imagebitmap = BitmapFactory.decodeByteArray(imagequery, 0, imagequery.length);
            Log.d("a","4:"+imagebitmap);
            //将位图显示为图片
            iv1.setImageBitmap(imagebitmap);
        }
        cur.close();

    }
}