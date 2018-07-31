package tr.org.tkk.tkk_ortak;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_Kefil extends Fragment {
    private String TAG = MainActivity.class.getSimpleName();
    FragmentActivity listener;
    private ProgressDialog pDialog;
    private ListView lv;
    private static String url = "";
    ArrayList<HashMap<String, String>> contactList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_kefil , container,false);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        contactList = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.list);
        new GetContacts().execute();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Lütfen Bekleyiniz...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String adsoyad_kefil;
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONObject("result").getJSONArray("kefilOlunanSozlesme");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String sozlesmeNo = c.getString("sozlesmeNo");
                        String ortakNo = c.getString("ortakNo");
                        String ortakAd = c.getString("ortakAd");
                        String ortakSoyad = c.getString("ortakSoyad");
                        String sozlesmeBedeli = c.getString("sozlesmeBedeli");
                               adsoyad_kefil = ortakAd+" "+ortakSoyad;

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("sozlesmeNo", sozlesmeNo);
                        contact.put("ortakNo", ortakNo);
                        contact.put("ortakAd", ortakAd);
                        contact.put("ortakSoyad", ortakSoyad);
                        contact.put("sozlesmeBedeli", sozlesmeBedeli);
                        contact.put("adsoyad_kefil", adsoyad_kefil);
                        // adding contact to contact list
                        contactList.add(contact);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Bağlantı Hatası!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), contactList,
                    R.layout.list_item_kefil, new String[]{"sozlesmeNo", "ortakNo",
                    "adsoyad_kefil","sozlesmeBedeli"}, new int[]{R.id.sozlesmeNo,
                    R.id.ortakNo, R.id.ortakadsoyad,R.id.sozlesmeBedeli});

            lv.setAdapter(adapter);
        }
    }
}
