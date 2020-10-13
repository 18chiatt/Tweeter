package com.example.tweeter.view.fragments.StatusLists;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class LinkSpan extends ClickableSpan {
    public LinkSpan(IntentFulfiller fulfiller, String url) {
        this.fulfiller = fulfiller;
        this.url = url;
    }



    public String getUrl() {
        return url;
    }

    private IntentFulfiller fulfiller;
    private String url;


    @Override
    public void onClick(@NonNull View widget) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        fulfiller.startActivity(browserIntent);
    }
}
