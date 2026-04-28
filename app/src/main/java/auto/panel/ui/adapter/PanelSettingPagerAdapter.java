package auto.panel.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import auto.panel.ui.fragment.PanelSettingCommonFragment;
import auto.panel.ui.fragment.PanelSettingLoginLogFragment;
import auto.panel.ui.fragment.PanelSettingNotificationFragment;
import auto.panel.ui.fragment.PanelSettingOpenAppsFragment;

public class PanelSettingPagerAdapter extends FragmentStateAdapter {
    public static final String TAG = "PanelSettingPagerAdapter";

    public PanelSettingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PanelSettingCommonFragment();
            case 1:
                return new PanelSettingNotificationFragment();
            case 2:
                return new PanelSettingLoginLogFragment();
            default:
                return new PanelSettingOpenAppsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
