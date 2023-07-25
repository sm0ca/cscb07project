package com.example.mallapp.tools;

import androidx.recyclerview.widget.RecyclerView;

public class NotifyAdapter {

    public static class Changed implements IFNotifyAdapterService {
        private final int position;

        public Changed(int position) {
            this.position = position;
        }

        @Override
        public void notifyAdapter(RecyclerView.Adapter<?> adapter) {
            adapter.notifyItemChanged(position);
        }
    }

    public static class Inserted implements IFNotifyAdapterService {
        private final int position;

        public Inserted(int position) {
            this.position = position;
        }

        @Override
        public void notifyAdapter(RecyclerView.Adapter<?> adapter) {
            adapter.notifyItemInserted(position);
        }
    }

    public static class Moved implements IFNotifyAdapterService {
        private final int from;
        private final int to;

        public Moved(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public void notifyAdapter(RecyclerView.Adapter<?> adapter) {
            adapter.notifyItemMoved(from, to);
        }
    }

    public static class Removed implements IFNotifyAdapterService {
        private final int position;

        public Removed(int position) {
            this.position = position;
        }
        @Override public void notifyAdapter(RecyclerView.Adapter<?> adapter) {
            adapter.notifyItemRemoved(position);
        }
    }
}
