package com.example.tanvi.NTrusted.Source.Utilities.Fragments.MISC;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvi.NTrusted.R;
import com.example.tanvi.NTrusted.Source.Constants;
import com.example.tanvi.NTrusted.Source.Models.Advertisement;
import com.example.tanvi.NTrusted.Source.Models.Category;
import com.example.tanvi.NTrusted.Source.Utilities.Adapters.CustomSpinnerAdapter;
import com.example.tanvi.NTrusted.Source.Utilities.ImageUtil.ImageUploadAsyncTask;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.GETOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.POSTOperation;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyGETCallBack;
import com.example.tanvi.NTrusted.Source.Utilities.REST_Calls.VolleyPOSTCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PostAdvFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner categorySpinner;
    private EditText productName, productDesc, productPrice;
    private List<Category> categories = new ArrayList<Category>();

    private CustomSpinnerAdapter customSpinnerAdapter;

    private Button postAdv;

    private GETOperation getOperation;

    private POSTOperation postOperation;

    private Advertisement advertisement;

    private RadioGroup radioGroup;

    private RadioButton radioBorrow, radioLend;

    private TextView productPriceTitle;

    private String userId;

    private Button mUploadImagesButton;

    private ImageUploadAsyncTask imageUploadAsyncTask;

    private OnFragmentInteractionListener mListener;

    private String photoURL;

    //Camera
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private List<String> imageStringArray;
    private final ArrayList<Bitmap> ResponseimageArray = new ArrayList<Bitmap>();
    private final ArrayList<Bitmap> ShowImagesCaptured = new ArrayList<Bitmap>();
    private final int PICK_IMAGE_MULTIPLE =1;
    private LinearLayout lnrImages;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private TextView numImagesTextView;
    private int numImages = 0;
    Uri file;


    public PostAdvFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostAdvFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostAdvFragment newInstance(String param1, String param2) {
        PostAdvFragment fragment = new PostAdvFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_post_adv, container, false);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref",0);
        userId = pref.getString(Constants.UserID,null);

        categorySpinner = (Spinner) rootView.findViewById(R.id.spinner);
        productName = (EditText) rootView.findViewById(R.id.productName);
        productDesc = (EditText) rootView.findViewById(R.id.productDesc);
        productPrice = (EditText) rootView.findViewById(R.id.productPrice);
        postAdv = (Button) rootView.findViewById(R.id.send);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioAdType);
        radioBorrow = (RadioButton) rootView.findViewById(R.id.radioBorrow);
        radioLend = (RadioButton) rootView.findViewById(R.id.radioLend);
        productPriceTitle = (TextView) rootView.findViewById(R.id.productPriceText);
        productPrice.setVisibility(View.INVISIBLE);
        productPriceTitle.setVisibility(View.INVISIBLE);

        radioLend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productPriceTitle.setVisibility(View.VISIBLE);
                productPrice.setVisibility(View.VISIBLE);

            }
        });

        radioBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productPriceTitle.setVisibility(View.INVISIBLE);
                productPrice.setVisibility(View.INVISIBLE);

            }
        });


        mUploadImagesButton = (Button) rootView.findViewById(R.id.image_upload);
        mUploadImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    mUploadImagesButton.setEnabled(false);
//                    ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
//                }
//
//                file = Uri.fromFile(getMediaFile());
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,file);
//                startActivityForResult(intent,100);
            }
        });
        postAdv.setOnClickListener(this);
        categorySpinner.setOnItemSelectedListener(this);
        getCategories();

        return rootView;
    }





    private File getMediaFile() {
        File imageStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CameraComplaintPics");
        if(!imageStorage.exists()){
            if(!imageStorage.mkdirs())
                return null;
        }
        String imageTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(imageStorage.getPath() + File.separator+ "Report-"+imageTimeStamp+".jpg");

    }


    private void selectImage() {
        Log.d(TAG, "in selectImage");

        final CharSequence[] items = { "Take Photo",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = CameraUtility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        System.out.println("In camera intent ****************");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                Log.d(TAG, "Select from gallery chosen");
            } else if (requestCode == REQUEST_CAMERA) {
                Log.d(TAG, "Took a pic.");
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {

        if (data != null) {
            Bitmap img = (Bitmap) data.getExtras().get("data");
        }

        ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"ProductPics");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Uri file = Uri.fromFile(destination);

        Log.d("captured",destination.getPath());



        new ImageUploadAsyncTask(getActivity().getApplicationContext(),file){


            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                photoURL=this.fileURL;
                System.out.println("Photo url is "+photoURL);
            }
        }.execute();

    }

    private void getCategories() {

        System.out.println("In get categories");
        getOperation = new GETOperation(Constants.getAllCategories, getActivity().getApplicationContext());
        getOperation.getData(new VolleyGETCallBack(){
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onSuccess(JSONArray result) {

                System.out.println("In volley call back !!!!!!!!!!!!!!!!!!"+result.toString());
                for(int i=0;i<result.length();i++) {

                    Category category = new Category();
                    try {
                        category.setCategoryID((Integer) result.getJSONObject(i).get("categoryId"));
                        category.setCategoryName((String) result.getJSONObject(i).get("categoryName"));
                        categories.add(category);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                customSpinnerAdapter = new CustomSpinnerAdapter(getActivity().getApplicationContext(),categories);
                categorySpinner.setAdapter(customSpinnerAdapter);

                for(int i=0;i<categories.size();i++)
                    System.out.println(categories.get(i).getCategoryName());

            }

        });





    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Category item = (Category) adapterView.getItemAtPosition(i);

        System.out.println("Item selected !!!!"+item.getCategoryName());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {

        advertisement = new Advertisement();

        advertisement.setProductName(String.valueOf(productName.getText()));
        advertisement.setProductDesc(String.valueOf(productDesc.getText()));
        advertisement.setProductPrice(String.valueOf(productPrice.getText()));
        advertisement.setStatus("1");
        Category category = (Category) categorySpinner.getSelectedItem();
        advertisement.setProductCategory(category);
        makePost();
    }

    private void makePost() {

        HashMap<String,String> parameters = new HashMap<String,String>();


        int radio= radioGroup.getCheckedRadioButtonId();
        System.out.println("Radio is -------------->"+radio);

        if(radioBorrow.getId()==radio){

            parameters.put("adType","2");
            parameters.put("productPrice","0");

        }

        if(radioLend.getId()==radio) {
            parameters.put("adType", "1");
            parameters.put("productPrice",advertisement.getProductPrice());
        }
        parameters.put("productName",advertisement.getProductName());
        parameters.put("productDescription",advertisement.getProductDesc());

        System.out.println("CAT!!!!!!!!!!!!!!!!!!!!!!!!"+String.valueOf(advertisement.getProductCategory().getCategoryID()));
        parameters.put("categoryId",String.valueOf(advertisement.getProductCategory().getCategoryID()));
        parameters.put("active", "1");
        parameters.put("userId",userId);
        System.out.println("Photo url is "+photoURL);
        parameters.put("photoURL",photoURL);



        System.out.println("Parameters: Product Name: "+parameters.get("productName")+" Description:  "+parameters.get("productDescription")+" Price: "+parameters.get("productPrice"));
        System.out.println("Category :"+parameters.get("categoryId")+" User:"+parameters.get("userId")+" Active:" +parameters.get("active") +"Type:"+parameters.get("adType"));


        postOperation = new POSTOperation(Constants.postAdvertisement, parameters, getActivity().getApplicationContext());
        postOperation.postData(new VolleyPOSTCallBack() {
            @Override
            public void onSuccess(Object result) {

                Toast.makeText(getActivity().getApplicationContext(),"Your Ad successfully posted !",Toast.LENGTH_SHORT).show();

                TabFragment tabFragment = new TabFragment();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.containerView,tabFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }




}
