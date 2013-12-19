package com.primerworldapps.iamonmaydan.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class UrlVisitor {
	public static void visitURL(Context context, String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		context.startActivity(browserIntent);
	}
}
