package gps.map.navigator.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.AndroidSupportInjection;
import gps.map.navigator.R;
import gps.map.navigator.common.Constants;
import gps.map.navigator.common.debug.Logger;
import gps.map.navigator.presenter.interfaces.Presenter;
import gps.map.navigator.view.ui.fragment.listener.NavigationViewListener;
import gps.map.navigator.view.ui.fragment.listener.NightModeListener;
import gps.map.navigator.view.ui.fragment.listener.SatelliteModeListener;
import gps.map.navigator.view.ui.fragment.listener.TrafficModeListener;

public class BottomMenuFragment extends BottomSheetDialogFragment {

    private NavigationView navigationView;

    @Inject
    @Named(Constants.ApplicationContext)
    Context context;
    @Inject
    Presenter presenterStrategy;
    @Inject
    NavigationView.OnNavigationItemSelectedListener listener;
    @Inject
    @Named(Constants.NightModeListener)
    CompoundButton.OnCheckedChangeListener nightModeListener;
    @Inject
    @Named(Constants.SatelliteModeListener)
    CompoundButton.OnCheckedChangeListener satelliteModeListener;
    @Inject
    @Named(Constants.TrafficModeListener)
    CompoundButton.OnCheckedChangeListener trafficModeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);
        navigationView = view.findViewById(R.id.navigation_view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navigationView.setNavigationItemSelectedListener(listener);
        addCheckboxListeners();
    }

    private void addCheckboxListeners() {
        try {
            Menu menu = navigationView.getMenu();

            addNightModeListener(menu.findItem(R.id.enable_night_mode));
            addSatelliteModeListener(menu.findItem(R.id.enable_satelite));
            addTrafficModeListener(menu.findItem(R.id.enable_traffic));
        } catch (Throwable t) {
            Logger.error(t);
        }
    }

    private void addNightModeListener(MenuItem menuItem) {
        SwitchCompat actionView = getView(menuItem);
        actionView.setChecked(presenterStrategy.hasNightMode());
        actionView.setOnCheckedChangeListener(nightModeListener);
    }

    private SwitchCompat getView(MenuItem menuItem) {
        return (SwitchCompat) menuItem.getActionView();
    }

    private void addSatelliteModeListener(MenuItem menuItem) {
        SwitchCompat actionView = getView(menuItem);
        actionView.setChecked(presenterStrategy.hasSatelliteMode());
        actionView.setOnCheckedChangeListener(satelliteModeListener);
    }

    private void addTrafficModeListener(MenuItem menuItem) {
        SwitchCompat actionView = getView(menuItem);
        actionView.setChecked(presenterStrategy.hasTrafficMode());
        actionView.setOnCheckedChangeListener(trafficModeListener);
    }
}
