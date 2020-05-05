package com.example.bottomsheetexemplo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomsheetexemplo.data.Colaborador;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ColaboradorFragment extends Fragment {

    //Declaração de constantes
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final int PERMISSAO_REQUEST = 3;
    private static final int PEGA_FOTO = 4;
    //Declaração de objetos criados nas telas activity e content
    private AppCompatImageView imageView;
    private AppCompatTextView textView;
    private String currentPhotoPath;
    private Activity activity;
    private Bitmap bitmap;

    private ColaboradorViewModel mViewModel;
    private AppCompatEditText edtNome, edtCargo, edtCpf, edtEndereco, edtEmail;

    public static ColaboradorFragment newInstance() {
        return new ColaboradorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.colaborador_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edtNome = getActivity().findViewById(R.id.edtNome);
        edtCargo = getActivity().findViewById(R.id.edtCargo);
        edtCpf = getActivity().findViewById(R.id.edtCpf);
        edtEndereco = getActivity().findViewById(R.id.edtEndereco);
        edtEmail = getActivity().findViewById(R.id.edtEmail);

        //Objeto activity criado para passagem nos parãmetros de permissões
        activity = getActivity();

        //Condicional para controle de permissões
        // Verifica se há permissão para leitura de arquivos
        if (ContextCompat.checkSelfPermission(activity.getBaseContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        //Verifica se há permissões para escrita de arquivos
        if (ContextCompat.checkSelfPermission(activity.getBaseContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }


        mViewModel = new ViewModelProvider(requireActivity())
                .get(ColaboradorViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getColaborador().observe(getViewLifecycleOwner(),
                new Observer<Colaborador>() {
                    @Override
                    public void onChanged(Colaborador colaborador) {
                        edtNome.setText(colaborador.getNome());
                        edtCargo.setText(colaborador.getCargo());
                        edtCpf.setText(colaborador.getCpf());
                        edtEndereco.setText(colaborador.getEndereco());
                        edtEmail.setText(colaborador.getEmail());
                    }
                });

        activity.findViewById(R.id.btnSalvarColab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] imagemEnviada = stream.toByteArray();

                        Colaborador colaborador = new Colaborador(
                                0, edtNome.getText().toString(),
                                edtCargo.getText().toString(),
                                edtCpf.getText().toString(),
                                edtEndereco.getText().toString(),
                                edtEmail.getText().toString(),
                                edtNome.getText().toString().toLowerCase().trim()
                                + ".jpg", imagemEnviada);

                        mViewModel.doAction(colaborador);
                    }
                });


        //Botão para editar foto do colaborador
        activity.findViewById(R.id.editFotoColab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, "Acessando Galeria!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // Acesso à Galeria
                        acessarGaleria();
                    }
                });

        //Botão para acionamento da câmera
        activity.findViewById(R.id.capturaFotoColab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, "Capturando Imagem!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Chamada do método tirarFoto()
                        tirarFoto();
                    }
                });
    }

    private void tirarFoto() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (it.resolveActivity(activity.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity().getBaseContext(),
                        activity.getBaseContext()
                                .getApplicationContext().getPackageName() + ".provider", photoFile);

                it.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(it, REQUEST_TAKE_PHOTO);
            }

        }
    }

    //Método para inclusão de imagem na galeria
    private void galleryAddPic() {
        File f = new File(currentPhotoPath);
        activity.sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));
    }

    //Método para acessar a galeria
    private void acessarGaleria() {
        Intent intentPegaFoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentPegaFoto, PEGA_FOTO);
    }

    //Método para definição das dimensões da imagem
    private void setPic() {
        // Obtendo as dimensões da imagem para a View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Obter as dimensões do Bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determina como diminuir a escala da imagem
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decodifica o arquivo de imagem para o Bitmap que preencherá a View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        // Cria o bitmap da imagem capturada
        bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        // Apresenta a imagem na tela
        imageView.setImageBitmap(bitmap);
    }



    // Método que retorna o resultado da chamada da câmera pela Intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageView = activity.findViewById(R.id.fotoColab);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Chamada do método de adição da imagem na galeria
            galleryAddPic();
            // Definição das dimensões da imagem
            setPic();
        } else if (requestCode == PEGA_FOTO && resultCode == RESULT_OK) {
            //Captura caminho da imagem selecionada
            Uri imagemSelecionada = data.getData();

            // declara um stream (seguimento de dados) para ler a imagem
            // recuperada do SD Card
            InputStream inputStream = null;

            // recuperando a sequencia de entrada, baseada no caminho (uri)
            // da imagem
            try {
                inputStream = activity.getContentResolver().openInputStream(imagemSelecionada);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // recuperando um bitmap do stream
            bitmap = BitmapFactory.decodeStream(inputStream);
            // Vínculo do objeto ImageView
            imageView = (AppCompatImageView) activity.findViewById(R.id.fotoColab);
            // Reduz imagem e configura apresentação
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 1080, 1080, true);
            imageView.setImageBitmap(bitmapReduzido);
            imageView.setScaleType(AppCompatImageView.ScaleType.FIT_XY);
        }
    }


    private File createImageFile() throws IOException {
        textView = activity.findViewById(R.id.textView);

        // Criando o nome da imagem
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Define a galeria como caminho da imagem para armazenamento
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",    /* suffix */
                storageDir      /* directory */
        );

        // Salva um arquivo: caminho para utilização com ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        // Apresenta caminho salvo na tela
        textView.setText(currentPhotoPath);

        return image;
    }
}
