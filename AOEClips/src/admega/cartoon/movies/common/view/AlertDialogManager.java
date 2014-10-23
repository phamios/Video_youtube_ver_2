package admega.cartoon.movies.common.view;

import admega.cartoon.movies.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AlertDialogManager {
	 /**
     * Function to display simple Alert Dialog
     * 
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon) - pass null if you
     *            don't want icon
     */
	
	private static AlertDialog mAlertDialog;
	
    public static void showAlert(Context context, String msg) {
    	if (mAlertDialog == null || !mAlertDialog.isShowing()) {
    		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	        builder.setMessage(msg)
    	                .setNegativeButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {

    	                    public void onClick(DialogInterface dialog, int id) {
    	                        dialog.cancel();
    	                    }
    	                });

    	        AlertDialog alert = builder.create();
    	        alert.setCancelable(true);
    	        alert.setCanceledOnTouchOutside(true);
    	        alert.show();
    	}
    }
    
    public static void showAlert(Context context, String msg, DialogInterface.OnClickListener click) {
    	if (mAlertDialog == null || !mAlertDialog.isShowing()) {
    		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	        builder.setMessage(msg).setNegativeButton(R.string.dialog_ok, click);
    	        AlertDialog alert = builder.create();
    	        alert.setCancelable(true);
    	        alert.setCanceledOnTouchOutside(true);
    	        alert.show();
    	}
    }
    
    public static void showAlert(Context context, String title, String content,
            String positiveButton, String nativeButton, OnClickListener onClick) {
    	if (mAlertDialog == null || !mAlertDialog.isShowing()) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setMessage(content)
                    .setPositiveButton(positiveButton, onClick)
                    .setNegativeButton(nativeButton, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alert = builder.create();
            alert.setCancelable(true);
            alert.setCanceledOnTouchOutside(true);
            alert.show();
    	}
    }

    public static void showAlert(Context context, String content, String positiveButton,
            String nativeButton, OnClickListener onClick) {
    	if (mAlertDialog == null || !mAlertDialog.isShowing()) {
    		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	        builder.setMessage(content)
    	                .setPositiveButton(positiveButton, onClick)
    	                .setNegativeButton(nativeButton, new DialogInterface.OnClickListener() {

    	                    public void onClick(DialogInterface dialog, int id) {
    	                        dialog.cancel();
    	                    }
    	                });


    	        AlertDialog alert = builder.create();
    	        alert.setCancelable(true);
    	        alert.setCanceledOnTouchOutside(true);
    	        alert.show();
    	}
    }

    public static void showAlert(Context context, String positiveButton,
            String nativeButton, OnClickListener onClick) {
    	if (mAlertDialog == null || !mAlertDialog.isShowing()) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setPositiveButton(positiveButton, onClick)
                    .setNegativeButton(nativeButton, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alert = builder.create();
            alert.setCancelable(true);
            alert.setCanceledOnTouchOutside(true);
            alert.show();
		}
    }
}
