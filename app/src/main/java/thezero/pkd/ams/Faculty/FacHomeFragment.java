package thezero.pkd.ams.Faculty;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thezero.pkd.ams.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacHomeFragment extends Fragment {


    public FacHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fac_home, container, false);
    }

}
