package com.edu.alarmsystem.interfaces;

import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface VolleyCallback {
    void getInfo(String response) throws JSONException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException, ExecutionException, InterruptedException;
}
