package bhtech.com.cabbytaxi.Receipt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.SlidingMenu.SlidingMenuController;
import bhtech.com.cabbytaxi.object.PhoneObject;
import bhtech.com.cabbytaxi.object.UserObj;

public class ReceiptController extends SlidingMenuController implements ReceiptInterface.Listener {

    private Context context;
    private ReceiptModel model;
    private ReceiptView receiptView;
    private ReceiptDetailView detailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ReceiptController.this;
        getLayoutInflater().inflate(R.layout.activity_receipt_controller, mFrameSlidingMenu);
        updateToolbarTitle(getString(R.string.receipt));
        model = new ReceiptModel(context);
        receiptView = new ReceiptView();

        receiptView.setListener(this);
        receiptView.setDatasource(model);

        detailView = new ReceiptDetailView();
        detailView.setListener(this);
        detailView.setDatasource(model);

        addFragment(R.id.receipt_contain, receiptView);
        addFragment(R.id.receipt_contain, detailView);
        hideAllFragment();
        showFragment(receiptView);

    }

    public void hideAllFragment() {
        hideFragment(receiptView);
        hideFragment(detailView);
    }

    @Override
    public void onSortByItemClick() {
        model.sortReceiptList();
        receiptView.reloadView();
    }

    @Override
    public void onListItemClick() {
        model.setDataDetail(context);
        hideAllFragment();
        showFragment(detailView);
        detailView.reloadView();
    }

    @Override
    public void onButtonDeleteClick() {
        model.deleteReceipt(context, new ReceiptModel.OnDeleteReceipt() {
            @Override
            public void onSuccess() {
                onListViewCreateViewFinish();
                hideAllFragment();
                showFragment(receiptView);
            }

            @Override
            public void onFailure(bhtech.com.cabbytaxi.object.Error error) {
                showAlertDialog(error.errorMessage).show();
            }
        });
    }

    @Override
    public void onButtonMailClick() {
        String userMail = UserObj.getInstance().getEmail();
        String subject = context.getString(R.string.receipt_detail);
        String body = model.getReceiptDetail();

        Intent i = PhoneObject.sendEmail(userMail, subject, body);
        startActivity(Intent.createChooser(i, context.getString(R.string.send_email)));
    }

    @Override
    public void onButtonBackClick() {
        hideAllFragment();
        showFragment(receiptView);
    }

    @Override
    public void onButtonPrintClick() {
        //TODO print receipt
    }

    @Override
    public void onListViewCreateViewFinish() {
        try {
            model.setListSortBy(context);
            model.getReceiptList(context, new ReceiptModel.onGetListReceipt() {
                @Override
                public void onSuccess() {
                    model.listByMonth();
                    receiptView.reloadView();
                }

                @Override
                public void onFailure(bhtech.com.cabbytaxi.object.Error error) {
                    Toast.makeText(context, error.errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListMonthItemClick() {
        model.listByMonth();
        receiptView.reloadView();
    }
}
