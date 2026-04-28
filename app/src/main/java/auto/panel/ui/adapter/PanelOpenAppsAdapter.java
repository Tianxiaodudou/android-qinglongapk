package auto.panel.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import auto.panel.R;
import auto.panel.bean.panel.PanelOpenApp;

public class PanelOpenAppsAdapter extends RecyclerView.Adapter<PanelOpenAppsAdapter.ViewHolder> {

    private List<PanelOpenApp> dataList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDelete(PanelOpenApp app, int position);
        void onResetSecret(PanelOpenApp app, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<PanelOpenApp> data) {
        dataList.clear();
        if (data != null) {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public List<PanelOpenApp> getData() {
        return dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.panel_item_open_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PanelOpenApp app = dataList.get(position);
        holder.nameText.setText(app.getName() != null ? app.getName() : "未命名");
        holder.clientIdText.setText("Client ID: " + (app.getClientId() != null ? app.getClientId() : ""));
        holder.scopesText.setText("权限: " + (app.getScopes() != null ? app.getScopes() : ""));

        holder.deleteBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(app, holder.getBindingAdapterPosition());
            }
        });

        holder.resetBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onResetSecret(app, holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView clientIdText;
        TextView scopesText;
        View deleteBtn;
        View resetBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.app_name);
            clientIdText = itemView.findViewById(R.id.app_client_id);
            scopesText = itemView.findViewById(R.id.app_scopes);
            deleteBtn = itemView.findViewById(R.id.app_delete_btn);
            resetBtn = itemView.findViewById(R.id.app_reset_btn);
        }
    }
}
