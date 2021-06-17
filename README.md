# WindowsIsetsTest
Android 11 new Keyboard API demo


It is known that "WindowCompat.setDecorFitsSystemWindows(window, false)" is interfering with the collapsing toolbar used in combination with the CoordinatorLayout.

Normally "WindowCompat.setDecorFitsSystemWindows(window, false)" is needed in order to allow the UI elements to receive the "setOnApplyWindowInsetsListener()" notifications (needed for the new keyboard API in Android 11) but there are some exceptions.
I have posted a comment before saying that we are receiving notifications even without the setDecorFitsSystemWindows() call, and here is why: there are some layouts like DrawerLayout, CoordinatorLayout, CollapsingToolbarLayout and friends, that have custom implementation for android:fitsSystemWindows="true" attribute and making possible to receive notifications because "they are calling internally dispatchApplyWindowInsets() for each child on Lollipop and higher to allow child views to also receive fitsSystemWindows, a departure from the default (where normally it would simply consume the insets and children would never receive fitsSystemWindows)".

So basically, the combination of DrawerLayout (in our case) and android:fitsSystemWindows="true" flag is unlocking the possibility to receive notifications by setOnApplyWindowInsetsListener() even without setDecorFitsSystemWindows() call, because is doing the same thing.

In conclusion, I found safe to remove the "WindowCompat.setDecorFitsSystemWindows(window, false)".

Some references for my conclusion:

"https://medium.com/androiddevelopers/why-would-i-want-to-fitssystemwindows-4e26d9ce1eec"
"https://proandroiddev.com/draw-under-status-bar-like-a-pro-db38cfff2870"
"https://medium.com/androiddevelopers/windowinsets-listeners-to-layouts-8f9ccc8fa4d1"
"https://proandroiddev.com/android-11-creating-an-ime-keyboard-visibility-listener-c390a40d1ad0"
"https://proandroiddev.com/exploring-windowinsets-on-android-11-a80cf8fe19be"
