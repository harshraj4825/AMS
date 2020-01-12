package thezero.pkd.ams.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomUserTypeArrayAdaptor extends ArrayAdapter<String> {
    public CustomUserTypeArrayAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public CustomUserTypeArrayAdaptor(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CustomUserTypeArrayAdaptor(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public CustomUserTypeArrayAdaptor(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CustomUserTypeArrayAdaptor(@NonNull Context context, int resource, @NonNull List <String> objects) {
        super(context, resource, objects);
    }

    public CustomUserTypeArrayAdaptor(@NonNull Context context, int resource, int textViewResourceId, @NonNull List <String> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
