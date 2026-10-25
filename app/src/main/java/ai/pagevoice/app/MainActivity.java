package ai.pagevoice.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import android.widget.Toast;

public class MainActivity extends Activity {
    private WebView webView;
    private ValueCallback<Uri[]> fileCallback;
    private static final int FILE_CHOOSER = 42;
    private static final String APP_URL = "https://pagevoice-epub-studio-e340on.v2.appdeploy.ai/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                return !("https".equals(uri.getScheme()) && uri.getHost() != null &&
                    (uri.getHost().equals("pagevoice-epub-studio-e340on.v2.appdeploy.ai") ||
                     uri.getHost().endsWith(".appdeploy.ai")));
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> callback, FileChooserParams params) {
                if (fileCallback != null) fileCallback.onReceiveValue(null);
                fileCallback = callback;
                Intent intent = params.createIntent();
                try { startActivityForResult(intent, FILE_CHOOSER); }
                catch (Exception e) { fileCallback = null; callback.onReceiveValue(null); return false; }
                return true;
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                Toast.makeText(MainActivity.this, "For generated audio, use the app's download button. If prompted, choose a download app.", Toast.LENGTH_LONG).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception ignored) {}
            }
        });
        webView.loadUrl(APP_URL);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER && fileCallback != null) {
            Uri[] result = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
            fileCallback.onReceiveValue(result);
            fileCallback = null;
        }
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
