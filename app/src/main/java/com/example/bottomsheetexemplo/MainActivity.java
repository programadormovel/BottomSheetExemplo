package com.example.bottomsheetexemplo;

import android.os.Bundle;

import com.example.bottomsheetexemplo.data.Colaborador;
import com.example.bottomsheetexemplo.dummy.DummyContent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
        implements ItemFragment.OnListFragmentInteractionListener {

    FloatingActionButton fab, fabAccountPlus;
    BottomSheetDialog bottomSheetDialog;
    View bottomSheet;
    FragmentTransaction frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // by PM
        fabAccountPlus = findViewById(R.id.fab_account_plus);
        //fabAccountPlus.setVisibility(View.INVISIBLE);
        fabAccountPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = getSupportFragmentManager().beginTransaction();
                frag.replace(R.id.nav_host_fragment,
                        new ColaboradorFragment())
                .addToBackStack(null).commit();
                //fabAccountPlus.setVisibility(View.INVISIBLE);
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(
                        MainActivity.this, R.style.BottomSheet);

                bottomSheet = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.bottom_sheet_layout,
                                (LinearLayout) findViewById(R.id.bottomSheetCont));

                bottomSheet.findViewById(R.id.contLayoutZero).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Snackbar.make(bottomSheet, "Colaboradores", Snackbar.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();

                        frag = getSupportFragmentManager().beginTransaction();
                        frag.replace(R.id.nav_host_fragment,
                                new ItemFragment())
                                .addToBackStack(null)
                                .commit();

                        fabAccountPlus.setVisibility(View.VISIBLE);
                    }
                });

                bottomSheet.findViewById(R.id.contLayoutOne).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(bottomSheet, "Foto", Snackbar.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();

                        //fabAccountPlus.setVisibility(View.INVISIBLE);
                    }
                });

                bottomSheet.findViewById(R.id.contLayoutTwo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(bottomSheet, "Notas", Snackbar.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheet.findViewById(R.id.contLayoutThree).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(bottomSheet, "Galeria", Snackbar.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                        //fabAccountPlus.setVisibility(View.INVISIBLE);
                    }
                });
                bottomSheet.findViewById(R.id.contLayoutFour).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(bottomSheet, "Arquivos", Snackbar.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                        //fabAccountPlus.setVisibility(View.INVISIBLE);
                    }
                });
                bottomSheet.findViewById(R.id.contLayoutFive).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(bottomSheet, "Pasta", Snackbar.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                        //fabAccountPlus.setVisibility(View.INVISIBLE);
                    }
                });

                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Colaborador item) {

    }
}
