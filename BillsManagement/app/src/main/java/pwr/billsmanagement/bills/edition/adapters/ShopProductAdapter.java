package pwr.billsmanagement.bills.edition.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import pwr.billsmanagement.R;
import pwr.billsmanagement.ocr.parsers.ShopProduct;

/**
 * Created by Squier on 10.05.2017.
 */

public class ShopProductAdapter extends ArrayAdapter<ShopProduct> {

    public ShopProductAdapter(Context context, int resource, List<ShopProduct> products) {
        super(context, resource, products);
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        if(rowView == null) {
            rowView = LayoutInflater.from(getContext()).inflate(R.layout.bill_product_row, parent, false);
        }

        ProductViewHandle handle = new ProductViewHandle(
                rowView.findViewById(R.id.productNumber),
                rowView.findViewById(R.id.productName),
                rowView.findViewById(R.id.productPrice),
                rowView.findViewById(R.id.productCategory),
                rowView.findViewById(R.id.acceptProduct),
                rowView.findViewById(R.id.deleteProduct)
        );

        ShopProduct product = getItem(position);

        handle.name.setActivated(true);
        handle.price.setActivated(true);
        handle.category.setActivated(true);

        handle.id.setText("Produkt " + (position+1));
        handle.name.setText(product.getName());
        handle.price.setText(product.getPrice());
        handle.accept.setImageResource(R.drawable.ic_accept_green);
        handle.delete.setImageResource(R.drawable.ic_delete_product);

        handle.accept.setOnClickListener(v -> com.orhanobut.logger.Logger.i("Accepted product"));
        handle.delete.setOnClickListener(v -> com.orhanobut.logger.Logger.i("Deleted product"));

        return rowView;

    }

    private class ProductViewHandle {
        TextView id;
        EditText name;
        EditText price;
        Spinner category;
        ImageView accept, delete;

        public ProductViewHandle(View id, View name, View price, View category, View accept, View delete) {
            this.id         = (TextView) id;
            this.name       = (EditText) name;
            this.price      = (EditText) price;
            this.category   = (Spinner) category;
            this.accept     = (ImageView) accept;
            this.delete     = (ImageView) delete;
        }
    }

}
