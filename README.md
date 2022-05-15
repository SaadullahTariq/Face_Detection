Pre-requisites:
● Firebase Machine Learning kit
● Adding Firebase to Android App
Approach

Step 1: Create a New Project

1. Open a new project in android studio with whatever name you want.
2. We are gonna work with empty activity for the particular project.
3. The minimum SDK required for this particular project is 23, so choose any API of
23 or above.
4. The language used for this project would be JAVA.
5. Leave all the options other than those mentioned above, untouched.
6. Click on FINISH.

Step 2: Connect with ML KIT on Firebase.

1. Login or signup on Firebase.
2. In Firebase console, create a new project or if you wanna work with an existing
project then open that.
3. Name the project as per your choice.
4. Go to Docs.

5. Click on Firebase ML, and in the left space, choose ‘recognize text‘ under
Vision.
6. Go through the steps mentioned for better understanding.
7. Come back to Android Studio.
8. Go to Tools -> Firebase -> Analytics -> Connect with Firebase -> Choose
your project from the dialog box appeared -> Click Connect. (This step
connects your android app to the Firebase)


Step 3: Custom Assets and Gradle

● For enhancing the GUI either choose an image of .png format and add it in the
res folder and set it as the background of main .xml file, or set a background
color by going to the design view of the layout and customizing background
under Declared Attributes.
● To, include the ML KIT dependencies, in the app, go to Gradle Script ->
build.gradle(Module:app) and add an implementation mentioned below:
implementation ‘com.google.firebase:firebase-ml-vision:17.0.0’
● Now copy the below-mentioned text, and paste it at the very end of the app level
Gradle, outside all the brackets.
apply plugin: ‘com.google.gms.google-services’
● Next, go to bulid.gradle (project) and copy the below-mentioned text, and paste it
in ‘dependencies’ classpath.
classpath ‘com.google.gms:google-services:4.2.0’
● Click on sync now.


Step 4: Designing the UI

● Add a Button to open the camera option.
● Now go to layout -> new -> layout resource file -> Name:
fragment_resultdialog.xml. This file has been created to customize the output

screen, which will display a dialog box called Result Dialog box with a text view
called Result Text with all the attributes of the detected image.

Step 5: Firebase App Initializer
● Create a new java class by java -> new -> class -> Name:
LCOFaceDetection.java -> superclass:
Application(android.app.Application).

Step 6: Inflating the Result Dialog Box

● Create a new java class namely, ResultDialog.java and superclass,
DialogFragment, which is the java file for the fragment_resultdialog.xml.
Below is the example code for java file.

Step 7: Open Camera on a Real Device and Enabling Face Detection
● Below is the example code for the main java file.
● There is a need of FirebaseVision and FirebaseVisionFaceDetector classes
for this.
● Here’s a list of all the settings you can configure in your face detection model.
