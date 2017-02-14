package bhtech.com.cabbytaxi.Register.Personal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import bhtech.com.cabbytaxi.FindTaxi.FindTaxiController;
import bhtech.com.cabbytaxi.Login.LoginInterface;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.Register.RegisterController;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.UserObj;

public class ProfileSettingController extends SlidingMenuController implements
        ProfileSettingInterface.Listener {
    private Context context = ProfileSettingController.this;
    private ProfileSettingModel model;
    private ProfileSettingView view;
    private CreditCardEditView creditCardEditView;
    private CreditCardDoneView creditCardDoneView;
    private ChooseImageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile_setting_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.profile_setting));
        model = new ProfileSettingModel(context);

        view = new ProfileSettingView();
        view.setListener(this);
        view.setDatabase(model);

        creditCardEditView = new CreditCardEditView();
        creditCardEditView.setListener(this);
        creditCardEditView.setDatasoucre(model);

        creditCardDoneView = new CreditCardDoneView();
        creditCardDoneView.setListener(this);
        creditCardDoneView.setDatasoucre(model);

        dialog = new ChooseImageDialog(context, this);

        if (savedInstanceState == null) {
            addFragment(R.id.container, view);
            addFragment(R.id.container, creditCardEditView);
            addFragment(R.id.container, creditCardDoneView);
            hideAllFragment();
            showFragment(view);
        } else {
            //Do nothing
        }

        model.getListCountry(context);
    }

    public void hideAllFragment() {
        hideFragment(view);
        hideFragment(creditCardEditView);
        hideFragment(creditCardDoneView);
    }

    @Override
    public void onCreateViewFinish() {
        view.reloadView();
    }

    @Override
    public void onButtonDoneClick() {
        Error error = model.checkProfileValidate(context);
        if (error.errorCode == Error.NO_ERROR) {
            if (model.isRadioButtonCash()) {
                model.register(context, new ProfileSettingModel.OnRegisterUserPayByCash() {
                    @Override
                    public void success() {
                        UserObj.getInstance().login(context, new LoginInterface.OnFinishExecute() {
                            @Override
                            public void onFinish(boolean result) {
                                uploadAvatar();
                            }
                        });
                    }

                    @Override
                    public void failure(bhtech.com.cabbytaxi.object.Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });
            } else if (model.isRadioButtonCreaditCard()) {
                model.saveDataToUserObj();
                hideAllFragment();
                showFragment(creditCardEditView);
            }
        } else {
            showAlertDialog(error.errorMessage).show();
        }
    }

    private void uploadAvatar() {
        if (model.getAvatarBitmap() != null) {
            model.uploadAvatar(new ProfileSettingModel.OnUploadAvatar() {
                @Override
                public void Success() {
                    startActivity(new Intent(context, FindTaxiController.class));
                    finishActivity();
                }

                @Override
                public void Failure(Error error) {
                    showAlertDialog(error.errorMessage).setCancelable(false)
                            .setPositiveButton(getString(R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(context, FindTaxiController.class));
                                            finishActivity();
                                        }
                                    })
                            .show();
                }
            });
        } else {
            startActivity(new Intent(context, FindTaxiController.class));
            finishActivity();
        }
    }

    @Override
    public void onProfileButtonBackClick() {
        onBackPressed();
    }

    @Override
    public void onImageAvatarClick() {
        dialog.show();
    }

    @Override
    public void onImageFacebookClick() {
        //TODO
    }

    @Override
    public void onImageGooglePlusClick() {
        //TODO
    }

    @Override
    public void onImageTwitterClick() {
        //TODO
    }

    @Override
    public void termOfUseClick() {
        showDialogTermOfUser();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, RegisterController.class));
        finishActivity();
    }

    @Override
    public void onCreditCardButtonOKClick() {
        Error error = model.checkCreditCardValidate(context);
        if (error.errorCode == Error.NO_ERROR) {
            creditCardDoneView.reloadView();
            model.register(context, new ProfileSettingModel.OnRegisterUserPayByCash() {
                @Override
                public void success() {
                    hideAllFragment();
                    showFragment(creditCardDoneView);
                    if (UserObj.getInstance().getCompany() == null) {
                        updateToolbarTitle(getString(R.string.send_detail));
                    } else {
                        updateToolbarTitle(getString(R.string.company_setting));
                    }
                }

                @Override
                public void failure(Error error) {
                    showAlertDialog(error.errorMessage).show();
                }
            });

        } else {
            showAlertDialog(error.errorMessage).show();
        }
    }

    @Override
    public void onDialogButtonTakePicktureOnCameraClick() {
        startActivityForResult(PhoneObject.takeCameraPicture(context), PhoneObject.CAMERA_CAPTURE_IMAGE);
    }

    @Override
    public void onDialogButtonChoosePicktureFromGallery() {
        Intent intent = PhoneObject.chooseImageFromGallery(context);
        startActivityForResult(Intent.createChooser(intent, null), PhoneObject.SELECTED_PICTURE);
    }

    @Override
    public void onButtonFinishClick() {
        model.userLogin(context, new ProfileSettingModel.OnUserLogin() {
            @Override
            public void success() {
                uploadAvatar();
            }

            @Override
            public void failure(Error error) {
                creditCardDoneView.setFinishButtonEnable(true);
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onCreditCardButtonBackClick() {
        hideAllFragment();
        showFragment(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            if (requestCode == PhoneObject.SELECTED_PICTURE) {
                File file = PhoneObject.getFileFromUri(context, data.getData());
                bitmap = PhoneObject.getBitmapFromFile(file);
            } else if (requestCode == PhoneObject.CAMERA_CAPTURE_IMAGE) {
                bitmap = PhoneObject.getBitmapFromBundle(data.getExtras());
            }
            if (bitmap != null) {
                view.setAvatarImage(bitmap);
                model.setAvatarBitmap(bitmap);
            } else {
                Log.e("onActivityResult", "Bitmap null");
            }
        } else if (resultCode == RESULT_CANCELED) {

        } else {

        }
    }
}
