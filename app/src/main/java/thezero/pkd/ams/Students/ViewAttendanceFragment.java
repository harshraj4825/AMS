package thezero.pkd.ams.Students;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thezero.pkd.ams.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAttendanceFragment extends Fragment {


    public ViewAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_attendance, container, false);
    }

}
