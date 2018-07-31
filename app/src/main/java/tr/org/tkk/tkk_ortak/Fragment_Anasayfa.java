package tr.org.tkk.tkk_ortak;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Anasayfa extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.layout_fragment_anasayfa , container,false);
         return view;
    }
}
