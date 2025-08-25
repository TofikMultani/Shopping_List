//package com.example.shoppinglistapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ItemList extends AppCompatActivity {
//
//    private EditText editItemName, editItemDesc;
//    private Button addItemButton;
//    private RecyclerView recyclerView;
//    private List<ItemModel> itemList;
//    private ItemListAdapter adapter;
//    private SharedPreferences sharedPreferences;
//    private Gson gson;
//    ImageView ilacc,ilbcak;
//    private String categoryKey; // Unique category identifier
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item_list);
//
//        // Get categoryKey from Intent
//        categoryKey = getIntent().getStringExtra("categoryKey");
//
//        if (categoryKey == null) {
//            Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        editItemName = findViewById(R.id.editItemName);
//        editItemDesc = findViewById(R.id.editItemDesc);
//        addItemButton = findViewById(R.id.addItemButton);
//        recyclerView = findViewById(R.id.recyclerView);
//        ilacc=findViewById(R.id.ilacc);
//        ilbcak = findViewById(R.id.ilbcak);
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//
//        gson = new Gson();
//
//        loadItemList();
//
//        adapter = new ItemListAdapter(itemList, this, sharedPreferences, categoryKey);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
//        addItemButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = editItemName.getText().toString().trim();
//                String desc = editItemDesc.getText().toString().trim();
//
//                if (!name.isEmpty()) {
//                    itemList.add(new ItemModel(name, desc, false));
//                    adapter.notifyItemInserted(itemList.size() - 1);
//                    saveItemList();
//
//                    editItemName.setText("");
//                    editItemDesc.setText("");
//                } else {
//                    Toast.makeText(ItemList.this, "Please enter item name", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        ilacc.setOnClickListener(view -> {
//            Intent i = new Intent(ItemList.this, Account_Show.class);
//            startActivity(i);
//        });
//
//        ilbcak.setOnClickListener(view -> {
//            Intent i = new Intent(ItemList.this, Category.class);
//            startActivity(i);
//        });
//    }
//
//    private void loadItemList() {
//        String key = "Items_" + categoryKey;
//        String json = sharedPreferences.getString(key, null);
//        Type type = new TypeToken<List<ItemModel>>() {}.getType();
//        itemList = (json == null) ? new ArrayList<>() : gson.fromJson(json, type);
//    }
//
//    private void saveItemList() {
//        String key = "Items_" + categoryKey;
//        String json = gson.toJson(itemList);
//        sharedPreferences.edit().putString(key, json).apply();
//    }
//
//}
//




/*package com.example.shoppinglistapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemList extends AppCompatActivity {

    private EditText editItemName, editItemDesc;
    private Button addItemButton, reportButton;
    private RecyclerView recyclerView;
    private List<ItemModel> itemList;
    private ItemListAdapter adapter;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private ImageView ilacc, ilbcak;
    private String categoryKey; // Unique category identifier

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String CHANNEL_ID = "report_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Get categoryKey from Intent
        categoryKey = getIntent().getStringExtra("categoryKey");

        if (categoryKey == null) {
            Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editItemName = findViewById(R.id.editItemName);
        editItemDesc = findViewById(R.id.editItemDesc);
        addItemButton = findViewById(R.id.addItemButton);
        reportButton = findViewById(R.id.report);
        recyclerView = findViewById(R.id.recyclerView);
        ilacc = findViewById(R.id.ilacc);
        ilbcak = findViewById(R.id.ilbcak);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        gson = new Gson();

        loadItemList();

        adapter = new ItemListAdapter(itemList, this, sharedPreferences, categoryKey);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editItemName.getText().toString().trim();
                String desc = editItemDesc.getText().toString().trim();

                if (!name.isEmpty()) {
                    itemList.add(new ItemModel(name, desc, false));
                    adapter.notifyItemInserted(itemList.size() - 1);
                    saveItemList();

                    editItemName.setText("");
                    editItemDesc.setText("");
                } else {
                    Toast.makeText(ItemList.this, "Please enter item name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ilacc.setOnClickListener(view -> {
            Intent i = new Intent(ItemList.this, Account_Show.class);
            startActivity(i);
        });

        ilbcak.setOnClickListener(view -> {
            Intent i = new Intent(ItemList.this, Category.class);
            startActivity(i);
        });

        // Report button click listener
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDFReport();
            }
        });
    }

    private void loadItemList() {
        String key = "Items_" + categoryKey;
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<List<ItemModel>>() {
        }.getType();
        itemList = (json == null) ? new ArrayList<>() : gson.fromJson(json, type);
    }

    private void saveItemList() {
        String key = "Items_" + categoryKey;
        String json = gson.toJson(itemList);
        sharedPreferences.edit().putString(key, json).apply();
    }

    // જનરેટ PDF રીપોર્ટ (લિસ્ટ આઈટમ સાથે)
    private void generatePDFReport() {
        createNotificationChannel(); // Ensure notification channel exists

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        // Define A4 page size (595 x 842 points)
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Draw title
        titlePaint.setTextSize(24);
        titlePaint.setFakeBoldText(true);
        canvas.drawText("Shopping List Report", 180, 50, titlePaint);

        // Draw items
        paint.setTextSize(14);
        int y = 100;
        for (int i = 0; i < itemList.size(); i++) {
            ItemModel item = itemList.get(i);
            String line = (i + 1) + ". " + item.getName() + " - " + item.getDescription() +
                    " [" + (item.isCollected() ? "Collected" : "Pending") + "]";
            canvas.drawText(line, 50, y, paint);
            y += 30;
        }

        document.finishPage(page);

        // Save PDF to app's private files directory
        String fileName = "ShoppingListReport_" + System.currentTimeMillis() + ".pdf";
        File file = new File(getFilesDir(), fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            document.writeTo(fos);
            Toast.makeText(this, "Report saved: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

            // Show notification with intent to open PDF
            showSuccessNotification(fileName, file);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        document.close();
    }

    private void showSuccessNotification(String fileName, File file) {
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_download) // Replace with your app icon
                .setContentTitle("Report Created")
                .setContentText(fileName + " saved")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Report Notifications";
            String description = "Notifications for PDF report creation";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void showSuccessNotification(String fileName) {
        // ફાઈલ ખોલવા માટે ઈન્ટેન્ટ બનાવો
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(this.getFilesDir(), fileName);
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Report Created")
                .setContentText(fileName + " saved")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}*/










package com.example.shoppinglistapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemList extends AppCompatActivity {

    private EditText editItemName, editItemDesc;
    private Button addItemButton, reportButton;
    private RecyclerView recyclerView;
    private List<ItemModel> itemList;
    private ItemListAdapter adapter;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private ImageView ilacc, ilbcak;
    private String categoryKey; // Unique category identifier

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String CHANNEL_ID = "report_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Get categoryKey from Intent
        categoryKey = getIntent().getStringExtra("categoryKey");

        if (categoryKey == null) {
            Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editItemName = findViewById(R.id.editItemName);
        editItemDesc = findViewById(R.id.editItemDesc);
        addItemButton = findViewById(R.id.addItemButton);
        reportButton = findViewById(R.id.report);
        recyclerView = findViewById(R.id.recyclerView);
        ilacc = findViewById(R.id.ilacc);
        ilbcak = findViewById(R.id.ilbcak);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        gson = new Gson();

        loadItemList();

        adapter = new ItemListAdapter(itemList, this, sharedPreferences, categoryKey);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editItemName.getText().toString().trim();
                String desc = editItemDesc.getText().toString().trim();

                if (!name.isEmpty()) {
                    itemList.add(new ItemModel(name, desc, false));
                    adapter.notifyItemInserted(itemList.size() - 1);
                    saveItemList();

                    editItemName.setText("");
                    editItemDesc.setText("");
                } else {
                    Toast.makeText(ItemList.this, "Please enter item name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ilacc.setOnClickListener(view -> {
            Intent i = new Intent(ItemList.this, Account_Show.class);
            startActivity(i);
        });

        ilbcak.setOnClickListener(view -> {
            Intent i = new Intent(ItemList.this, Category.class);
            startActivity(i);
        });

        // Report button click listener
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDFReport();
            }
        });
    }

    private void loadItemList() {
        String key = "Items_" + categoryKey;
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<List<ItemModel>>() {
        }.getType();
        itemList = (json == null) ? new ArrayList<>() : gson.fromJson(json, type);
    }

    private void saveItemList() {
        String key = "Items_" + categoryKey;
        String json = gson.toJson(itemList);
        sharedPreferences.edit().putString(key, json).apply();
    }

  /*  private void generatePDFReport() {
        createNotificationChannel();

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        titlePaint.setTextSize(24);
        titlePaint.setFakeBoldText(true);
        canvas.drawText("Shopping List Report", 180, 50, titlePaint);

        paint.setTextSize(14);
        int y = 100;
        for (int i = 0; i < itemList.size(); i++) {
            ItemModel item = itemList.get(i);
            String line = (i + 1) + ". " + item.getName() + " - " + item.getDescription() +
                    " [" + (item.isCollected() ? "Collected" : "Pending") + "]";
            canvas.drawText(line, 50, y, paint);
            y += 30;
        }

        document.finishPage(page);

        String fileName = "ShoppingListReport_" + System.currentTimeMillis() + ".pdf";

        // This code checks if the Android version is 10 (Q) or higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Use MediaStore API for Android 10 and above
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

            try {
                if (uri != null) {
                    try (OutputStream out = getContentResolver().openOutputStream(uri)) {
                        document.writeTo(out);
                        Toast.makeText(this, "Report saved to Downloads: " + fileName, Toast.LENGTH_LONG).show();
                        showSuccessNotification(fileName, uri);
                    }
                } else {
                    Toast.makeText(this, "Failed to create file Uri", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Use this code for Android 9 and below
            if (checkStoragePermission()) {
                File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!downloadsFolder.exists()) {
                    downloadsFolder.mkdirs();
                }
                File file = new File(downloadsFolder, fileName);

                try (FileOutputStream fos = new FileOutputStream(file)) {
                    document.writeTo(fos);
                    Toast.makeText(this, "Report saved to Downloads: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    showSuccessNotification(fileName, Uri.fromFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to save report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                requestStoragePermission();
            }
        }

        document.close();
    }*/


    private void generatePDFReport() {
        createNotificationChannel();

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();
        Paint headerPaint = new Paint();
        Paint footerPaint = new Paint();

        int pageWidth = 595;  // A4 width in points
        int pageHeight = 842; // A4 height in points
        int margin = 40;
        int lineHeight = 30;

        // Format for today's date and day
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        // Calculate how many items can fit on a page (leaving space for headers and footers)
        int itemsPerPage = (pageHeight - 150) / lineHeight;
        int totalPages = (int) Math.ceil((double) itemList.size() / itemsPerPage);

        int itemIndex = 0;

        for (int pageNum = 1; pageNum <= totalPages; pageNum++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNum).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            // Header: Date and Title
            headerPaint.setTextSize(14);
            headerPaint.setFakeBoldText(true);
            headerPaint.setColor(Color.BLACK);
            canvas.drawText(currentDate, margin, 30, headerPaint);

            titlePaint.setTextSize(22);
            titlePaint.setFakeBoldText(true);
            titlePaint.setColor(Color.BLACK);
            canvas.drawText("Shopping List Report", margin, 60, titlePaint);

            // Draw a line below the header
            paint.setStrokeWidth(1);
            paint.setColor(Color.GRAY);
            canvas.drawLine(margin, 70, pageWidth - margin, 70, paint);

            // List header (column titles)
            paint.setTextSize(16);
            paint.setFakeBoldText(true);
            paint.setColor(Color.BLACK);

            int col1X = margin;          // Sr. No. column
            int col2X = margin + 40;     // Item Name column
            int col3X = margin + 250;    // Description column
            int col4X = margin + 450;    // Status column

            int y = 100;

            canvas.drawText("No.", col1X, y, paint);
            canvas.drawText("Item Name", col2X, y, paint);
            canvas.drawText("Description", col3X, y, paint);
            canvas.drawText("Status", col4X, y, paint);

            y += lineHeight;

            // List items
            paint.setTextSize(14);
            paint.setFakeBoldText(false);

            for (int i = 0; i < itemsPerPage && itemIndex < itemList.size(); i++, itemIndex++) {
                ItemModel item = itemList.get(itemIndex);

                canvas.drawText(String.valueOf(itemIndex + 1), col1X, y, paint);
                canvas.drawText(item.getName(), col2X, y, paint);
                canvas.drawText(item.getDescription(), col3X, y, paint);
                String status = item.isCollected() ? "Collected" : "Pending";
                canvas.drawText(status, col4X, y, paint);

                y += lineHeight;
            }

            // Footer: Page number
            footerPaint.setTextSize(12);
            footerPaint.setColor(Color.DKGRAY);
            String footerText = "Page " + pageNum + " of " + totalPages;
            float footerWidth = footerPaint.measureText(footerText);
            canvas.drawText(footerText, (pageWidth - footerWidth) / 2, pageHeight - 30, footerPaint);

            document.finishPage(page);
        }

        String fileName = "ShoppingListReport_" + System.currentTimeMillis() + ".pdf";

        // File saving logic (for both Android Q+ and below)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri uri = null;
            OutputStream outputStream = null;
            try {
                Uri collection = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
                uri = getContentResolver().insert(collection, values);

                if (uri != null) {
                    outputStream = getContentResolver().openOutputStream(uri);
                    document.writeTo(outputStream);
                    Toast.makeText(this, "Report saved to Downloads: " + fileName, Toast.LENGTH_LONG).show();
                    showSuccessNotification(fileName, uri);
                } else {
                    Toast.makeText(this, "Failed to create file Uri", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (checkStoragePermission()) {
                File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!downloadsFolder.exists()) {
                    downloadsFolder.mkdirs();
                }
                File file = new File(downloadsFolder, fileName);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    document.writeTo(fos);
                    Toast.makeText(this, "Report saved to Downloads: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    showSuccessNotification(fileName, Uri.fromFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to save report: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                requestStoragePermission();
            }
        }

        document.close();
    }



    private boolean checkStoragePermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generatePDFReport();
            } else {
                Toast.makeText(this, "Permission denied. Cannot save report.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSuccessNotification(String fileName, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Report Created")
                .setContentText(fileName + " saved to Downloads")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Report Notifications";
            String description = "Notifications for PDF report creation";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}











