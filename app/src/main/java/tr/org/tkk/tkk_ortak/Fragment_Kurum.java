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

public class Fragment_Kurum extends Fragment{    private String TAG = MainActivity.class.getSimpleName();
    FragmentActivity listener;
    private ProgressDialog pDialog;
    private ListView lv;
    private static String url = "https://ekoop.tarimkredi.org.tr/rest/ekoopWebServisUtils/kurumBilgileriRestServis?kurumNo=K010852";
    ArrayList<HashMap<String, String>> contactList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_kurum , container,false);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject object = jsonObj.getJSONObject("object");

                 //   JSONObject goat = json.getJSONObject("goat");



                    // looping through All Contacts
               //    for (int i = 0; i < contacts.length(); i++) {
                  //      JSONObject c = contacts.getJSONObject(i);


                        String bolgeNo = object.getString("bolgeNo");
                        String bolgeAd = object.getString("bolgeAd");
                        String kurumNo = object.getString("kurumNo");
                        String kurumAd = object.getString("kurumAd");
                    String telefon = object.getString("telefon");
                    String faks = object.getString("faks");
                    String gsm = object.getString("gsm");
                    String adres = object.getString("adres");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("bolgeNo", bolgeNo);
                        contact.put("bolgeAd", bolgeAd);
                        contact.put("kurumNo", kurumNo);
                        contact.put("kurumAd", kurumAd);
                    contact.put("telefon", telefon);
                    contact.put("faks", faks);
                    contact.put("gsm", gsm);
                    contact.put("adres", adres);


                        // adding contact to contact list
                        contactList.add(contact);
                 //   }

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
                    R.layout.list_item_kurum, new String[]{"bolgeNo", "bolgeAd",
                    "kurumNo","kurumAd","telefon","faks","gsm","adres"}, new int[]{R.id.bolgeNo,
                    R.id.bolgeAd, R.id.kurumNo,R.id.kurumAd,R.id.telefon,R.id.faks,R.id.gsm,R.id.adres});

            lv.setAdapter(adapter);
        }
    }



}
