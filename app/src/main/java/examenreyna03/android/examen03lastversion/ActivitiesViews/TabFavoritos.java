package examenreyna03.android.examen03lastversion.ActivitiesViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import examenreyna03.android.examen03lastversion.R;

public class TabFavoritos  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_favoritos, container, false);

        return rootView;
    }
}
