package bhtech.com.cabbytaxi.Register.Company;

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
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.UserObj;

public class CompanySettingController extends SlidingMenuController implements CompanySettingInterface.Listener {
    private Context context;
    private CompanySettingModel model;
    private CompanySettingView view;
    private CreditCardEditView creditCardEditView;
    private CreditCardDoneView creditCardDoneView;
    private ChooseImageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = CompanySettingController.this;
        getLayoutInflater().inflate(R.layout.activity_company_setting_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.company_setting));

        model = new CompanySettingModel(context);
        dialog = new ChooseImageDialog(context, this);

        view = new CompanySettingView();
        view.setListener(this);
        view.setDatabase(model);

        creditCardEditView = new CreditCardEditView();
        creditCardEditView.setListener(this);
        creditCardEditView.setDatasoucre(model);

        creditCardDoneView = new CreditCardDoneView();
        creditCardDoneView.setListener(this);
        creditCardDoneView.setDatasoucre(model);

        if (savedInstanceState == null) {
            addFragment(R.id.company_st_contain, view);
            addFragment(R.id.company_st_contain, creditCardEditView);
            addFragment(R.id.company_st_contain, creditCardDoneView);
            hideAllFragment();
            showFragment(view);
        }

        model.getListCountry(context);
    }

    @Override
    public void onCreateViewFinish() {
        view.reloadView();
    }

    @Override
    public void OnAvatarClick() {
        dialog.show();
    }

    @Override
    public void OnButtonBackClick() {
        onBackPressed();
    }

    @Override
    public void OnButtonDoneClick() {
        Error error = model.checkAllValidate(context);
        if (error.errorCode == Error.NO_ERROR) {
            if (model.getPayMode() == ContantValuesObject.PayByCash) {
                model.register(context, new CompanySettingModel.OnRegister() {
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
                    public void failure(Error error) {
                        showAlertDialog(error.errorMessage).show();
                    }
                });
            } else if (model.getPayMode() == ContantValuesObject.PayByCreditCard) {
                model.saveDataToUserObj();
                hideAllFragment();
                showFragment(creditCardEditView);
            }
        } else {
            showAlertDialog(error.errorMessage).show();
        }
    }

    private void hideAllFragment() {
        hideFragment(view);
        hideFragment(creditCardEditView);
        hideFragment(creditCardDoneView);
    }

    private void uploadAvatar() {
        if (model.getAvatarBitmap() != null) {
            model.uploadAvatar(new CompanySettingModel.OnUploadAvatar() {
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
        model.userLogin(context, new CompanySettingModel.OnUserLogin() {
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
    public void onCreditCardButtonOKClick() {
        Error error = model.checkCreditCardValidate(context);
        if (error.errorCode == Error.NO_ERROR) {
            creditCardDoneView.reloadView();
            model.registerCreditCard(context, new CompanySettingModel.OnRegister() {
                @Override
                public void success() {
                    model.saveDataToCardObj();
                    hideAllFragment();
                    showFragment(creditCardDoneView);
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
    public void onCreditCardButtonBackClick() {
        hideAllFragment();
        showFragment(view);
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
