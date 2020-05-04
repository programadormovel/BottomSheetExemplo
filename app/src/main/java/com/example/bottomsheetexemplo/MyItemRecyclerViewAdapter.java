package com.example.bottomsheetexemplo;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.bottomsheetexemplo.ItemFragment.OnListFragmentInteractionListener;
import com.example.bottomsheetexemplo.data.Colaborador;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Colaborador} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    // Changed by PM
    private final List<Colaborador> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Colaborador> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getNome());
        // included by PM
        holder.mDetailView.setText(mValues.get(position).getCargo());
        Bitmap bitmap = converterByteToBipmap(mValues.get(position).getFoto());

        if (bitmap != null) {
            holder.mFotoView.setImageBitmap(bitmap);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, foto.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bmp;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final AppCompatTextView mIdView;
        public final AppCompatTextView mContentView;
        //included by PM
        public final AppCompatTextView mDetailView;
        public final AppCompatImageView mFotoView;

        public Colaborador mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (AppCompatTextView) view.findViewById(R.id.item_number);
            mContentView = (AppCompatTextView) view.findViewById(R.id.content);
            //included by PM
            mDetailView = (AppCompatTextView) view.findViewById(R.id.details);
            mFotoView = (AppCompatImageView) view.findViewById(R.id.foto);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
