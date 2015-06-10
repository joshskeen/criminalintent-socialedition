CriminalIntent Social Edition!

Enables liking and posting crime reports to your facebook wall.
To test, you must either have an emulator or hardware device with the facebook app installed. 
See facebook android developer docs for details on installing. 

1.  visit https://developers.facebook.com/ & log in with a facebook account. It's best if
you create a new facebook account for test purposes so as to not spam your normal facebook friends!

2. under My Apps click Add New App & select android

3. Enter an app name, click create new facebook app id.

4. Follow the steps on the quickstart page. 

5. Copy the app id & appname listed on the developer page into the strings.xml file of your project (res/values/strings.xml): 

	```    
	<string name="app_name">CriminalIntentApp</string>
	<string name="app_id">12343453453534...</string>
	
	```
6. This info will also need to be added to the androidmanifest.xml: 
	
	```
	 <activity android:name="com.facebook.FacebookActivity"
	                  android:configChanges=
	                      "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
	                  android:label="@string/app_name"
	                  android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
	
	        <meta-data
	            android:name="com.facebook.sdk.ApplicationId"
	            android:value="@string/app_id"/>
	        <meta-data
	            android:name="com.facebook.sdk.ApplicationName"
	            android:value="@string/app_name"/>
	        <provider
	            android:name="com.facebook.FacebookContentProvider"
	            android:authorities="com.facebook.app.FacebookContentProvider{app_id}"
	            android:exported="true"/>
	```
	Make sure to switch `{app_id}` for whatever your app id is. 
6. Next, we need to initialize the facebook sdk. We will want to do this when the app first is created. To do this, we will add an Application class. 

	```	
	public class CriminalIntentApplication extends Application {
	
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        FacebookSdk.sdkInitialize(getApplicationContext());
	    }
	}
	```
7. Because we are using a custom Application class, we must register it with the manifest. 

	```
	 <application
	        android:name=".CriminalIntentApplication"
	        android:allowBackup="true"
	        android:icon="@mipmap/ic_launcher"
	        android:label="@string/app_name"
	        android:theme="@style/AppTheme">
	```
7. Now add a share button to the view: 
	
	frgment_crime.xml: 
	
	```
	 <Button
	        android:id="@+id/crime_report"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:layout_marginLeft="16dp"
	        android:layout_marginRight="16dp"
	        android:text="@string/crime_report_text"
	        />
	
	    <com.facebook.share.widget.ShareButton
	        android:id="@+id/share_button"
	        android:layout_marginLeft="16dp"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"/>
	
	</LinearLayout>
	```
	CrimeFragment.java: 
	
	```
	
	mShareButton = (ShareButton) v.findViewById(R.id.share_button);
	
	```
	
8. Finally, we will wire up a share button to the crime! We only want to be able to share crime posts with an image for now. 

	CrimeFragment.java: 
	
	```
	private void updateFacebookShareButton() {
	        Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
	        mShareButton.setEnabled(false);
	        if (bitmap == null) {
	        //no image? leave share button disabled
	            return;
	        }
	        mShareButton.setEnabled(true);
	        Uri imageUri = Uri.fromFile(mPhotoFile);
	        SharePhoto.Builder sharePhotoBuilder = new SharePhoto.Builder();
	        sharePhotoBuilder.setImageUrl(imageUri)
	                .setUserGenerated(true)
	                .setBitmap(bitmap);
	        SharePhotoContent photoContent = new SharePhotoContent.Builder()
	                .addPhoto(sharePhotoBuilder.build())
	                .build();
	        mShareButton.setShareContent(photoContent);
	        mShareButton.setFragment(this);
	    }
	```
