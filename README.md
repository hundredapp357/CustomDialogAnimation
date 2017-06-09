# This module can set a customized layout, and animation is possible. 

## usage  

 * call from activity and fragment.
 * use DialogListener(interface).
 * implements DialogListener(is positive button and negative button event) in activity or fragment.  
 Created by yamaichi57 on 2016/11/19.



  new CustomDialogAnimation.Builder(MainActivity.this, getString(R.string.title), getString(R.string.message))
      .animation(true)
      .listener(eventListener())
      .cancelable(false)
      .positive(getString(R.string.positive_button_label))
      .negative(getString(R.string.negative_button_label))
      .show();


## how to add in your project  
  git submodule add --force https://github.com/hundredapp357/CustomDialogAnimation CustomDialogAnimation  
  git submodule init  
  git submodule update  
  
  
## how to submodule delete
  git submodule deinit -f path/to/CommonDialogFragment  
  git rm -f  path/to/CommonDialogFragment  



