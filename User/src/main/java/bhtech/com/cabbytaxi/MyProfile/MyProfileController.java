package bhtech.com.cabbytaxi.MyProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import java.io.File;

import bhtech.com.cabbytaxi.Login.LoginController;
import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.UserObj;

public class MyProfileController extends SlidingMenuController implements MyProfileInterface.Listener {
    private Context context = MyProfileController.this;
    private MyProfileModel model;
    private MyProfileView view;
    private ChooseImageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile_setting_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.my_profile));
        model = new MyProfileModel(context);

        view = new MyProfileView();
        view.setListener(this);
        view.setDatabase(model);

        dialog = new ChooseImageDialog(context, this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, view).commit();
        } else {
            //Do nothing
        }
    }

    @Override
    public void onCreateViewFinish() {
        model.getUserInformation();
        view.reloadView();
    }

    @Override
    public void onButtonLogoutClick() {
        model.userLogOut(context, new MyProfileModel.OnUserLogOut() {
            @Override
            public void success() {
                startActivity(new Intent(context, LoginController.class));
                finishActivity();
            }

            @Override
            public void failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onButtonDeleteClick() {
        showAlertDialog(getString(R.string.are_you_sure_you_want_to_delete_your_account))
                .setPositiveButton(getString(R.string.ok).toUpperCase(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.hasCurrentRequest(context, new MyProfileModel.OnCheckCurrentRequest() {
                            @Override
                            public void success(boolean hasCurrentRequest) {
                                if (hasCurrentRequest) {
                                    showAlertDialog(getString(R.string.can_not_delete_account_when_you_have_request_taxi)).show();
                                } else {
                                    deleteUser();
                                }
                            }

                            @Override
                            public void failure(bhtech.com.cabbytaxi.object.Error error) {
                                if (error.errorCode == Error.REQUEST_ID_NULL) {
                                    deleteUser();
                                } else {
                                    showAlertDialog(error.errorMessage).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void deleteUser() {
        int agreeDeleteAccount = 1;
        UserObj.getInstance().setDelete(agreeDeleteAccount);
        model.updateProfile(context, new MyProfileModel.onUpdateProfile() {
            @Override
            public void success() {
                startActivity(new Intent(context, LoginController.class));
                finishActivity();
            }

            @Override
            public void failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onAvatarClick() {
        dialog.show();
    }

    @Override
    public void onButtonEditScreenNameClick() {
        view.reloadView();
    }

    @Override
    public void onButtonEditPhoneNumberClick() {
        view.reloadView();
    }

    @Override
    public void onButtonEditEmailClick() {
        view.reloadView();
    }

    @Override
    public void onButtonEditCountryClick() {
        view.reloadView();
    }

    @Override
    public void onButtonEditUsernameClick() {
        view.reloadView();
    }

    @Override
    public void onButtonEditPasswordClick() {
        view.reloadView();
    }

    @Override
    public void onButtonFacebookClick() {

    }

    @Override
    public void onButtonGooglePlusClick() {

    }

    @Override
    public void onButtonTwitterClick() {

    }

    @Override
    public void onButtonBackClick() {
        onBackPressed();
    }

    @Override
    public void onButtonSaveClick() {
        model.updateProfile(context, new MyProfileModel.onUpdateProfile() {
            @Override
            public void success() {
                showAlertDialog(getString(R.string.successfully)).show();
            }

            @Override
            public void failure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PhoneObject.SELECTED_PICTURE) {
                File file = PhoneObject.getFileFromUri(context, data.getData());
                Bitmap bitmap = PhoneObject.getBitmapFromFile(file);
                uploadAvatar(PhoneObject.getFileFromBitmap(context, bitmap));
            } else if (requestCode == PhoneObject.CAMERA_CAPTURE_IMAGE) {
                Bitmap bitmap = PhoneObject.getBitmapFromBundle(data.getExtras());
                view.setAvatarImage(bitmap);
                uploadAvatar(PhoneObject.getFileFromBitmap(context, bitmap));
            }
        } else if (resultCode == RESULT_CANCELED) {

        } else {

        }
    }

    private void uploadAvatar(File file) {
        model.uploadAvatar(file, new MyProfileModel.OnUploadAvatar() {
            @Override
            public void Success() {
                view.reloadView();
            }

            @Override
            public void Failure(Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
