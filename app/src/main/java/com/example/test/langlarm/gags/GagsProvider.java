package com.example.test.langlarm.gags;

import android.content.Context;

import com.example.test.langlarm.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.security.ProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GagsProvider {

    private static final List<Gag> gags = new ArrayList();
    private static GagsProvider gagsInstance = null;

    public GagsProvider(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.gags);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split(";");
                gags.add(new Gag(lineValues[0], lineValues[1], Integer.valueOf(lineValues[2])));
            }
            gagsInstance = this;
        } catch (IOException e) {
            throw new InvalidParameterException("gags file parse error");
        }
    }

    public static GagsProvider getInstance() {
        if (gagsInstance == null) {
            throw new ProviderException("lack of context during initialization");
        }
        return gagsInstance;
    }

    public Gag getRandomGag() {
        return gags.get(new Random().nextInt(gags.size()));
    }
}