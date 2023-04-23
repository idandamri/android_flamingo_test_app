### This project is set to help test the new "Rule" feature  inside the Network Inspector


- Single screen app with a text and 2 buttons.
- first button sends respone to the [#link](https://demo2803075.mockable.io/flamingo_test "#link")
- second button resets the text to "Hello Android!"

##### What happens when the request button was clicked:
- A request is being set By *Volly*.
- If the response failes the Text will show "Error".
- If the response returns succesfuly it is being parsed, from the parsed data we search the key "name" and take it's value to replace the text inside the Composable Text.
- If all is well untill this point we will see the text changed to "Hey Boss!".
- if the "Reset" is being clicked the value of the text will set the composable Text into "Hello Android!".


![Preview](https://github.com/idandamri/android_flamingo_test_app/blob/main/flamingo_test_app.png?raw=true "Preview")
