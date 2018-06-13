package art.kashmir.com.pq;



import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements View.OnClickListener {
    int a;
    Button hd;
LinearLayout tx,pro;
ImageView img,copy;
ImageButton dwn;
String url1;
ContentResolver contentResolver;
    String url;
    String path;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_blank, container, false);
       img=v.findViewById(R.id.image);
       tx=v.findViewById(R.id.tx);
       contentResolver=v.getContext().getContentResolver();
       dwn=v.findViewById(R.id.dwn);
       dwn.setOnClickListener(this);
       hd=v.findViewById(R.id.hd);
       hd.setOnClickListener(this);
       pro=v.findViewById(R.id.pro);
       pro.setVisibility(View.VISIBLE);
       tx.setVisibility(View.GONE);
        getimage();
        return v;
    }
    //this is where the image comes in from .using a simple api
public void getimage(){
        //using math.random to get new new posts on refresh to trick the url
        a=(int)(Math.random()*1000);
        url="https://picsum.photos/300/450/?image="+a;
        pro.setVisibility(View.GONE);
        tx.setVisibility(View.VISIBLE);
//removing whitespaces from url
        String ur=url.replace("//s+","");
        url1=ur;
    try {
        Glide.with(this).load(ur).into(img);
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
    }
}

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dwn:
                try {
                   try {//download is done here
                       //first check if sdcard is mounted
                         String state= Environment.getExternalStorageState();
                         if(state.equals(Environment.MEDIA_MOUNTED)) {
                             //then took the imageview and convert it to bitmap
                             Bitmap bm=((BitmapDrawable)img.getDrawable()).getBitmap();
                             Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                             //send the bitmap to the function for further process
                             saveImage(bm);
                         }

                         else{
                             Toast.makeText(getActivity(), "no sdcard found", Toast.LENGTH_SHORT).show();
                         }
                   } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.hd:
                Intent intent= null;
                try {
                    intent = new Intent(v.getContext(),web.class);
                    intent.putExtra("url",url1);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                }
        }

   private void saveImage(Bitmap data) {
        //create a folder if not exists with name="pq"
       File createFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"pq");
       if(!createFolder.exists())
           createFolder.mkdir();
       //took the current timestamp so that each image gets a unique name
       String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
       String imageFileName = "IMG_" + timeStamp;
       //save the image to the file in that folder you just created
       File saveImage = new File(createFolder,imageFileName+".jpg");
       //get the image's absolute path
       path=saveImage.getAbsolutePath();
       //notify gallery about the pic using an broadcast receiver otherwise image will not be visible to tghe user until rebooted
       notifyGalleryAboutPic(getActivity(),path);
       try {
           OutputStream outputStream = new FileOutputStream(saveImage);
           //compresss the bitmap
           data.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
           outputStream.flush();
           outputStream.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    public static void notifyGalleryAboutPic(Context context, String imagePath) {
        //sendiing an explicit intent to the media scaneer
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imagePath);
        Uri contentUri = Uri.fromFile(file);
        intent.setData(contentUri);
        //sending the bbroadscast
        context.sendBroadcast(intent);
    }
    }
