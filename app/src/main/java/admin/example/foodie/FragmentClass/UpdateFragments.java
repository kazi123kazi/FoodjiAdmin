package admin.example.foodie.FragmentClass;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.example.foodie.R;

import admin.example.foodie.MainActivity;
import admin.example.foodie.WelcomeActvity;
import admin.example.foodie.models.UpdateInfo;
import admin.example.foodie.models.UpdateResponse;
import admin.example.foodie.org.example.foodie.apifetch.FoodieClient;
import admin.example.foodie.org.example.foodie.apifetch.ServiceGenerator;
import admin.example.foodie.viewmodels.RestaurantsViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class UpdateFragments extends Fragment {
    private Button captureImage,selectImage;
    private ImageView imageView;
    private RestaurantsViewModel restaurantsViewModel;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA=1;
    public ProgressBar update;
    FoodieClient foodieClient;
    EditText NameUpdate,AddressUpdate,PhoneUpdate,PasswordUpdate;
    Button Update;
     List<String> contactNos=new ArrayList<String>();
     String name=null,address=null,phone=null,password=null;
     View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_contactus_fragments , container , false);


        captureImage =(Button) rootview.findViewById(R.id.capture_image);
        selectImage =(Button) rootview.findViewById(R.id.select_image);
        imageView = (ImageView) rootview.findViewById(R.id.Image_view);
        update=rootview.findViewById(R.id.progress_update);
        NameUpdate=(EditText) rootview.findViewById(R.id.RestaurantNameUpdate);
        AddressUpdate=(EditText) rootview.findViewById(R.id.RestaurantaddressUpdate);
        PhoneUpdate=(EditText) rootview.findViewById(R.id.RestaurantPhoneUpdate);
        PasswordUpdate=(EditText) rootview.findViewById(R.id.RestaurantPasswordUpdate);
        Update=(Button)rootview.findViewById(R.id.RestaurantInfoUpdate);

       name=NameUpdate.getText().toString();
       address=AddressUpdate.getText().toString();
       password=PasswordUpdate.getText().toString();
       if (PhoneUpdate.getText()!=null){
           contactNos.add(PhoneUpdate.getText().toString());
       }


        update.setVisibility(View.VISIBLE);
        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        showInfo();
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                            getActivity(), Manifest.permission.CAMERA)) {


                    } else {
                        ActivityCompat.requestPermissions((Activity) getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }

                }
                else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", f);

                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, 1);

                }

            }
        });
        //On click listener for
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (EasyPermissions.hasPermissions(getActivity(), galleryPermissions)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else {
                    EasyPermissions.requestPermissions(getActivity(), "Access for storage",
                            101, galleryPermissions);
                }
        }});
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfo();
            }
        });

        return  rootview;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("FOODJI ADMIN");
    }

    private void UpdateInfo() {
          // contactNos.add(PhoneUpdate.getText().toString());
        foodieClient = ServiceGenerator.createService(FoodieClient.class);

        UpdateInfo updateInfo  =new UpdateInfo(name, address, password, contactNos);
         Call<UpdateResponse> call=foodieClient.updateInfo(WelcomeActvity.token,updateInfo);
         call.enqueue(new Callback<UpdateResponse>() {
             @Override
             public void onResponse(Call<UpdateResponse> call , Response<UpdateResponse> response) {
                 if (response.code()==200) {
                     Toast.makeText(getActivity() , "Information Updated successfully" , Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<UpdateResponse> call , Throwable t) {

             }
         });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    //  update.setVisibility(View.VISIBLE);
                    MultipartBody.Part body = ProcessImage(bitmap);
                    postImage(body);


                    //   viewImage.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                //   Log.i("path",filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.deactivate();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.i("path of image:", picturePath + "");

                // update.setVisibility(View.VISIBLE);
                MultipartBody.Part body = ProcessImage(thumbnail);
                postImage(body);

            }
        }


    }

    public void postImage(MultipartBody.Part image){
        foodieClient = ServiceGenerator.createService(FoodieClient.class);


        Call<ResponseBody> call=foodieClient.postImage(MainActivity.token,image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
               // finish();
               // startActivity(getIntent());

                Log.i("Response:", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Response",t.getMessage());
            }
        });
    }

    public  MultipartBody.Part ProcessImage(Bitmap thumbnail){

        //create a file to write bitmap data
        File f = new File(getActivity().getCacheDir(),"fos.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 0,/*0 ignored for PNG,*/ bos);
        byte[] bitmapdata = bos.toByteArray();

        Log.i("START","STARTED");
//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("FINISHED","FINISHED");

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", f.getName(), reqFile);


        Log.i("TOKEN FROM HOME:",MainActivity.token);

        return body;

    }
    public void showInfo(){


        restaurantsViewModel= ViewModelProviders.of(UpdateFragments.this).get(RestaurantsViewModel.class);

        restaurantsViewModel.init();


        restaurantsViewModel.getRestaurantRepository().observe(this,responseUser -> {


            if (responseUser.getImage()!=null&&responseUser.getImage()!=""){

                String encodedImage = responseUser.getImage();

                byte[] image= Base64.decode(encodedImage,Base64.DEFAULT);
                Bitmap i=BitmapFactory.decodeByteArray(image,0,image.length);
                imageView.setImageBitmap(i);
                update.setVisibility(View.GONE);
            }


        });

    }

}
