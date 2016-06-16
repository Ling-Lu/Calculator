# Calculator

A material design calculator. WITH CARDS

## About

Card Calculator was created with the idea of giving calculators in Android a more unique perspective and utilizing [Material Design](https://material.google.com/) to allow quicker and more intuitive navigation over a long series of events. The current standard for calculators in the Android ecosystem is exactly what one would normally expect: a grid of buttons. While [Google's official calculator app](https://play.google.com/store/apps/details?id=com.google.android.calculator) does use a swipe gesture to allow more screen space to be saved, I thought that there was still more possibility for improvement. Thus, this app was created. For input, it uses the standard keyboard used on the Android device. This means that the user will already be accustomed to its layout and won't need time to get used to entering info in the app. It also provides some extra features that could be useful and wouldn't be easy to implement otherwise, such as the voice input option on Google Keyboard. After inputting an equation, it will be parsed using the [exp4j](http://www.objecthunter.net/exp4j/) library. I did have my own algorithm to parse the equation for a while, but over the course of time (I was trying to add new features) it became ridiculously hard to test and debug, so I eventually gave up and switched to a library (I met someone on a forum while trying to debug it that helpfully pointed me to this). Recently solved equations show in a list on the app's main page. Clicking on them allows you to copy the equation to the clipboard or edit it, while swiping deletes it and shows a [snackbar](https://material.google.com/components/snackbars-toasts.html) with an option to undo the action. This kind of behavior is standard for many apps in Android, so it should be relatively easy for a new user to figure out the interface of the app and start using it.

The app's icon was designed by [Vukašin Anđelković](https://plus.google.com/+Vuka%C5%A1inAn%C4%91elkovi%C4%87zavukodlak).

## License

```
Copyright 2016 James Fenn

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
