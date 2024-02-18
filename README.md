### How to use

To get a Git project into your build:

**Step 1**. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the dependency

```
dependencies {
    implementation 'com.github.akimov1712:MonthAndYearPicker:1.0.0'
}
```

<img width="284" alt="Снимок экрана 2024-02-18 в 13 28 39" src="https://github.com/akimov1712/MonthAndYearPicker/assets/107273987/c5c8de24-04b4-4f83-92b9-56f539e95177">


###  Usage
```
<ru.topbun.yearmonthpicker.view.YearMonthPicker
    android:id="@+id/month_year_picker"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

Or

```
private fun openDialog(){
        // FragmentTransaction, we'll need to show dialogFragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // Create the dialog itself, then set it up and show it to the user
        val dialogPicker = PickerDialogFragment()

        dialogPicker
            .setTitleText("MyApp")
            .setColorTitleText(Color.BLACK)
           ... other settings
            
        // Show the DialogFragment by specifying the tag you like
        dialogPicker.show(fragmentTransaction, "picker")
}
```

### Attributes

There are many different attributes in the library that allow you to customize the Picker the way you want it.
There are 2 ways to set attributes. From XML markup or from code

**Note that the listener on the buttons can only be set from the code.** You can add MonthYearPicker to Layout, and set the listener by accessing the view from the code.

By creating the MonthYearPicker from code, you have an additional attribute available for customization, **setCornerRadius(25f)**. Since calling the picker using the **show()** method from the code opens a DialogFragment that has rounding, you can also customize it if needed

First

```
 <ru.topbun.yearmonthpicker.view.YearMonthPicker
        android:id="@+id/month_year_picker"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:minWidth="330dp"
        
//     Attributes to customize the title
        app:isShowTitle="true"
        app:textTitle="MyApp"
        app:colorTitle="@color/black"
        app:textSizeTitle="30sp"


//     Attributes for customizing the bottom buttons
        app:textPositiveButton="positive text"
        app:textNegativeButton="negative text"
        app:colorTextPositiveButton="@color/blue"
        app:colorTextNegativeButton="@color/orange"

        app:backgroundPositiveButton="@drawable/background_button_filled"
        app:backgroundNegativeButton="@drawable/background_button_filled"
        app:backgroundTintPositiveButton="@color/cyan"
        app:backgroundTintNegativeButton="@color/light_green"


//      Attributes for customizing tab buttons
        app:colorTextActiveButton="@color/black"
        app:colorTextDisactiveButton="@color/red"

        app:backgroundActiveButton="@drawable/background_button_filled"
        app:backgroundDisactiveButton="@color/transparent"
        app:backgroundTintActiveButton="@color/purple"
        app:backgroundTintDisactiveButton="@color/transparent"


//     Attributes for customizing the date selection buttons
        app:backgroundActiveItem="@drawable/background_button_filled"
        app:backgroundDisactiveItem="@color/transparent"
        app:colorTextActiveItem="@color/blue"
        app:colorTextDisactiveItem="@color/orange"

        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

Second

```
binding.dialogPicker
            .setTitleText("MyApp")
            .setColorTitleText(Color.BLACK)
            .setTextSizeTitle(28)
            .setIsVisibleTitle(true)
            .setPositiveButtonText("Confirm")
            .setNegativeButtonText("Dismiss")
            .setColorPositiveButtonText(Color.WHITE)
            .setColorNegativeButtonText(Color.BLACK)
            .setBackgroundPositiveButton(R.drawable.background_button_filled)
            .setBackgroundNegativeButton(R.drawable.background_button_stroke)
            .setTintPositiveButton(0) // 0 – do not install tint. The color is taken from the background
            .setTintNegativeButton(0) // 0 – do not install tint. The color is taken from the background
            .setTextColorActiveButton(Color.WHITE)
            .setTextColorDisactiveButton(Color.BLACK)
            .setBackgroundActiveButton(R.drawable.background_button_filled)
            .setBackgroundDisactiveButton(R.drawable.background_button_stroke)
            .setTintActiveButton(0) // 0 – do not install tint. The color is taken from the background
            .setTintDisactiveButton(0) // 0 – do not install tint. The color is taken from the background
            .setTextColorActiveItem(Color.WHITE)
            .setTextColorDisactiveItem(R.color.default_grey)
            .setBackgroundActiveItem(R.drawable.background_button_filled)
            .setBackgroundDisactiveItem(R.color.transparent)
            .setIsVisibleTitle(true)
            .setCornerRadius(25f)
            .setListener { bottomAction, timeMillis ->
                /*
                    timeMillis is the time in timeStamp
                    It is further convenient to work with him.
                    Using the 'Date()' class you can get all the information (time, day, month, year).

                    We just save the value in our code and work with it further
                */

                // Using bottomAction we determine which of the two buttons was pressed
                when (bottomAction) {

                    // The positiveButton was pressed
                    BottomButtonAction.POSITIVE -> {
                        // For example here when I click on the positive button
                        // I display the date in dd MMM yyyy HH:mm format using formatTimeMillis()
                        val formatTime = formatTimeMillis(timeMillis)
                        Toast.makeText(this, formatTime, Toast.LENGTH_SHORT).show()
                        dialogPicker.dismiss()
                    }

                    // The negativeButton was pressed
                    BottomButtonAction.NEGATIVE -> {

                        //Exit FragmentDialog
                        dialogPicker.dismiss()
                    }
                }
            }
```

### License

```
MIT License

Copyright (c) 2024 topbun

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
