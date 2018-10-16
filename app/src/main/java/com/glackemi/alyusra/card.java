package com.nouveta.payme;

import android.content.Intent;
import android.net.Uri;

public class card {
    Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

}
